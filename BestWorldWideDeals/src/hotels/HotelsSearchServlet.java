package hotels;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import hotels.hotelsSuppliers.HomeAway;
import hotels.utils.SearchObject;
import utils.DB;

public class HotelsSearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONParser parser = new JSONParser();
		System.out.println(req.getParameter("data"));
		
		try{
			Date start = new Date();
			JSONObject obj = (JSONObject) parser.parse(req.getParameter("data"));
			SearchObject searchObj = new SearchObject(obj);
			HomeAway hw = new HomeAway(searchObj);
			hw.getSearchResults(70);
			Date end = new Date();
			System.out.println( "Time Taken : "+(end.getTime() - start.getTime()) /1000+" Seconds");
			//System.out.println(this.getCurrency_rates().toString());
		}catch(Exception e){
		   e.printStackTrace();
	    }
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONArray getCurrency_rates() throws Exception {
		Map<String,Double> currency_rates = new HashMap<String,Double>();
		JSONArray final_result = new JSONArray();
		String get_exchanges = "SELECT currency_name,rate FROM currencies_rates;";
		DB db = new DB("jdbc/bwwd_systemDB");
		Connection con = db.getConnection();
		PreparedStatement ps = con.prepareStatement(get_exchanges);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
		  currency_rates.put(rs.getString(1),rs.getDouble(2));
		}
		// now onvert EUR based to USD based
		double usd_rate = 1 / currency_rates.get("USD");
		currency_rates.remove("USD");
		Iterator<String> keys = currency_rates.keySet().iterator();
		while(keys.hasNext()){
			 String key = keys.next();
			 JSONObject tmp_obj = new JSONObject();
			 tmp_obj.put(key,usd_rate*currency_rates.get(key));
			 final_result.add(tmp_obj);
		}
		JSONObject tmp_obj = new JSONObject();
		tmp_obj.put("EUR",usd_rate);
		final_result.add(tmp_obj);
		return final_result;
	}
	
	
	//private JSONArray getSearchResults(JSONObject info_obj){
		
		
	//}
	
	
}
