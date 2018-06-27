import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

	public static void main(String[] args) {
		String access_key = "27f276e4a26be678fd08ce3aa4c45294";
		DB db = new DB(DB.DB_base_url+"bwwd_system","main_root","551360bwwd");
		DB report_db = new DB(DB.DB_base_url+"bwwd_app_stats","main_root","551360bwwd");
		String remove_existing_rates = "DELETE FROM currencies_rates WHERE true;";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date new_date = new Date();
		String log_file_url = df.format(new_date)+"-file.log";
		Logger logger = new Logger("/home/ubuntu/bwwd/BwwdCurrencyUpdater/"+log_file_url);
		logger.log_report("STARTING to update currencies_rate table.",true);
        try {
            Connection con = db.getConnection();
			PreparedStatement ps = con.prepareStatement(remove_existing_rates);
			ps.executeUpdate();
			URL url = new URL("http://data.fixer.io/api/latest?access_key="+access_key);
			HttpURLConnection http_con = (HttpURLConnection) url.openConnection();
			http_con.setRequestMethod("GET");
			http_con.setDoInput(true); // indicates server will return a response
			
			int responseCode = http_con.getResponseCode();
			//System.out.println("POST Response Code :: " + responseCode);
			
			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
						http_con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				//System.out.println(response.toString());
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(response.toString());
		        JSONObject response_json = (JSONObject) obj;
		        parseServerResponse(report_db,db,response_json,logger);
			} else {
				logger.log_report("Server Error with code = [ "+ responseCode +" ] prevented updating the currencies_rates table.", false);
				try {
					Report_lib.store_report_to_DB(report_db,"There was an error when trying to update currencies_rate table. Please check the log file.");
				}catch (Exception e1) {
					StringWriter content = new StringWriter();
					e1.printStackTrace(new PrintWriter(content));
					logger.log_report("There was a problem writing report of currencies_rate into database. :",true);
					logger.log_report("=============================================",false);
					logger.log_report(content.toString(),false);
					logger.log_report("=============================================",false);
				}
			}
			logger.log_report("Successfully FINISHED updating the currencies_rate table..",true);
			con.close();
		} catch (Exception e) {
			StringWriter content = new StringWriter();
			e.printStackTrace(new PrintWriter(content));
			logger.log_report("There was a problem when updating the currencies_rate table. :",true);
			logger.log_report("=============================================",false);
			logger.log_report(content.toString(),false);
			logger.log_report("=============================================",false);
			try {
				Report_lib.store_report_to_DB(report_db,"There was an error when trying to update currencies_rate table. Please check the log file.");
			}catch (Exception e1) {
				content = new StringWriter();
				e1.printStackTrace(new PrintWriter(content));
				logger.log_report("There was a problem writing report of currencies_rate into database. :",true);
				logger.log_report("=============================================",false);
				logger.log_report(content.toString(),false);
				logger.log_report("=============================================",false);
			}  
		}
	}
	
	/**
	 * 
	 * @param report_db
	 * @param db
	 * @param response_json
	 * @param logger
	 * @throws Exception
	 */
	private static void parseServerResponse(DB report_db,DB db,JSONObject response_json,Logger logger) throws Exception{
		boolean success_result = (Boolean) response_json.get("success");
		if(success_result){
			JSONObject rates = (JSONObject) response_json.get("rates");
			update_currencies_table(db,rates);
		}else{
			int error_code = (Integer) ((JSONObject)response_json.get("error")).get("code");
			String error_msg = (String) ((JSONObject)response_json.get("error")).get("info");
			throw new Exception("Error with code = [ "+ error_code+" ] and message ["+error_msg+"] prevented updating currencies_rate table.");
		}
    }
	
	/**
	 * 
	 * @param db
	 * @param rates
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static void update_currencies_table(DB db,JSONObject rates) throws Exception{
		String query_update_rates = "INSERT INTO currencies_rates(currency_name,rate) VALUES(?,?);";
		Connection con = db.getConnection();
		PreparedStatement ps = con.prepareStatement(query_update_rates);
		Iterator<String> currencies = rates.keySet().iterator();
		while(currencies.hasNext()){
			String name = currencies.next();
			if(name.equals("EUR")){
				continue;
			}
			//System.out.println(name);
			double rate = (Double) rates.get(name);
			ps.setString(1,name);
			ps.setDouble(2,rate);
			ps.addBatch();
		}
		ps.executeBatch();
		con.close();
	}
}
