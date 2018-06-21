package utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main{

	//accepts an in-line parameter firstTime, if true it is telling the system this is the first time 
	// the database is being populated.
	public static void main(String[] args)  {
		
		boolean is_first_time = args[0].split("=")[1].equalsIgnoreCase("true");
		UpdateIndexes uidx;
		UpdateToken tk = null;
		DB db = new DB("jdbc:mysql://localhost:3306/bwwd_app_stats","main_root","551360bwwd");
		//DB db = new DB("jdbc:mysql://bwwd.csaafepy4o0c.us-east-1.rds.amazonaws.com:3306/bwwd_app_stats","main_root","551360bwwd");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date new_date = new Date();
		String log_file_url = df.format(new_date)+"-file.log";
		//Logger logger = new Logger("/home/ubuntu/bwwd/HomeawayUpdater/"+log_file_url);
		Logger logger = new Logger("./"+log_file_url);
		logger.log_report("STARTING TO UPDATE THE DATABASE!!!",true);
		try {
			    tk = new UpdateToken("jdbc:mysql://localhost:3306/bwwd_admin","main_root","551360bwwd", "Homeaway");
			    //tk = new UpdateToken("jdbc:mysql://bwwd.csaafepy4o0c.us-east-1.rds.amazonaws.com:3306/bwwd_admin","main_root","551360bwwd", "Homeaway");
				int i = tk.isTokenValid();
				System.out.println("=====> "+ i);
				if( i == 0){
				   tk.updateToken(true);
				}else if( i < 0 ){
				   tk.updateToken(false);
				}
				try{
				     Report_lib.store_report_to_DB(db,"The authentication token was checked and updated successfully for ( Homeaway )");
				}catch(Exception e){
					 StringWriter content = new StringWriter();
					 e.printStackTrace(new PrintWriter(content));
					 logger.log_report("There was a problem writing report of token authentication into database. :",true);
					 logger.log_report("=============================================",false);
					 logger.log_report(content.toString(),false);
					 logger.log_report("=============================================",false);
							 
				}
		}catch(Exception e){ // if there is issue initializing update_token or updating the token report and return.
			StringWriter content = new StringWriter();
			e.printStackTrace(new PrintWriter(content));
			logger.log_report("There was a problem when updating token , homeaway_update is EXITTING. :",true);
			logger.log_report("=============================================",false);
			logger.log_report(content.toString(),false);
			logger.log_report("=============================================",false);
		 	
			try {
				Report_lib.store_report_to_DB(db,"There was an error when trying to update authentication token for ( Homeaway ). Please check the log file.");
			}catch (Exception e1) {
				content = new StringWriter();
				e1.printStackTrace(new PrintWriter(content));
				logger.log_report("There was a problem writing report of token authentication into database. :",true);
				logger.log_report("=============================================",false);
				logger.log_report(content.toString(),false);
				logger.log_report("=============================================",false);
			}
			return;
		}
		
		//=====  this is where you update indexes ========
		try{
			String   token = tk.getToken();
			// if this is the very first time the database is being populated.
			if ( is_first_time ) {
				    uidx = new UpdateIndexes(token,"https://channel.homeaway.com/channel/vacationRentalIndexFeed?"
                         + "fromDate=2015-04-21T00:00:00.000Z&paged=true&pageSize=10&_restfully=true",
                         "https://channel.homeaway.com/channel/vacationRentalAdvertisement?_restfully=true&unitUrl=",logger);
			}else{
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				Date new_date1 = new Date();
				Date past_date1 = new Date(new_date1.getTime() - 86400000);
								
				uidx = new UpdateIndexes(token,"https://channel.homeaway.com/channel/vacationRentalIndexFeed?"
                        + "fromDate="+df1.format(past_date1)+"&paged=true&pageSize=10&_restfully=true",
                        "https://channel.homeaway.com/channel/vacationRentalAdvertisement?_restfully=true&unitUrl=",logger);
			}
			uidx.update_unit_idxs_details_database(is_first_time);
			
		 } catch (Exception e) {
			StringWriter content = new StringWriter();
			e.printStackTrace(new PrintWriter(content));
			//DB db1 = new DB("jdbc:mysql://mysql3000.mochahost.com:3306/bwwd_app_stats","main_root","551360");
			logger.log_report("The homeaway_update is EXITING :",true);
			logger.log_report("=============================================",false);
			logger.log_report(content.toString(),false);
			logger.log_report("=============================================",false);
			try {
				Report_lib.store_report_to_DB(db,"There was a major error which caused homeaway_update program to shutdown.Please check the log file.");
			}catch (Exception e1) {
				content = new StringWriter();
				e1.printStackTrace(new PrintWriter(content));
				logger.log_report(content.toString(),true);
			}
		}
	    /*
		DB db = new DB("jdbc:mysql://bwwd.csaafepy4o0c.us-east-1.rds.amazonaws.com:3306/bwwd_partners_homeaway","main_root","551360bwwd");
		try {
			Connection con = db.getConnection();
			String add_new_headlines = "INSERT INTO homeaway_property_headlines(property_id,locale,property_headline) VALUES(?,?,?);";
			PreparedStatement ps = con.prepareStatement(add_new_headlines);
			ps.setLong(1,1);
			ps.setString(2,"pm");
			ps.setString(3,"🌴 ADMEMPTED 🌊 SVEMBAD OG RETRAKTIV BELØB, CONDOMINIUM 150M FRA STRANDEN!");
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
			
		//Email em = new Email("email-smtp.us-east-1.amazonaws.com","AKIAJ7XTXW2FQJQYCGQA","AqX5idr5aZ5iuwLvNQJ03WTOkp0TofepGB68EUGjvmlK",587);
		//em.sendEmail("business@bestworldwidedeals.com","Hossein Fazeli","Hossein_free@hotmail.com","Hello from hossein","HEllo EVERY BODY","text/html");
		
    }
    
	
}
