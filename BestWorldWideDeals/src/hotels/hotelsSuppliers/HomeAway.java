package hotels.hotelsSuppliers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import hotels.interfaces.HotelSearch;
import hotels.utils.SearchObject;
import utils.DB;
import utils.UpdateToken;


public class HomeAway extends UpdateToken implements HotelSearch {

	private SearchObject serachObj;
	private DB searchDbObj;
	private String smallImage;
	private String mediumImage;	
	
	private String largeImage;
	private static String qouta_url = "https://channel.homeaway.com/channel/vacationRentalQuotes?_restfully=true";
	public HomeAway(SearchObject seacrhObj) throws Exception{
		super("jdbc/bwwd_adminDB","Homeaway");
		this.setSerachObj(seacrhObj);
		this.setSearchDbObj(new DB("jdbc/bwwd_homeawayDB"));
		this.setLargeImage("1024x768");
		this.setMediumImage("400x300");
		this.setSmallImage("FULL");
	}

	private String getMediumImage() {
		return mediumImage;
	}

	private void setMediumImage(String mediumImage) {
		this.mediumImage = mediumImage;
	}

	private String getSmallImage() {
		return smallImage;
	}

	private void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	private String getLargeImage() {
		return largeImage;
	}

	private void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	private DB getSearchDbObj() {
		return searchDbObj;
	}

	
	private void setSearchDbObj(DB searchDbObj) {
		this.searchDbObj = searchDbObj;
	}

	
	private SearchObject getSerachObj() {
		return serachObj;
	}

	private void setSerachObj(SearchObject serachObj) {
		this.serachObj = serachObj;
	}

