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
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UpdateDetails {

	private String token;
	private String target_url;
	
	public UpdateDetails(String token,String target_url ){
		  this.setToken(token);
		  this.setTarget_url(target_url);
    }
		
	@SuppressWarnings("unused")
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
	
	/**
	 * The method send a HTTP request to the server to get data for the unit with unique
	 * URL unit_url
	 * @param unit_url, unique identifier for the unit we want to get data for.
	 * @return, JSONObject, containing data for the unit in Json format.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONObject get_Unit_Details_From_Source(String unit_url) throws Exception{
		URL url = new URL(target_url+unit_url);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer "+this.getToken());
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		int responseCode = con.getResponseCode();
		JSONObject result = new JSONObject();
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
			result.put("result","OK");
			result.put("data",obj);
		} else {
			result.put("result",Integer.toString(responseCode));
			result.put("data",null);
		}
	    return result;
	}
	
	
	/**
	 * The method returns a unit_id for the unit in homeaway_unit_exists table.
	 * 
	 * @param unit_url, unique identifier for the unit we are interested in.
	 * @param con, Connection object.
	 * @return, Integer, the unit_id in homeaway_unit_exists corresponding to this unit.
	 * @throws Exception
	 */
	private long get_unit_exist_code(String unit_url,Connection con) throws Exception{
		
		String get_unit_code_exists = "SELECT record_id FROM homeaway_unit_exists WHERE unit_id = "
			                          + "( SELECT record_id FROM homeaway_unit_idx_feeds WHERE unitUrl = ?);"; 
		PreparedStatement ps = con.prepareStatement(get_unit_code_exists);
		ps.setString(1,unit_url);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			long unit_exist_id = rs.getLong(1);
			ps.close();
			return unit_exist_id;
		}else{
			ps.close();
			throw new Exception("Could not get the record_id from homeaway_unit_exists for the unit = "+unit_url);
		}
	}

	/**
	 * This method gets data for the unit, and takes a proper action when server returns an
	 * error code.
	 * @param unit_url, the unique unit url used to get data from server for this unit.
	 * @throws Exception
	 */
	public void update_unit_details(String unit_url,DB db) throws Exception{
		JSONObject result_obj = this.get_Unit_Details_From_Source(unit_url);
		JSONObject unit_details_obj ; 
		
		//====================== TEST ===============
	    	/*FileWriter fw = new FileWriter("c:/users/hosse/desktop/bwwd_test_json.json",true);
			BufferedWriter bf = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bf);
			out.println(result_obj.toString());
			out.close();*/ 
		//====================== TEST ===============
		String result_status = (String) result_obj.get("result");
        // if the server successfully returns data	
		if(result_status.trim().equals("OK")){
			long unit_id;
			Connection con = db.getConnection();
			try {
				con.setAutoCommit(false);
				unit_id = get_unit_exist_code(unit_url,con);
				unit_details_obj = (JSONObject) result_obj.get("data");
				this.parse_apply_details_changes(unit_details_obj,unit_url,con,unit_id);
				con.commit();
				con.setAutoCommit(true);
				con.close();
			} catch (Exception e) {
				con.rollback(); // undo all the changes related to this record in database.
				StringWriter err_content = new StringWriter();
				e.printStackTrace(new PrintWriter(err_content));
				throw new Exception(err_content.toString());
			}
		}else if(result_status.trim().equals("404")){
			//you need to remove all the details for this unit and set enabled to "false" for this unit.
			Connection con = db.getConnection();
			this.disable_unit(unit_url, con);
			this.remove_record(unit_url, con);
			con.close();
		}else{
			Connection con = db.getConnection();
			this.disable_unit(unit_url, con);
			this.remove_record(unit_url, con);
			con.close();
			throw new Exception("Server Error Code = "+result_status);
		}
	}
	
	/**
	 * 
	 * @param unit_url
	 * @param con
	 * @throws Exception
	 */
	private void disable_unit(String unit_url,Connection con) throws Exception{
		String disable_unit = "UPDATE homeaway_unit_idx_feeds SET enabled  = false WHERE unitUrl = ?;";
		PreparedStatement ps = con.prepareStatement(disable_unit);
		ps.setString(1,unit_url);
		ps.executeUpdate();
		ps.close();
		return;
	}
	
	/**
	 * Removes record for unit with unique identifier unit_url from table homeaway_unit_exists,which 
	 * consequently removes all the records in all the tables for this unit.
	 * 
	 * @param unit_url, unique identifier for this unit.
	 * @param con, Connection object.
	 * @throws Exception
	 */
	 public void remove_record(String unit_url,Connection con) throws Exception{
		long u_id = get_unit_exist_code(unit_url,con);
		String remove_records = "DELETE FROM homeaway_unit_exists WHERE unit_id = ?;";
		PreparedStatement ps = con.prepareStatement(remove_records);
		ps.setLong(1,u_id);
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * This method parses the JSON object containing information for the property
	 * and then applies updates into corresponding tables in database.
	 * 
	 * @param unit_details_obj, json object containing data for the current property .
	 * @param unit_url, unique URL for this property
	 * @param con
	 * @param unit_id, property_id in table  homeaway_unit_exists 
	 * @throws Exception, throws exception if any of the sub methods throws exception and rolls back
	 *                    all the changes to the database.
	 */
    private void parse_apply_details_changes(JSONObject unit_details_obj,String unit_url,Connection con,long unit_id) throws Exception{
		 this.update_property_urls((JSONObject) unit_details_obj.get("propertyDetailsUrls"),unit_id,con);
		 this.update_ad_content((JSONObject) unit_details_obj.get("adContent"), unit_id, con);
		 this.update_address((JSONObject) unit_details_obj.get("location"), unit_id, con);
		 this.update_photos((JSONArray) unit_details_obj.get("photos"),false, unit_id, con); // update regular photos
		 this.update_photos((JSONArray) unit_details_obj.get("thumbnails"),true, unit_id, con); // update thumbnails
		 JSONArray unit_objs = (JSONArray) unit_details_obj.get("units");
		 boolean is_feature_record_removed = false;
		 if(unit_details_obj.containsKey("featureValues")){
			 is_feature_record_removed = true;
			 this.update_features((JSONArray) unit_details_obj.get("featureValues"),true, unit_id, con); // update feature values.
		 }
		 //find the unit for the current unit url , since there are some multi unit listings.
		 JSONObject temp_unit_obj = this.getTheUnit(unit_objs, unit_url);
		 if(temp_unit_obj != null ){
			 if( temp_unit_obj.containsKey("featureValues") ){
				 if( is_feature_record_removed )
				    this.update_features((JSONArray) temp_unit_obj.get("featureValues"), false,unit_id, con); // update feature values from the unit object.
				 else
					this.update_features((JSONArray) temp_unit_obj.get("featureValues"), true,unit_id, con); // update feature values from the unit object.
			 } 
		 }else{
			 throw new Exception("the unit object for this unit url was not found. unit_url => "+unit_url);
		 }		 
		 
		 this.update_unit(temp_unit_obj, con, unit_id);
		 
	}
	
	/**
	 * This method removes all the existing url records for the specified property,
	 * and after that, it inserts the current urls to database.
	 * 
	 * @param property_urls, JSON object containing pairs of locale,url for unit
	 * @param unit_id, the id assigned to this property in homeaway_unit_exists table
	 * @param con, Connection object
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void update_property_urls(JSONObject property_urls,long unit_id,Connection con) throws Exception{
		 String remove_all_existing_url = "DELETE FROM homeaway_property_details_urls WHERE property_id = ?;";
		 String add_new_urls = "INSERT INTO homeaway_property_details_urls(property_id,locale,pr_url) VALUES(?,?,?);";
		 PreparedStatement ps = con.prepareStatement(remove_all_existing_url);
		 ps.setLong(1,unit_id);
		 ps.executeUpdate();
		 ps.close();
		 ps = con.prepareStatement(add_new_urls);
		 
		 Iterator it = property_urls.keySet().iterator();
		 while(it.hasNext()){
			 String locale_key = (String) it.next();
			 ps.setLong(1,unit_id);
			 ps.setString(2,locale_key);
			 ps.setString(3,(String) property_urls.get(locale_key));
			 ps.addBatch();
		 }
		 ps.executeBatch();
		 ps.close();
	}
	
	/**
	 * This method uses three other methods to update adContent including (description,headline,
	 * and further details if applicable).
	 * 
	 * @param ad_content, Json object containing (description,headline,furtherDetails)
	 * @param unit_id, id of unit in homeaway_unit_exists
	 * @param con, Connection object
	 * @throws Exception 
	 */
	private void update_ad_content(JSONObject ad_content,long unit_id,Connection con) throws Exception{
		
		if( ad_content.containsKey("description")){
			JSONObject descript_obj = (JSONObject) ad_content.get("description");
			update_ad_content_description(descript_obj,unit_id,con);
		}
		
		if( ad_content.containsKey("headline")){
			JSONObject headline_obj = (JSONObject) ad_content.get("headline");
			update_ad_content_headline(headline_obj, unit_id, con);
		}
		
		if( ad_content.containsKey("furtherDetails")){
			JSONObject further_details_obj = (JSONObject) ad_content.get("furtherDetails");
			update_ad_content_ferthure_details(further_details_obj, unit_id, con);
		}
	}
	
	/**
	 * This method is used in method update_ad_content to update description of the unit in provided
	 * locals in homeaway_property_descriptions.
	 * 
	 * @param descript_obj, Json object including pairs of locale,content for description of the unit 
	 * @param unit_id, id of property in homeaway_unit_exists
	 * @param con, Connection object 
	 * @throws Exception
	 */
	private void update_ad_content_description(JSONObject descript_obj,long unit_id,Connection con) throws Exception{
		String remove_all_existing_descriptions = "DELETE FROM homeaway_property_description WHERE property_id = ?;";
		String add_new_descriptions = "INSERT INTO homeaway_property_description(property_id,locale,property_description) VALUES(?,?,?);";
		JSONArray description_texts = (JSONArray) descript_obj.get("texts");
				
		//Remove all existing records of descriptions for this unit
		PreparedStatement ps = con.prepareStatement(remove_all_existing_descriptions);
		ps.setLong(1,unit_id);
		ps.executeUpdate();
		ps.close();
		ps = con.prepareStatement(add_new_descriptions);
		
		for(int i=0; i < description_texts.size();i++){
			JSONObject temp_obj = (JSONObject) description_texts.get(i);
			String locale = (String) temp_obj.get("locale");
			String content = (String) temp_obj.get("content");
			if( content.trim().length() > 0 && locale.trim().length() > 0){
				ps.setLong(1,unit_id);
				ps.setString(2,(String) temp_obj.get("locale"));
				ps.setString(3,(String) temp_obj.get("content"));
				ps.addBatch();
			}
		}
		ps.executeBatch();
		ps.close();
	}
	
	/**
	 * This method is used in method update_ad_content to update headlines, of the unit in provided
	 * locals in homeaway_property_descriptions.
	 * 
	 * @param headline_obj,Json object including pairs of locale,content for headline of the unit
	 * @param unit_id, id of property in homeaway_unit_exists
	 * @param con, Connection object 
	 * @throws Exception
	 */
	private void update_ad_content_headline(JSONObject headline_obj,long unit_id,Connection con) throws Exception{
		String remove_all_existing_headlines = "DELETE FROM homeaway_property_headlines WHERE property_id = ?;";
		String add_new_headlines = "INSERT INTO homeaway_property_headlines(property_id,locale,property_headline) VALUES(?,?,?);";
		JSONArray headline_texts = (JSONArray) headline_obj.get("texts");
				
		//Remove all existing records of descriptions for this unit
		PreparedStatement ps = con.prepareStatement(remove_all_existing_headlines);
		ps.setLong(1,unit_id);
		ps.executeUpdate();
		ps.close();
		ps = con.prepareStatement(add_new_headlines);
		
		for(int i=0; i < headline_texts.size();i++){
			JSONObject temp_obj = (JSONObject) headline_texts.get(i);
			String locale = (String) temp_obj.get("locale");
			String content = (String) temp_obj.get("content");
			if( content.trim().length() > 0 && locale.trim().length() > 0){
				ps.setLong(1,unit_id);
				ps.setString(2,(String) temp_obj.get("locale"));
				ps.setString(3,(String) temp_obj.get("content"));
				ps.addBatch();
			}	
		}
		ps.executeBatch();
		ps.close();
	}
	
	/**
	 * This method is used in method update_ad_content to update fethure_details, of the unit in provided
	 * locals in homeaway_property_descriptions.
	 * 
	 * @param fethure_details_obj,Json object including pairs of locale,content for fethure_details of the unit
	 * @param unit_id, id of property in homeaway_unit_exists
	 * @param con, Connection object 
	 * @throws Exception
	 */
	private void update_ad_content_ferthure_details(JSONObject ferthur_details_obj,long unit_id,Connection con) throws Exception{
		String remove_all_existing_fethure_details = "DELETE FROM homeaway_property_further_details WHERE property_id = ?;";
		String add_new_fethure_details = "INSERT INTO homeaway_property_further_details(property_id,locale,further_details) VALUES(?,?,?);";
		JSONArray fethure_details_texts = (JSONArray) ferthur_details_obj.get("texts");
				
		//Remove all existing records of descriptions for this unit
		PreparedStatement ps = con.prepareStatement(remove_all_existing_fethure_details);
		ps.setLong(1,unit_id);
		ps.executeUpdate();
		ps.close();
		ps = con.prepareStatement(add_new_fethure_details);
		
		for(int i=0; i < fethure_details_texts.size();i++){
			JSONObject temp_obj = (JSONObject) fethure_details_texts.get(i);
			String locale = (String) temp_obj.get("locale");
			String content = (String) temp_obj.get("content");
			if( content.trim().length() > 0 && locale.trim().length() > 0){
				ps.setLong(1,unit_id);
				ps.setString(2,locale);
				ps.setString(3,content);
				ps.addBatch();
			}
	    }
		ps.executeBatch();
		ps.close();
	}
	
	/**
	 * This method updates address and coordinates of each unit. 
	 * 
	 * @param location_obj, Json object containing the address and coordinate info for the unit.
	 * @param unit_id, id of property in homeaway_unit_exists
	 * @param con, Connection object 
	 * @throws Exception
	 */
	private void update_address(JSONObject location_obj,long unit_id,Connection con) throws Exception{
		JSONObject addr = (JSONObject) location_obj.get("address");
		JSONObject latlang = (JSONObject) location_obj.get("latLng");
		String remove_existing_address = "DELETE FROM homeaway_property_address WHERE property_id = ?";
		String insert_new_address = "INSERT INTO homeaway_property_address(property_id,addr_1,addr_2,addr_3,city,province,subdivision,country,postal_code,lang,lat)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?);"; 
		//Remove all existing records of descriptions for this unit
		PreparedStatement ps = con.prepareStatement(remove_existing_address);
		ps.setLong(1,unit_id);
		ps.executeUpdate();
		ps.close();
		ps = con.prepareStatement(insert_new_address);
		ps.setLong(1,unit_id);
		ps.setString(2,(String) addr.get("address1"));
		ps.setString(3,(String) addr.get("address2"));
		ps.setString(4,(String) addr.get("address3"));
		ps.setString(5,(String) addr.get("city"));
		ps.setString(6,(String) addr.get("stateProvince"));
		ps.setString(7,(String) addr.get("subdivision"));
		ps.setString(8,(String) addr.get("country"));
		ps.setString(9,(String) addr.get("postalCode"));
		ps.setDouble(10,(Double) latlang.get("longitude"));
		ps.setDouble(11,(Double) latlang.get("latitude"));
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * This method first removes existing photo records for the unit. After that, it enters new records in to 
	 * table homeaway_property_photos. It also uses method add_images to add new images for each property
	 * to table homeaway_property_images.
	 * 
	 * @param photo_objs, JSONArray containing an array of photo objects(each contains a list of images for that photo)
	 * @param unit_id, id of property in homeaway_unit_exists 
	 * @param is_thumbnail, boolean, is this method is being used to update thumbnails or regular photos.
	 * @param con, Connection object
	 * @throws Exception
	 */
	private void update_photos(JSONArray photo_objs,boolean is_thumbnail,long unit_id,Connection con) throws Exception{
		
		String remove_existing_photos = "DELETE FROM homeaway_property_photos WHERE property_id = ? AND is_thumbnail = ?;";
		String add_new_photos = "INSERT INTO homeaway_property_photos(property_id,is_thumbnail) VALUES(?,?);";
		PreparedStatement ps = con.prepareStatement(remove_existing_photos);
		ps.setLong(1,unit_id);
		ps.setString(2, is_thumbnail ? "YES" : "NO");
		ps.executeUpdate();
		ps.close();
		String cols[] = {"record_id"};
		//ps = con.prepareStatement(add_new_photos,cols);
		//ps.setLong(1,unit_id);
		ResultSet rs;
		for(int i = 0 ; i < photo_objs.size() ; i++){
			JSONObject temp_obj = (JSONObject) photo_objs.get(i);
			ps = con.prepareStatement(add_new_photos,cols);
			ps.setLong(1,unit_id);
			ps.setString(2, is_thumbnail ? "YES" : "NO");
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				long record_id = rs.getLong(1);
				if(is_thumbnail){
				   JSONObject temp_photo_obj = (JSONObject) temp_obj.get("photo");	 
				   this.add_images((JSONArray) temp_photo_obj.get("images"),record_id,con);
				}else{
					this.add_images((JSONArray) temp_obj.get("images"),record_id,con);
				}
			}else{
				new Exception("could not enter a photo record for unit = "+unit_id);
			}
			ps.close();
		}
		ps.close();
	}
		
	/**
	 * This method is used by update_photos and update_thumbnails to add new images of each property to 
	 * table homeaway_property_images.
	 * 
	 * @param image_objs, List of JSONObject, containing info about the image.
	 * @param is_thumbnail, boolean, if this images are thumb nails or not.
	 * @param photo_group_id, record id from table homeaway_property_photos
	 * @param con, Connection object
	 * @throws Exception
	 */
	private void add_images(JSONArray image_objs,long photo_group_id,Connection con) throws Exception{
		
		String add_new_images = "INSERT INTO homeaway_property_images(photo_group_id,image_url,treatment,width,height) VALUES(?,?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(add_new_images);
		
		for(int i = 0 ; i < image_objs.size() ; i++){
			JSONObject temp_obj = (JSONObject) image_objs.get(i); 
			ps.setLong(1,photo_group_id);
			ps.setString(2,(String) temp_obj.get("uri"));
			ps.setString(3,(String) temp_obj.get("treatment"));
			JSONObject dimension = (JSONObject) temp_obj.get("dimension");
			ps.setLong(4, (Long) dimension.get("width"));
			ps.setLong(5, (Long) dimension.get("height"));
			ps.addBatch();
		}
		
		ps.executeBatch();
		ps.close();
	}
	
	/**
	 * This method uses two other methods getFeatureLabel() and parse_Update_Feature(). This method 
	 * Deletes all existing records of feature labels and names for this unit first. After that, It inserts
	 * new records for this record into corresponding tables.
	 * 
	 * @param features_objs, Json Object containing info for features offered for this unit.
	 * @param unit_id, id for this property in table homeaway_unit_exists
	 * @param remove_records, if true remove all the existing records in the table homeaway_property_feature_labels
	 * @param con, Connection object
	 * @throws Exception
	 */
	private void update_features(JSONArray features_objs,boolean remove_records,long unit_id,Connection con) throws Exception{
		String remove_existing_feature_labels = "DELETE FROM homeaway_property_feature_labels WHERE property_id = ?;";
		if(remove_records){
			PreparedStatement ps = con.prepareStatement(remove_existing_feature_labels);
			ps.setLong(1,unit_id);
			ps.executeUpdate();
			ps.close();
		}	
		for(int i = 0 ; i < features_objs.size() ; i++){
			JSONObject temp_obj = (JSONObject) features_objs.get(i);
			long fl_id = this.getFeatureLabel((String) temp_obj.get("featureLabel"), con);
			this.parse_update_feature(temp_obj, fl_id, unit_id, con);
		}
	}
	
	/**
	 * checks if the feature_label already exists in table homeaway_feature_labels, if it doesnot, it enters 
	 * it. At the end, it returns the fl_id for this record.
	 * 
	 * @param feature_label, name of the feature label we are interested in.
	 * @param con, Connection object
	 * @return,Integer, feature_label_id in table homeaway_feature_labels. 
	 * @throws Exception
	 */
	private long getFeatureLabel(String feature_label,Connection con) throws Exception{
		
		String get_feature_label = "SELECT fl_id FROM homeaway_feature_labels WHERE feature_label = ? ;";
		String insert_new_feature_label = "INSERT INTO homeaway_feature_labels(feature_label) VALUES(?);";
		String cols[] = {"fl_id"};
		PreparedStatement ps = con.prepareStatement(get_feature_label);
		ps.setString(1,feature_label);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){ // if this feature label already exists
			return rs.getInt(1);
		}else{ // if this feature label does not exist
			ps.close();
			ps = con.prepareStatement(insert_new_feature_label,cols);
			ps.setString(1,feature_label);
			ps.executeUpdate();
			ResultSet rs2 = ps.getGeneratedKeys();
			if( rs2.next() ){
				long res = rs2.getLong(1);
				ps.close();
				return res;
			}else{ // if could not enter the new label to table
				ps.close();
				throw new Exception("Could not enter new feature label => "+feature_label);
			}
		}
	}
	
	/**
	 * creates records of (property,feature_label) in table homeaway_property_feature_labels. after that 
	 * other adds features for this unit under feature_labels into table homeaway_property_features. 
	 * At the end for each feature all locals are entered into table homeaway_features_locales.
	 * 
	 * @param feature_value_obj, JSON Object containing info for the this feature 
	 * @param feature_label_id, fl_id in table homeaway_feature_labels.
	 * @param unit_id, the property id in table homeaway_unit_exists.
	 * @param con, Connection object.
	 * @throws Exception
	 */
	private void parse_update_feature(JSONObject feature_value_obj,long feature_label_id,long unit_id,Connection con) throws Exception{
		String add_property_feature_label = "INSERT INTO homeaway_property_feature_labels(property_id,fl_id) VALUES(?,?);";
		String add_property_features = "INSERT INTO homeaway_property_features(active,property_fl_id,feature_count) VALUES(?,?,?);";
		String add_property_features_locals = "INSERT INTO homeaway_features_locales(feature_id,locale,feature_name) VALUES(?,?,?);";
		JSONObject feature_obj = (JSONObject) feature_value_obj.get("feature");
		boolean active = (boolean) feature_obj.get("active"); // is this feature active
		long count = (Long) feature_value_obj.get("count"); // how many of this feature exists.
		JSONArray featureLocals = (JSONArray) ((JSONObject) feature_obj.get("localizedName")).get("texts");
		//add record to homeaway_property_features_labels
		String cols[] = {"pfl_id"};
		PreparedStatement ps = con.prepareStatement(add_property_feature_label,cols);
		ps.setLong(1,unit_id);
		ps.setLong(2,feature_label_id);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys(); // get the pfl_id returned after insertion of new records.
		if(rs.next()){
			String cols2[] = {"f_id"};
			ps = con.prepareStatement(add_property_features,cols2);
			long pfl_id = rs.getLong(1);
			if(active)
				ps.setString(1,"YES");
			else
				ps.setString(1,"NO");
			ps.setLong(2,pfl_id);
			ps.setLong(3,count);
			ps.executeUpdate();
			ResultSet rs2 = ps.getGeneratedKeys();
			if(rs2.next()){
				long feature_id = rs2.getLong(1);
				ps = con.prepareStatement(add_property_features_locals);
				for(int i = 0 ; i < featureLocals.size() ; i++){
					ps.setLong(1,feature_id);
					ps.setString(2,(String) ((JSONObject) featureLocals.get(i)).get("locale"));
					ps.setString(3,(String) ((JSONObject) featureLocals.get(i)).get("content"));
					ps.addBatch();
				}
				ps.executeBatch();
				ps.close();
			}else{
				ps.close();
				throw new Exception("Could not add feature label");
			}
		}else{
			ps.close();
			throw new Exception("Could not add feature label");
		}
	}
	
	/**
	 * Since there are multi-units listings in database, this method finds the one unit we are interested in.
	 * it uses unit_url to find the specific unit.
	 * @param unit_objs, list of units (Json objects)
	 * @param unit_url, url of the unit we are interested in.
	 * @return
	 */
	private JSONObject getTheUnit(JSONArray unit_objs,String unit_url){
		
		for(int i = 0 ; i<unit_objs.size() ; i++){
			String tmp_str = (String) ((JSONObject) unit_objs.get(i)).get("srcEntityUrl");
			if(tmp_str.trim().equals(unit_url)){
				return (JSONObject) unit_objs.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * This method updates general data for each unit, and also uses methods update_rooms() and update_bedroom_details()
	 * to add bedrooms and bathrooms(including their data) into corresponding tables;
	 * 
	 * @param unit_obj, JSONObject that contains all the info about the unit
	 * @param con, Connection object
	 * @param unit_id, id for the property in table homeaway_unit_exists
	 * @throws Exception
	 */
    private void update_unit(JSONObject unit_obj,Connection con,long unit_id) throws Exception{
		String delete_existing_unit_info = "DELETE FROM homeaway_units WHERE property_id = ?";
    	String insert_new_unit_info = "INSERT INTO homeaway_units(property_id,num_bathrooms,num_bedrooms,maxsleepinbeds,maxsleep,propertytype,area,area_measure_unit) VALUES(?,?,?,?,?,?,?,?);";
    	PreparedStatement ps = con.prepareStatement(delete_existing_unit_info);
    	ps.setLong(1,unit_id);
    	ps.executeUpdate();
    	String cols[] = {"u_id"};
    	ps = con.prepareStatement(insert_new_unit_info, cols);
    	ps.setLong(1,unit_id);
    	ps.setLong(2,unit_obj.containsKey("numberOfBathrooms") ? (Long) unit_obj.get("numberOfBathrooms") : 0);
    	ps.setLong(3,unit_obj.containsKey("numberOfBedrooms") ? (Long) unit_obj.get("numberOfBedrooms") : 0);
    	ps.setLong(4,unit_obj.containsKey("maxSleepInBeds") ? (Long) unit_obj.get("maxSleepInBeds") : 0);
    	ps.setLong(5, unit_obj.containsKey("maxSleep") ? (Long) unit_obj.get("maxSleep") : 0);
    	ps.setString(6, unit_obj.containsKey("propertyType") ? (String) unit_obj.get("propertyType") : null);
    	ps.setLong(7, unit_obj.containsKey("area") ? (Long) unit_obj.get("area") : 0);
    	ps.setString(8,unit_obj.containsKey("areaUnit") ? (String) unit_obj.get("areaUnit") : null);
    	ps.executeUpdate();
    	ResultSet rs  = ps.getGeneratedKeys();
    	if(rs.next()){
    		long u_id = rs.getLong(1);
    		
    		if( unit_obj.containsKey("bedrooms") && unit_obj.get("bedrooms") != null){
    		     JSONArray bedrooms = (JSONArray) unit_obj.get("bedrooms");
    		     this.update_rooms(bedrooms,"BED", con, u_id);
    		}
    		
    		if( unit_obj.containsKey("bedroomDetails") && unit_obj.get("bedroomDetails") != null){
    			 this.update_bedroom_details((JSONObject) unit_obj.get("bedroomDetails"),con,u_id);
    		}
    		
    		if( unit_obj.containsKey("bathrooms") && unit_obj.get("bathrooms") != null){
   		     JSONArray bathrooms = (JSONArray) unit_obj.get("bathrooms");
   		     this.update_rooms(bathrooms,"BATH", con, u_id);
   		    }
    		
    		if( unit_obj.containsKey("reviewSummary") && unit_obj.get("reviewSummary") != null){
    			this.update_reveiw_summary((JSONObject) unit_obj.get("reviewSummary") ,con,u_id);
    		}
    		
    		if( unit_obj.containsKey("unitRentalPolicy") && unit_obj.get("unitRentalPolicy") != null){
    		    this.update_rental_policy((JSONObject) unit_obj.get("unitRentalPolicy") ,con,u_id);
    		}   
   	   	}else{
       		ps.close();
       		throw new Exception("Could not add unit for unit_id => "+unit_id);
       	}
    }
    
    /**
     * updates the info for rooms provided in input. this method used method this.update_room_amenities() and 
     * update_room_name_notes().
     * 
     * @param rooms_objs, JSONArray object for all the rooms of this type available for the unit.
     * @param room_type, type of room{'BED','BATH'}
     * @param con, Connection object.
     * @param u_id, unit id in table homeaway_units.
     * @throws Exception
     */
    private void update_rooms(JSONArray rooms_objs,String room_type,Connection con,long u_id) throws Exception{
	   String add_new_rooms = "INSERT INTO homeaway_unit_rooms(unit_id,room_type) VALUES(?,?);";
	   String cols[] = {"room_id"};
	   PreparedStatement ps;
	   
	   for(int i = 0 ; i < rooms_objs.size() ; i++){
		   ps = con.prepareStatement(add_new_rooms,cols);
		   ps.setLong(1,u_id);
		   ps.setString(2,room_type);
		   ps.executeUpdate();
		   ResultSet rs = ps.getGeneratedKeys();
		   if(rs.next()){
			   JSONObject tmp_room_obj = (JSONObject) rooms_objs.get(i);
			   long room_id = rs.getLong(1);
			   this.update_room_name_notes(tmp_room_obj, con,room_id); 
			   if( tmp_room_obj.containsKey("amenities") && tmp_room_obj.get("amenities") != null){
				   this.update_room_amenities((JSONArray) tmp_room_obj.get("amenities"),con,room_id);
			   }
		   }else{
			   throw new Exception("Could not add room for unit => "+u_id);
		   }
	   }
	}
    
    /**
     * updates details of bedrooms for each unit.
     * 
     * @param bedroom_details, JSONObject contains the locals for details of bedrooms for each unit.
     * @param con, Connection object
     * @param unit_id, id for this unit in table homeaway_units 
     * @throws Exception
     */
    private void update_bedroom_details(JSONObject bedroom_details,Connection con,long unit_id) throws Exception{
    	String remove_existing_bedroom_details = "DELETE FROM homeaway_bedroom_details_locale WHERE unit_id = ?;";
    	String add_new_bedroom_details = "INSERT INTO homeaway_bedroom_details_locale(unit_id,locale,bedroom_details) VALUES(?,?,?);";
    	PreparedStatement ps = con.prepareStatement(remove_existing_bedroom_details);
    	ps.setLong(1,unit_id);		
    	ps.executeUpdate();
        ps.close();
        ps = con.prepareStatement(add_new_bedroom_details);
        JSONArray bedroom_details_texts = new JSONArray();
        if(bedroom_details.containsKey("texts") && bedroom_details.get("texts") != null){
        	bedroom_details_texts = (JSONArray) bedroom_details.get("texts");
        }
        for(int i = 0 ; i < bedroom_details_texts.size() ; i++){
        	JSONObject  tmp_obj = (JSONObject) bedroom_details_texts.get(i);
        	if(tmp_obj.containsKey("content")){
        		ps.setLong(1,unit_id);
        		ps.setString(2,(String) tmp_obj.get("locale"));
        		ps.setString(3,(String) tmp_obj.get("content"));
        		ps.addBatch();
        	}
        }
        ps.executeBatch();
    }
    
    /**
     * updates info for names and notes for each room of a unit. it imports all the available locals
     * for names and notes for each room.
     * 
     * @param room_obj,JSONObject that contains info for the room we are updating. 
     * @param con, Connection object.
     * @param room_id, id in table homeaway_unit_rooms.
     * @throws Exception
     */
    private void update_room_name_notes(JSONObject room_obj,Connection con,long room_id) throws Exception{
    	
    	String add_new_notes = "INSERT INTO homeaway_unit_rooms_note_locales(room_id,locale,room_note) VALUES(?,?,?);";
    	String add_new_names = "INSERT INTO homeaway_unit_rooms_name_locales(room_id,locale,room_name) VALUES(?,?,?);";
    	//System.out.println(room_obj);
    	if(room_obj.containsKey("name") && ((JSONObject) room_obj.get("name")) != null){
    		JSONArray name_texts = (JSONArray) ((JSONObject) room_obj.get("name")).get("texts");
	    	PreparedStatement ps = con.prepareStatement(add_new_names);
	    	for(int i = 0 ; i < name_texts.size() ; i++){
	    		String name_content = (String) ((JSONObject)name_texts.get(i)).get("content");
	    		if(name_content != null && name_content.trim().length() > 0){
	    			ps.setLong(1,room_id);
	    			ps.setString(2,(String) ((JSONObject)name_texts.get(i)).get("locale"));
	    			ps.setString(3,name_content);
	    			ps.addBatch();
	    		}
	        }
	    	ps.executeBatch();
    	}
    	
    	if(room_obj.containsKey("note") && ((JSONObject) room_obj.get("note")) != null){
    		JSONArray note_texts = (JSONArray) ((JSONObject) room_obj.get("note")).get("texts");
	    	PreparedStatement ps = con.prepareStatement(add_new_notes);
	    	for(int i = 0 ; i < note_texts.size() ; i++){
	    		String note_content = (String) ((JSONObject)note_texts.get(i)).get("content");
	    		if(note_content != null && note_content.trim().length() > 0){
	    			ps.setLong(1,room_id);
	    			ps.setString(2,(String) ((JSONObject)note_texts.get(i)).get("locale"));
	    			ps.setString(3,note_content);
	    			ps.addBatch();
	    		}
	        }
	    	ps.executeBatch();
    	}
    }
    
    /**
     * updates amenities info for each room.
     * 
     * @param aminities, JSONArray contains JSON objects containing info for each amenity.
     * @param con, Connection object.
     * @param room_id, id of the room in table homeaway_unit_rooms. 
     * @throws Exception
     */
    private void update_room_amenities(JSONArray aminities,Connection con,long room_id) throws Exception{
    	
    	String delete_existing_amenities = "DELETE FROM homeaway_room_aminities WHERE room_id = ?;";
    	String insert_new_aminity = "INSERT INTO homeaway_room_aminities(room_id,am_count) VALUES(?,?);";
    	String insert_new_aminity_locals = "INSERT INTO homeaway_room_aminities_locales(room_aminity_id,locale,room_aminity_name) VALUES(?,?,?);";
    	PreparedStatement ps = con.prepareStatement(delete_existing_amenities);
    	ps.setLong(1,room_id);
    	ps.executeUpdate();
    	
    	String cols[] = {"ra_id"};
    	for(int i = 0 ; i<aminities.size() ; i++){
    		JSONObject aminity_obj = (JSONObject) aminities.get(i);
    	 	ps = con.prepareStatement(insert_new_aminity,cols);
    	    ps.setLong(1,room_id);
    	    if( aminity_obj.containsKey("count") && aminity_obj.get("count") != null){
    	    	ps.setLong(2,(Long) aminity_obj.get("count"));	
    	    }else{
    	    	ps.setInt(2,0);
    	    }
    	    ps.executeUpdate();
    	    ResultSet rs = ps.getGeneratedKeys();
    	    if(rs.next()){
    	    	long ra_id = rs.getLong(1);
    	    	PreparedStatement ps2 = con.prepareStatement(insert_new_aminity_locals);
    	    	JSONObject features_obj = (JSONObject)aminity_obj.get("feature");
    	    	JSONArray local_texts = (JSONArray) ((JSONObject)features_obj.get("localizedName")).get("texts");
    	    	for(int j = 0 ; j < local_texts.size() ; j++){
    	    		String content = (String) ((JSONObject) local_texts.get(j)).get("content");
    	    		if( content != null && content.trim().length() > 0){
    	    			ps2.setLong(1,ra_id);
    	    			ps2.setString(2, (String) ((JSONObject) local_texts.get(j)).get("locale"));
    	    			ps2.setString(3,content);
    	    			ps2.addBatch();
    	    		}
    	    	}
    	    	ps2.executeBatch();
    	    }else{
    	    	throw new Exception("Could not add aminity for room => "+room_id);
    	    }
    	}
    }
    
    /**
     * Updates review info for this specific unit.
     * 
     * @param review_obj, JSONObject contains review info for this unit.
     * @param con, Connection object
     * @param u_id, id in table homeaway_unit_rooms
     * @throws Exception
     */
    private void update_reveiw_summary(JSONObject review_obj,Connection con,long u_id) throws Exception{
    	
    	String remove_existing_review = "DELETE FROM homeaway_unit_reveiws WHERE unit_id = ?;";
    	String add_new_review = "INSERT INTO homeaway_unit_reveiws(unit_id,reviewcount,averagerating,onestarreviewcount,twostarreviewcount,threestarreviewcount,fourstarreviewcount,fivestarreviewcount) VALUES(?,?,?,?,?,?,?,?);";
    	PreparedStatement ps = con.prepareStatement(remove_existing_review);
    	ps.setLong(1,u_id);
    	ps.executeUpdate();
    	ps.close();
    	ps = con.prepareStatement(add_new_review);
    	ps.setLong(1,u_id);
        ps.setLong(2,review_obj.containsKey("reviewCount") ? (Long) review_obj.get("reviewCount") : 0);
        ps.setDouble(3,review_obj.containsKey("averageRating") ? (Double) review_obj.get("averageRating") : 0.0);
        ps.setLong(4,review_obj.containsKey("oneStarReviewCount") ? (Long) review_obj.get("oneStarReviewCount") : 0);
        ps.setLong(5,review_obj.containsKey("twoStarReviewCount") ? (Long) review_obj.get("twoStarReviewCount") : 0);
        ps.setLong(6,review_obj.containsKey("threeStarReviewCount") ? (Long) review_obj.get("threeStarReviewCount") : 0);
        ps.setLong(7,review_obj.containsKey("fourStarReviewCount") ? (Long) review_obj.get("fourStarReviewCount") : 0);
        ps.setLong(8,review_obj.containsKey("fiveStarReviewCount") ? (Long) review_obj.get("fiveStarReviewCount") : 0);
        ps.executeUpdate();
        
    }
    
    
    private void update_rental_policy(JSONObject policy_obj,Connection con,long u_id) throws Exception{
    	String remove_existing_policy = "DELETE FROM homeaway_unit_rentalpolicy WHERE unit_id = ?;";
    	String add_new_policy = "INSERT INTO homeaway_unit_rentalpolicy(unit_id,pet_allowed,smoking_allowed,children_allowed,check_in,check_out,house_rules) VALUES(?,?,?,?,?,?,?);";
    	PreparedStatement ps = con.prepareStatement(remove_existing_policy);
    	ps.setLong(1,u_id);
    	ps.executeUpdate();
    	ps.close();
    	ps = con.prepareStatement(add_new_policy);
    	ps.setLong(1,u_id);
    	ps.setString(2,(policy_obj.containsKey("petsAllowed") && policy_obj.get("petsAllowed") != null) ? ( (boolean) policy_obj.get("petsAllowed") ? "YES" : "NO" ) :  null);
    	ps.setString(3,(policy_obj.containsKey("smokingAllowed") && policy_obj.get("smokingAllowed") != null) ?  ( (boolean) policy_obj.get("smokingAllowed") ? "YES" : "NO" ) :  null);
    	ps.setString(4,(policy_obj.containsKey("childrenAllowed") && policy_obj.get("childrenAllowed") != null) ? (String) policy_obj.get("childrenAllowed") :  null);
    	ps.setString(5,(policy_obj.containsKey("checkInTime") && policy_obj.get("checkInTime") != null) ? (String) policy_obj.get("checkInTime") :  null);
    	ps.setString(6,(policy_obj.containsKey("checkOutTime") && policy_obj.get("checkOutTime") != null) ? (String) policy_obj.get("checkOutTime") :  null);
    	ps.setString(7,(policy_obj.containsKey("houseRules") && policy_obj.get("houseRules") != null) ? (String) policy_obj.get("houseRules") :  null);
    	ps.executeUpdate();
    	ps.close();
    }
}
