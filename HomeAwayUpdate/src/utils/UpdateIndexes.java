package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UpdateIndexes {
	private String token;
	private String target_url;
	private UpdateDetails ud;
	private Logger logger;
	private Map<JSONObject,Boolean> unsuccess_units ;
	
	public UpdateIndexes(String token,String target_url,String details_target_url,Logger logger ){
		  this.setToken(token);
		  this.setTarget_url(target_url);
		  this.setUd(new UpdateDetails(token,details_target_url));
		  this.setLogger(logger);
		  this.setUnsuccess_units(new HashMap<JSONObject,Boolean>());
    }
		
	private Map<JSONObject, Boolean> getUnsuccess_units() {
		return unsuccess_units;
	}

	private void setUnsuccess_units(Map<JSONObject, Boolean> unsuccess_units) {
		this.unsuccess_units = unsuccess_units;
	}

	private UpdateDetails getUd() {
		return ud;
	}

	private void setUd(UpdateDetails ud) {
		this.ud = ud;
	}

	private String getTarget_url() {
		return target_url;
	}

	private void setTarget_url(String target_url) {
		this.target_url = target_url;
	}

	private String getToken() {
		return token;
	}

	private void setToken(String token) {
		this.token = token;
	}
	
	private Logger getLogger() {
		return logger;
	}

	private void setLogger(Logger logger) {
		this.logger = logger;
	}

    
	/**
	 * gets indexes from server. 
	 * @param target_url, UR to be used to get indexes info from the server.
	 * @return, JSONObject, returns a JSON object con
	 * @throws Exception
	 */
	private JSONObject get_Idxs_From_Source(String target_url) throws Exception{
		URL url = new URL(target_url);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer "+this.getToken());
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(response.toString());
		    return (JSONObject) obj;
		}else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
			this.getLogger().log_report("Server Returned 401 code. Trying to get new Token.", true);
		    // it is possible that the token is expired. try to renew it.
			//UpdateToken tk = new UpdateToken("jdbc:mysql://bwwd.csaafepy4o0c.us-east-1.rds.amazonaws.com:3306/bwwd_admin","main_root","551360bwwd", "Homeaway");
			UpdateToken tk = new UpdateToken("jdbc:mysql://localhost:3306/bwwd_admin","main_root","551360bwwd", "Homeaway");
			int i = tk.isTokenValid();
			if( i >= 0){
			   tk.updateToken(true);
			}else if( i < 0 ){
			   tk.updateToken(false);
			}
			this.setToken(tk.getToken());
			if( tk.isTokenValid() > 0 ){
				this.getLogger().log_report("======================================================", false);
				this.getLogger().log_report("Token was successfully obtained.", false);
			}
			throw new Exception("The server respond with error number = "+responseCode);
		}else {
			throw new Exception("The server respond with error number = "+responseCode);
		}
	}
	
	/**
	 * This method gets batch of 10 index feeds from the server. After that, for each index it updates
	 * corresponding tables in database using method update_unit_info(). this method continues as long as
	 * the link for next batch of data is provided. 
	 * 
	 * @param is_first_time, whether this is the very first time database is being populated or not.
	 * @throws Exception, if some serious issue raises that prevents update from continuing.
	 *
	 */
	public void update_unit_idxs_details_database(boolean is_first_time) throws Exception{
		boolean there_is_idx = false;
		DB db = new DB(DB.DB_base_url+"bwwd_partners_homeaway","main_root","551360bwwd");
		JSONObject batch_of_idxs;
		
		try{
		    batch_of_idxs = (JSONObject) this.get_Idxs_From_Source(this.getTarget_url());
		}catch(Exception e){
			JSONObject temp_batch_of_idxs = null;
			temp_batch_of_idxs = this.retry_to_get_idx_from_server(this.getTarget_url());
			if(temp_batch_of_idxs != null){ // if retry was successful.
				batch_of_idxs = temp_batch_of_idxs;
			}else{
				StringWriter err_content = new StringWriter();
				e.printStackTrace(new PrintWriter(err_content));
				this.getLogger().log_report("=============================================",false);
				this.getLogger().log_report(err_content.toString(),false);
				this.getLogger().log_report("=============================================",false);
				throw new Exception("ALL ATTEMPTS TO RETRY TO GET CONNECTED TO SERVER FAILD.");
			}
		}
		    
		JSONArray idxs_objs = (JSONArray) batch_of_idxs.get("entries");
		if(idxs_objs.size() > 0 ){
			there_is_idx = true;
		}
		int counter = 0 ;
		int err_counter = 0 ;
		int enabled_counter = 0 ;
		Date start_date = new Date();
		while( there_is_idx ){
	    	
	    	String next_url = null;
	    	if(batch_of_idxs.containsKey("nextResults")){
	    	   //System.out.println((String) batch_of_idxs.get("nextResults"));	
	    	   next_url = (String) batch_of_idxs.get("nextResults");
	    	}   
            for(int i=0; i < idxs_objs.size();i++){
            	counter += 1;
            	JSONObject curent_unit_obj = (JSONObject) idxs_objs.get(i);
            	if((boolean) curent_unit_obj.get("enabled")){
    				enabled_counter += 1 ;
    			}
            	try {
            		update_unit_info(curent_unit_obj,is_first_time,db);
            	} catch (Exception e) { //if update was not successful for this unit skip this unit
					     err_counter += 1;
						 String unit_url = (String) curent_unit_obj.get("unitUrl");
						 this.getUnsuccess_units().put(curent_unit_obj,false); // add unit to map to be fixed later.
						 this.disable_unit(unit_url, db);
						 StringWriter err_content = new StringWriter();
						 e.printStackTrace(new PrintWriter(err_content));
						 this.getLogger().log_report("The unit with unit_url ["+unit_url+"] could not be updated:",true);
						 this.getLogger().log_report("=============================================",false);
						 this.getLogger().log_report(err_content.toString(),false);
						 this.getLogger().log_report("=============================================",false);
						 
						 // remove the unit records from database
						 try {
							Connection con = db.getConnection();
							this.getUd().remove_record(unit_url, con);
							con.close();
						 } catch (Exception e1) { // if the unit records could not be removed
							StringWriter err1_content = new StringWriter();
							e1.printStackTrace(new PrintWriter(err1_content));
							this.getLogger().log_report("The records for unit with unit_url ["+unit_url+"] could not be removed from database:",true);
							this.getLogger().log_report("=============================================",false);
							this.getLogger().log_report(err1_content.toString(),false);
							this.getLogger().log_report("=============================================",false);
					     }
			 	 } // end of first catch
    		}// end of for
            
            if( counter%5000 == 0 ){
            	Date end_date = new Date();
            	/*System.out.println("Total Record : "+counter);
            	System.out.println("Total Errors : "+err_counter);
            	System.out.println("Time Taken So Far: "+ Math.abs((start_date.getTime() - end_date.getTime()))/1000+" Seconds");
            	System.out.println("===================");*/
            	this.getLogger().log_report("Total Record : "+counter+" ==== "+"Total Errors : "+err_counter+" ==== "+
            			"Time Taken So Far: "+ Math.abs((start_date.getTime() - end_date.getTime()))/1000+" Seconds",true);
            }
           if( counter == 100000 ){
            	Date end_date = new Date();
            	this.getLogger().log_report("FINISHED UPDATING!",true);
            	this.getLogger().log_report("=================================",false);
            	this.getLogger().log_report("Total Record : "+counter,false);
            	this.getLogger().log_report("Total Errors : "+err_counter,false);
            	this.getLogger().log_report("Total Enabled Record : "+enabled_counter,false);
            	this.getLogger().log_report("Time Taken : "+ Math.abs((start_date.getTime() - end_date.getTime()))/1000+" Seconds",false);
            	return;
            }
            // must finish processing all entries of current at this point
            // if there is an error getting batch of indexes from the server, we try again twice
            // with 4 minutes delay between 
			if( next_url != null && next_url.trim().length() > 0 ){
				try{
				      batch_of_idxs = (JSONObject) this.get_Idxs_From_Source("https://channel.homeaway.com"+next_url);
				}catch(Exception e){// if there was a problem to get batch of idxs from the server retry
					JSONObject temp_batch_of_idxs = null;
					temp_batch_of_idxs = this.retry_to_get_idx_from_server("https://channel.homeaway.com"+next_url);
					if(temp_batch_of_idxs != null){ // if retry was successful.
						batch_of_idxs = temp_batch_of_idxs;
					}else{
						StringWriter err_content = new StringWriter();
						e.printStackTrace(new PrintWriter(err_content));
						this.getLogger().log_report("=============================================",false);
						this.getLogger().log_report(err_content.toString(),false);
						this.getLogger().log_report("=============================================",false);
						throw new Exception("ALL ATTEMPTS TO RETRY TO GET CONNECTED TO SERVER FAILD.");
					}
				}
				idxs_objs = (JSONArray) batch_of_idxs.get("entries");
				if( ! ( idxs_objs.size() > 0 ) ){
					there_is_idx = false;
				}
			}else{
				there_is_idx = false;
			}
			//there_is_idx = false;
		} // End of while
		
		
		if(this.getUnsuccess_units().keySet().size() <= 0 ){
			return;
		}
		// now try to populate records for the units saved in unsuccessful units
		this.getLogger().log_report("STARTING TO UPDATE FAILED UNITS.",true);
		counter = 0;
		Iterator<JSONObject> it  = this.getUnsuccess_units().keySet().iterator();
		while(it.hasNext()){
			JSONObject temp_obj = it.next();	
			try{
			   update_unit_info(temp_obj,is_first_time,db);
			   this.getUnsuccess_units().put(temp_obj, true);
			   counter += 1 ;
			}catch(Exception e){
				 String unit_url = (String) temp_obj.get("unitUrl");
				 this.disable_unit(unit_url, db);
				 StringWriter err_content = new StringWriter();
				 e.printStackTrace(new PrintWriter(err_content));
				 this.getLogger().log_report("The second attemp to get records for unit with unit_url ["+unit_url+"] failed:",true);
				 this.getLogger().log_report("=============================================",false);
				 this.getLogger().log_report(err_content.toString(),false);
				 this.getLogger().log_report("=============================================",false);
				 
			}
		}
		this.getLogger().log_report("  "+counter+" of failed unit were fixed. GOOD BYE.",true);
	}
	
	/**
	 * The method retries to connect to the server and get batch of idxs if there was
	 * a failure in initial call. It tries twice with 4 minutes wait time between.
	 * 
	 * @param target_url, url to be used to get idxs from the server
	 * @return, JSONObject if successful, null if unsuccessful
	 */
	private JSONObject retry_to_get_idx_from_server(String target_url){
		
		this.getLogger().log_report("  THERE WAS A PROBLEM GETTING IDXs FROM SERVER FOR TARGET_URL ["+target_url+"]",true);
		this.getLogger().log_report("RETRYING TO GET IDXS",false);
		JSONObject result = null;
		for(int i = 0; i < 2 ; i++){
			try{
				Thread.sleep(240000); //delay for 4 minutes
			}catch(Exception e){
				return null;
			}
			this.getLogger().log_report("====================================================",false);
			this.getLogger().log_report("Attempt "+(i+1)+" STARTING.",true);
			try {
				result = this.get_Idxs_From_Source(target_url);
				if( result != null ){ // success
				   this.getLogger().log_report("Attempt "+(i+1)+" Was Successfull, RESUMING THE UPDATING PROCESS.",false);
				   return result; 	
				}else{ // failure
					this.getLogger().log_report("Attempt "+(i+1)+" Failed.",false);
				}
			} catch (Exception e) { // failure
				StringWriter err_content = new StringWriter();
				e.printStackTrace(new PrintWriter(err_content));
				this.getLogger().log_report("Attempt "+(i+1)+" Failed.",false);
				this.getLogger().log_report("=============================================",false);
				this.getLogger().log_report(err_content.toString(),false);
				this.getLogger().log_report("=============================================",false);
			}
			
		}//end of for
		// We failed completely
		this.getLogger().log_report("ALL ATTEMPTS TO GET THE IDXS FAILED . EXITING ",true);
		return null;
	}
	
	/**
	 * This method uses methods update_unit_idx() and update_unit_details() (from UpdateDetails class)
	 * to update information for each unit in database.
	 * 
	 * @param unit_json_obj, JSON object containing all the info for the unit.
	 * @param is_first_time, tells us if this is the very first time we are populating the database or not.
	 * @throws Exception
	 */
	private void update_unit_info(JSONObject unit_json_obj,boolean is_first_time,DB db) throws Exception{
	   //DB db = new DB(DB.DB_base_url+"bwwd_partners_homeaway?characterEncoding=utf8","root","551360");	
	   this.update_unit_idx(db, unit_json_obj,is_first_time);
	   String unit_url = (String) unit_json_obj.get("unitUrl");
	   if ( (boolean) unit_json_obj.get("enabled") ){ // only if the unit is enabled.
	       this.ud.update_unit_details(unit_url,db);
	   }
	   
	}
	
	
	private void disable_unit(String unit_url,DB db){
		String disable_unit = "UPDATE homeaway_unit_idx_feeds SET enabled  = false WHERE unitUrl = ?;";
		Connection con;
		try {
			con = db.getConnection();
		 	PreparedStatement ps = con.prepareStatement(disable_unit);
		    ps.setString(1,unit_url);
		    ps.executeUpdate();
		    ps.close();
		    return;
		} catch (Exception e) {
			StringWriter err_content = new StringWriter();
			e.printStackTrace(new PrintWriter(err_content));
			this.getLogger().log_report("The unit with unit_url ["+unit_url+"] could not be disabled:",true);
			this.getLogger().log_report("=============================================",false);
			this.getLogger().log_report(err_content.toString(),false);
			this.getLogger().log_report("=============================================",false);
			 
			return;
	    }
	}
	/**
	 * This method updates tables 1-homeaway_unit_idx_feeds and 2-homeaway_unit_exits,
	 * if a record is completely new then it just adds records to both tables.
	 * if an previously enabled unit is disabled now, it just updates table 1 and removes
	 * record for this unit from table 2. if an previously disabled unit is now enabled
	 * , it just updates data in table 1 and removes record for this record from table 2.
	 * 
	 * @param db, instance of DataBase object
	 * @param unit_idx_json_obj
	 * @param is_first_time, this one indicates if it is the very first time, we are populating this database
	 * @throws Exception
	 */
	private void update_unit_idx(DB db,JSONObject unit_idx_json_obj,boolean is_first_time) throws Exception{
		
		String update_existing_idx = "UPDATE homeaway_unit_idx_feeds SET listingUrl = ?, unitUrl= ?,enabled=? WHERE unit_id = ?;";
		String insert_new_idx = "INSERT INTO homeaway_unit_idx_feeds(unit_id,listingUrl,unitUrl,enabled) VALUES(?,?,?,?);";
		String check_enabled_record_exist = "SELECT record_id FROM homeaway_unit_exists WHERE unit_id = (SELECT record_id FROM "
				                             + "homeaway_unit_idx_feeds WHERE unit_id = ?);";
		String insert_new_updated_exist_record = "INSERT INTO homeaway_unit_exists(unit_id) VALUES((SELECT record_id FROM "
				                             + "homeaway_unit_idx_feeds WHERE unit_id = ?));";
		String insert_new_exist_record = "INSERT INTO homeaway_unit_exists(unit_id) VALUES(?);";
		String delete_disabled_existing_unit = "DELETE FROM homeaway_unit_exists WHERE unit_id = (SELECT record_id FROM "
												+ "homeaway_unit_idx_feeds WHERE unit_id = ?);";
		String cols[] = {"record_id"};
		
		String id = (String) unit_idx_json_obj.get("id");
		String listing_url = (String) unit_idx_json_obj.get("listingUrl");
		String unit_url = (String) unit_idx_json_obj.get("unitUrl");
		boolean enabled = (boolean) unit_idx_json_obj.get("enabled");
		
		Connection con = db.getConnection();
		PreparedStatement ps = con.prepareStatement(update_existing_idx,cols);
		ps.setString(1,listing_url);
		ps.setString(2,unit_url);
		ps.setBoolean(3,enabled);
		ps.setString(4,id);
		   
		int rows_effected = ps.executeUpdate();
		ResultSet rs;
		
		// the id was not in database, this is new unit
		if( rows_effected <= 0 ){ 
		   ps.close();	
		   ps = con.prepareStatement(insert_new_idx,cols);
		   ps.setString(1,id);
		   ps.setString(2,listing_url);
		   ps.setString(3,unit_url);
		   ps.setBoolean(4,enabled);
		   int result = ps.executeUpdate();
		   rs = ps.getGeneratedKeys();
		   // if data was not entered to database
		   if ( result <= 0 ){ 
			    con.close();
				throw new Exception("could not insert index information for unit => "+unit_url);
		   }
		   // if the unit is new and enabled then enter a new record into unit_exists table.
		   if(enabled){
			   int unit_record_id = -1;
		   	   if (rs.next()){
		   		   unit_record_id = rs.getInt(1);
		   	   }
		   	   // if there was record_id returned
		   	   if(unit_record_id >= 0 ){
		   		   ps.close();
		   		   ps = con.prepareStatement(insert_new_exist_record,cols);
				   ps.setInt(1,unit_record_id);
				   ps.executeUpdate();
				   
			   }else{
				   con.close();
				   throw new Exception("could not insert existance information to homeaway_unit_exists table for unit => "+unit_url);
			   }
		   }
		   con.close();
		   return;
		 }
		 // check if this is the very first time the database is being populated ignore following lines of codes.
		 // if the unit existed and is disabled, delete all the records from homeaway_unit_exits.
		 if( !is_first_time && !enabled ){   // if an existing enabled unit was disabled
			 ps.close();
			 ps = con.prepareStatement(delete_disabled_existing_unit);
			 ps.setString(1,id);
			 ps.executeUpdate();
			 
         }else if(!is_first_time && enabled){ //if an existing previously disabled/enabled unit was enabled
        	 ps.close();
			 ps = con.prepareStatement(check_enabled_record_exist);
			 ps.setString(1,id);
			 rs = ps.executeQuery();
			 if(!rs.next()){ // there is no record, add new record
				 ps.close();
				 ps = con.prepareStatement(insert_new_updated_exist_record);
				 ps.setString(1,id);
				 ps.executeUpdate();
			 }
			 //################# TEST ####################
			 /*else{ // if there is record delete and add again ADDED
				 ps.close();
				 ps = con.prepareStatement(delete_disabled_existing_unit);
				 ps.setString(1,id);
				 ps.executeUpdate();
				 ps.close();
				 ps = con.prepareStatement(insert_new_updated_exist_record);
				 ps.setString(1,id);
				 ps.executeUpdate();
			 }*/
		  }
		  ps.close();
		  con.close();
		  return;
	}
}