	/**
	 * 
	 */
	@Override
	public JSONObject getSearchResults(int dist_from_dest) throws Exception {
		int i = this.isTokenValid();
		if( i == 0 ){ // if token is not valid but it is in database.
		   this.updateToken(true);
		}else if( i < 0 ){ // if the token does not exist in database at all.
		   this.updateToken(false);
		}
		/*System.out.println(this.isTokenValid());
		System.out.println(this.getToken());*/
		//System.out.println(this.getAvailableUnits(dist_from_dest));
		FileWriter fw = new FileWriter("c:/users/hosse/desktop/homeaway.json",true);
		BufferedWriter bf = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bf);
		out.println(this.getAvailableUnits(dist_from_dest).toString());
		out.close();
	    return null;
	}
	
	/**
	 * 
	 * @param dist_from_dest
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONArray getAvailableUnits(int dist_from_dest) throws Exception{
		Map<String,JSONObject> result = this.getCoordQualifiedUnits(dist_from_dest);
		System.out.println("Number Of Qualified Units => "+result.size());
		JSONArray unitsList = new JSONArray();
		Iterator<String> it = result.keySet().iterator();
		String unitUrl;
		int counter = 0 ;
		//###################### TEST ##############################
        //FileWriter fw = new FileWriter("c:/users/hosse/desktop/homeaway.json",true);
		//BufferedWriter bf = new BufferedWriter(fw);
		//PrintWriter out = new PrintWriter(bf);
		/*out.println(result.toString());
		out.close();*/
		//##########################################################
		while(it.hasNext()){
			//System.out.println("=> "+counter);
			unitUrl = it.next();
			counter += 1 ;
			JSONObject tmp_url_obj = new JSONObject();
			tmp_url_obj.put("unitUrl",unitUrl);
			unitsList.add(tmp_url_obj);
			if( counter % 1400 == 0 ){ // update the availabilities for units
				this.updateUnitAvailabilities(result, unitsList);
			 	unitsList.clear();
			 	counter = 0;
			}
		}
		//System.out.println("########"+unitsList.size()+"########");
		if( counter > 0 ){
			this.updateUnitAvailabilities(result, unitsList);
		}
		
		System.out.println("ALMOST DONE!!");
		//###################### TEST ##############################
		/*fw = new FileWriter("c:/users/hosse/desktop/homeaway.json",true);
		bf = new BufferedWriter(fw);
		out = new PrintWriter(bf);
		out.println("RESULT AFTER");
		out.println("#######################################################");
		out.println(result.toString());
		out.close();*/
		//###########################################################
		System.out.println("DONE!!");
		// At this point get the data for available units
		JSONArray finalUnitsList = new JSONArray(); 
		it = result.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			JSONObject obj = result.get(key);
			if( (boolean) obj.get("available") ){
				obj.put("unitUrl",key);
				finalUnitsList.add(obj);
			}
		}
		
		this.getDetailsOfProperty(finalUnitsList);
		return finalUnitsList;
	}
	
	/**
	 * 
	 * @param dist_from_dest
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String,JSONObject> getCoordQualifiedUnits(int dist_from_dest) throws Exception{
		double lat = this.getSerachObj().getLat();
		double lan = this.getSerachObj().getLan();
		Connection con = this.getSearchDbObj().getConnection();
		String result_query = "SELECT a.record_id,b.unitUrl,"+
				 "ST_Distance_Sphere(point(?,?),point(c.lat,c.lang) ) * 0.000621371192 As DIST "+
				 "FROM homeaway_unit_exists As a  "+
				 "INNER JOIN homeaway_unit_idx_feeds As b ON a.unit_id = b.record_id "+
				 "INNER JOIN homeaway_property_address As c ON a.record_id = c.property_id "+     
		         "WHERE ST_Distance_Sphere(point(?,?),point(c.lat,c.lang) ) * 0.000621371192 <= ? "+
		         "ORDER BY DIST;";
		// replace point(c.lang,c.lat) with point(c.lat,c.lang) 
		PreparedStatement ps = con.prepareStatement(result_query);
		System.out.println(lat+" ==== "+lan);
		ps.setDouble(1,lan);
		ps.setDouble(2,lat);
		ps.setDouble(3,lan);
		ps.setDouble(4,lat);
		ps.setInt(5,dist_from_dest);
		ResultSet rs = ps.executeQuery();
		Map<String,JSONObject> result = new HashMap<String,JSONObject>();
		while(rs.next()){
			JSONObject tmp_obj = new JSONObject();
			tmp_obj.put("id",rs.getLong(1));
			tmp_obj.put("dist_to_dest",rs.getDouble(3));
			tmp_obj.put("available",false);
			result.put(rs.getString(2),tmp_obj);
	    }
				
		return result;
	}
	
	/**
	 * 
	 * @param units
	 * @param unitsList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void updateUnitAvailabilities(Map<String,JSONObject> units,JSONArray unitsList) throws Exception{
		URL url = new URL(HomeAway.qouta_url);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		//System.out.println("I AM INSIDE UPDATE !!!!!!!!!!! ====== "+this.getToken());
		con.setRequestProperty("Authorization", "Bearer "+this.getToken());
		//con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true); // indicated this is a Post request
        con.setDoInput(true); // indicates server will return a response
		JSONObject quota=new JSONObject();
        quota.put("checkinDate",this.getSerachObj().getFromDate());
        quota.put("checkoutDate",this.getSerachObj().getToDate());
        quota.put("currencyCode","USD");
        JSONObject occupants = new JSONObject();
        occupants.put("adultCount",this.getSerachObj().getNum_adults());
        occupants.put("childCount",this.getSerachObj().getNum_childs());
        quota.put("occupancy",occupants);
        quota.put("vacationRentalQuoteSummaries",unitsList);
        //System.out.println(quota.toString());
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
		writer.write(quota.toString());
		writer.flush();
		writer.close();        
        
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
			//###################### TEST ##############################
	        /*FileWriter fw = new FileWriter("c:/users/hosse/desktop/homeaway.json",true);
			BufferedWriter bf = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bf);
			out.println("Batch");
			out.println("##############################################");
			out.println(response.toString());
			out.close();
			fw.close();
			bf.close();*/
			//##########################################################
			JSONArray result = (JSONArray) ((JSONObject) obj).get("vacationRentalQuoteSummaries");
			
			for(int i = 0 ; i < result.size() ; i++){
				//System.out.println("==>>>>>>> "+i);
				JSONObject tmpObj = (JSONObject) result.get(i);
				String unitUrl = (String) tmpObj.get("unitUrl");
				if( (Long) ((JSONObject) tmpObj.get("quoteStatus") ).get("code") == 5001 ){
					units.get(unitUrl).put("available",true);
					JSONObject priceObj = (JSONObject) tmpObj.get("posaPrice");
					units.get(unitUrl).put("price",calculate_unit_price(priceObj));
				}
			}
		} else {
			throw new Exception("Server returned the following error responseCode =>"+responseCode);
		}
	    
	}
	
	/**
	 * 
	 * @param priceObj
	 * @return
	 */
	private double calculate_unit_price(JSONObject priceObj){
		 
		 double tot_price = 0.0;
		 JSONObject tmpObj = new JSONObject();
		 JSONObject tmpValue = new JSONObject();
		 if( priceObj.containsKey("baseRent")){
			 tmpObj = (JSONObject) priceObj.get("baseRent");
			 tmpValue = (JSONObject) tmpObj.get("value");
			 tot_price += Double.parseDouble((String) tmpValue.get("amount"));
		 }
		 if( priceObj.containsKey("fees")){
			 tmpObj = (JSONObject) priceObj.get("fees");
			 tmpValue = (JSONObject) tmpObj.get("value");
			 tot_price += Double.parseDouble((String) tmpValue.get("amount"));
		 }
		 if( priceObj.containsKey("taxes")){
			 tmpObj = (JSONObject) priceObj.get("taxes");
			 tot_price += Double.parseDouble((String) tmpObj.get("amount"));
		 }
		 
		 return tot_price;
    }
	
	
	
	
	@SuppressWarnings("unchecked")
	private void getDetailsOfProperty(JSONArray available_units) throws Exception{
		for( int i = 0 ; i < available_units.size() ; i++ ){
			JSONObject obj  = (JSONObject) available_units.get(i);
			//this.getPropertyBasicInfo((long) obj.get("id"),"homeaway_property_headlines",this.getSerachObj().getLocale());
			obj.put("headline",this.getPropertyBasicInfo((long) obj.get("id"),"headline",this.getSerachObj().getLocale()));
			obj.put("description",this.getPropertyBasicInfo((long) obj.get("id"),"description",this.getSerachObj().getLocale()));
			obj.put("further_details",this.getPropertyBasicInfo((long) obj.get("id"),"further_details",this.getSerachObj().getLocale()));
			obj.put("address",this.getAddress((long) obj.get("id")));
			obj.put("propertyUrl",this.getPropertyDetailsURL((long) obj.get("id")));
			obj.put("photos",this.getPhotos((long) obj.get("id")));
			obj.put("unitInfo",this.getUnitInfo((long) obj.get("id")));
    	}
	}
	
	/**
	 * 
	 * @param property_id
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getPropertyBasicInfo(long property_id,String request_name,String locale) throws Exception{
		String loc = locale.split("_")[0];
		String origin_locale_query = null;
	    String alter_locale_query = null;
	    
	    switch(request_name){
	    
	    	case "headline":
	    		origin_locale_query = "SELECT property_headline FROM homeaway_property_headlines WHERE property_id = ? AND locale = ?;";
	    		alter_locale_query = "SELECT property_headline FROM homeaway_property_headlines WHERE property_id = ? AND locale = 'en';";
	    		break;
	    	case "description":
	    		origin_locale_query = "SELECT property_description FROM homeaway_property_description WHERE property_id = ? AND locale = ?;";
	    		alter_locale_query = "SELECT property_description FROM homeaway_property_description WHERE property_id = ? AND locale = 'en';";
	    		break;
	    	case "further_details":
	    		origin_locale_query = "SELECT further_details FROM homeaway_property_further_details WHERE property_id = ? AND locale = ?;";
	    		alter_locale_query = "SELECT further_details FROM homeaway_property_further_details WHERE property_id = ? AND locale = 'en';";
	    		break;
	    }
	    	    
		Connection con = this.getSearchDbObj().getConnection();
	    PreparedStatement ps = con.prepareStatement(origin_locale_query);
	    ps.setLong(1,property_id);
	    ps.setString(2,loc);
	    ResultSet rs = ps.executeQuery();
	    JSONObject json_result = new JSONObject();
	    if(rs.next()){ // if the original local was found return it
	    	String result = rs.getString(1);
	    	con.close();
	    	json_result.put("locale","original");
	    	json_result.put("value",result);
	    	return json_result;
	    }else{ // if the original local was not found return 'en' result
	    	ps = con.prepareStatement(alter_locale_query);
	    	ps.setLong(1,property_id);
		    rs = ps.executeQuery();
		    if(rs.next()){ 
		    	String result = rs.getString(1);
		    	con.close();
		    	json_result.put("locale","alt");
		    	json_result.put("value",result);
		    	return json_result;
		    }
		    con.close();
		    return null;
		}
	    
	 }
	
	/**
	 * 
	 * @param property_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getAddress(long property_id) throws Exception{
		 
		 String result_query = "SELECT addr_1,addr_2,addr_3,city,province,"+
				 "subdivision,country,postal_code,lang,lat "+
				 "FROM homeaway_property_address "+
				 "WHERE property_id = ?;";
		 Connection con = this.getSearchDbObj().getConnection();
		 PreparedStatement ps = con.prepareStatement(result_query);
		 ps.setLong(1,property_id);
		 ResultSet rs = ps.executeQuery();
		 if(rs.next()){
			 JSONObject address = new JSONObject();
			 String tmp_str = ( (rs.getString(1) != null )?rs.getString(1):"" )+( (rs.getString(2) != null )?","+rs.getString(2):"" )
					          +( (rs.getString(3) != null )?","+rs.getString(3):"" )+( (rs.getString(4) != null )?","+rs.getString(4):"" )
					          +( (rs.getString(5) != null )?","+rs.getString(5):"" )+( (rs.getString(6) != null )?","+rs.getString(6):"" )
					          +( (rs.getString(7) != null )?","+rs.getString(7):"" );
			 address.put("main_address",tmp_str);
			 address.put("postalCode",( (rs.getString(8) != null )?","+rs.getString(8):"" ) );
			 address.put("lon",( (rs.getString(9) != null )?rs.getString(9):"" ) );
			 address.put("lat",( (rs.getString(10) != null )?rs.getString(10):"" ) );
			 con.close();
			 return address;
		 }    
		 con.close();
		 return null;
		 
	}
	
	
	/**
	 * 
	 * @param property_id
	 * @return
	 * @throws Exception
	 */
	private String getPropertyDetailsURL(long property_id) throws Exception{
		String getDetailsUrlsQuery = "SELECT locale,pr_url FROM homeaway_property_details_urls WHERE property_id = ?;";
		Map<String,String> urls = new HashMap<String,String>();
		Connection con = this.getSearchDbObj().getConnection();
		PreparedStatement ps = con.prepareStatement(getDetailsUrlsQuery);
		ps.setLong(1,property_id);
		ResultSet rs = ps.executeQuery();
		// store all the Urls available for this unit in a Map
		while(rs.next()){
		    urls.put(rs.getString(1),rs.getString(2));
		}
		con.close();
	    String locale = this.getSerachObj().getLocale();
	    //if there is a direct value for the requested locale
	    if(urls.keySet().contains(locale)){
	    	return urls.get(locale);
	    }
	    
	    Iterator<String> it = urls.keySet().iterator();
	    //store all existing values for a locale for example 'es'=>{es_ES,es_MX}
	    Map<String,Set<String>> multiple_locals = new HashMap<String,Set<String>>();
	    while(it.hasNext()){
	    	String tmp_str = it.next();
	    	String loc = tmp_str.split("_")[0];
	    	if( multiple_locals.containsKey(loc) ){
	    		multiple_locals.get(loc).add(tmp_str);
	    	}else{
	    		multiple_locals.put(loc,new HashSet<String>());
	    		multiple_locals.get(loc).add(tmp_str);
	    	}
	    }
	    
	    String loc = locale.split("_")[0];
	    if(multiple_locals.get(loc).size() ==  1){
	    	return urls.get(multiple_locals.get(loc).iterator().next());
	    }
	    // if there is no record for the locale,for example 'fa',  then return 'en' url.
	    if(loc.equals("en") || !multiple_locals.containsKey(loc)){
	    	if(multiple_locals.get(loc).contains("en_GB")){
	    		return urls.get("en_GB");
	        }else if(multiple_locals.get(loc).contains("en_US")){
	        	return urls.get("en_US");
	        }else if(multiple_locals.get(loc).contains("en_CA")){
	        	return urls.get("en_CA");
	        }else if(multiple_locals.get(loc).contains("en_AU")){
	        	return urls.get("en_AU");
	        }else{
	        	return urls.get(multiple_locals.get(loc).iterator().next());
	        } 	
	    }
	    	
	    if(loc.equals("es")){
	        if(multiple_locals.get(loc).contains("es_ES")){
	    		return urls.get("es_ES");
	    	}else if(multiple_locals.get(loc).contains("es_MX")){
	    	   	return urls.get("es_MX");
	    	}else{
	    	   	return urls.get(multiple_locals.get(loc).iterator().next());
	    	} 	
	    }
	    
	    if(loc.equals("pt")){
	        if(multiple_locals.get(loc).contains("pt_PT")){
	    		return urls.get("pt_PT");
	    	}else if(multiple_locals.get(loc).contains("pt_BR")){
	    	   	return urls.get("pt_BR");
	    	}else{
	    	   	return urls.get(multiple_locals.get(loc).iterator().next());
	    	} 	
	    }
	    
	    if(loc.equals("fr")){
	    	if(multiple_locals.get(loc).contains("fr_FR")){
	    		return urls.get("fr_FR");
	    	}else{
	    		return urls.get(multiple_locals.get(loc).iterator().next());
	    	}
	    }
	    
	    return null;
	}
	
	/**
	 * 
	 * @param property_id
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getPhotos(long property_id) throws Exception{
		
		String getPhotosForProperty = "SELECT photo_group_id,is_thumbnail,image_url,treatment,width,height FROM homeaway_property_photos As a "
				                      +"INNER JOIN  homeaway_property_images As b ON a.record_id = b.photo_group_id "
				                      +"WHERE a.property_id = ? AND b.treatment IN "+"('"+this.getLargeImage()+"','"+this.getMediumImage()+"','"+this.getSmallImage()+"');";
		JSONObject photos = new JSONObject();
		JSONArray images = new JSONArray();
		Map<Long,JSONObject> data = new HashMap<Long,JSONObject>();
		Connection con = this.getSearchDbObj().getConnection();
	    PreparedStatement ps = con.prepareStatement(getPhotosForProperty);
	    ps.setLong(1,property_id);
	    ResultSet rs = ps.executeQuery();
	    photos.put("hasThumbnail",false);
	    while(rs.next()){
	    	
	    	if( rs.getString(2).equals("YES") ){
	    		JSONObject tmp_img = new JSONObject();
	    		tmp_img.put("url",rs.getString(3));
	    		tmp_img.put("width",rs.getInt(5));
	    		tmp_img.put("height",rs.getInt(6));
	    		photos.put("hasThumbnail",true);
	    		if( !photos.containsKey("thumbnail") ){
	    			photos.put("thumbnail",new JSONObject());	
	    		}
	    		((JSONObject)photos.get("thumbnail")).put(rs.getString(4),tmp_img);
	    		continue;
	    	}
	    	
	    	if( !data.containsKey(rs.getLong(1))){
	    		data.put(rs.getLong(1),new JSONObject());
	    		JSONObject tmp_img = new JSONObject();
	    		tmp_img.put("url",rs.getString(3));
	    		tmp_img.put("width",rs.getInt(5));
	    		tmp_img.put("height",rs.getInt(6));
	    		data.get(rs.getLong(1)).put(rs.getString(4),tmp_img);
	       	}else{
	       		JSONObject tmp_img = new JSONObject();
	    		tmp_img.put("url",rs.getString(3));
	    		tmp_img.put("width",rs.getInt(5));
	    		tmp_img.put("height",rs.getInt(6));
	    		data.get(rs.getLong(1)).put(rs.getString(4),tmp_img);
	       	}
	    }
	    
	    Iterator<Long> it = data.keySet().iterator();
	    while(it.hasNext()){
	    	images.add((JSONObject)data.get(it.next()));
	    }
	    photos.put("images",images);
	    con.close();
		return  photos;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private JSONObject getUnitInfo(long property_id) throws Exception{
		
		String get_unit_info = "SELECT num_bedrooms,num_bathrooms,maxsleep,propertytype,area,area_measure_unit,reviewcount,averagerating"
				                +",onestarreviewcount,twostarreviewcount,threestarreviewcount,fourstarreviewcount,fivestarreviewcount "
				                +"FROM homeaway_units As a LEFT JOIN homeaway_unit_reveiws As b ON a.u_id = b.unit_id "
				                +"WHERE property_id = ?;"; 
		Connection con = this.getSearchDbObj().getConnection();
	    PreparedStatement ps = con.prepareStatement(get_unit_info);
	    ps.setLong(1,property_id);
	    ResultSet rs = ps.executeQuery();
	    JSONObject result = new JSONObject();
	   	    
	    if(rs.next()){
	    	result.put("numBedrooms",rs.getLong(1));
	    	result.put("numBathrooms",rs.getLong(2));
	    	result.put("maxSleep",rs.getLong(3));
	    	result.put("propertyType",rs.getString(4));
	    	result.put("area",rs.getDouble(5));
	    	result.put("area_unit",rs.getString(6));
	    	result.put("reviewCount",rs.getInt(7));
	    	result.put("reviewAvg",rs.getDouble(8));
	    	result.put("oneReview",rs.getInt(9));
	    	result.put("twoReview",rs.getInt(10));
	    	result.put("threeReview",rs.getInt(11));
	    	result.put("fourReview",rs.getInt(12));
	    	result.put("fiveReview",rs.getInt(13));
	    }
	    con.close();
	    return result;
		
	}
	/*private JSONObject getFeatures(long property_id) throws Exception{
		
		String get_features = "SELECT a.feature_label,b.f_id,b.active,b.locale,b.feature_name "+
                              "FROM homeaway_feature_labels As a "+
                              "INNER JOIN (SELECT c.fl_id,c.property_id,d.f_id,d.active,d.locale,d.feature_name "+
                              "FROM homeaway_property_feature_labels As c "+
                              "INNER JOIN  (SELECT e.f_id,e.property_fl_id,e.active,f.locale,f.feature_name "+
                              "FROM homeaway_property_features As e "+
                              "INNER JOIN homeaway_features_locales As f ON e.f_id = f.feature_id ) As d ON c.pfl_id = d.property_fl_id) "+
                              "As b ON a.fl_id = b.fl_id "+ 
                              "WHERE b.property_id = ?;";
		
		Connection con = this.getSearchDbObj().getConnection();
	    PreparedStatement ps = con.prepareStatement(get_features);
	    ps.setLong(1,property_id);
	    Map<String,Map<Long,Map<String,String>>> data = new HashMap<String,Map<Long,Map<String,String>>>();
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			if( !data.containsKey(rs.getString(1)) ){
				data.put(rs.getString(1),new HashMap<Long,Map<String,String>>());
			}
			if( !data.get(rs.getString(1)).containsKey(rs.getLong(2)) ){
				data.get(rs.getString(1)).put(rs.getLong(2),new HashMap<String,String>());
			}
			data.get(rs.getString(1)).get(rs.getLong(2)).put(rs.getString(4),rs.getString(5));
		}
	    System.out.println(data);
	    
	    return null;	
	}*/
}
