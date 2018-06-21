package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UpdateToken implements UpdateAuthToken{

	private static int partnerID;
	private static DB dbObj;
	/**
	 * 
	 * @param db_url
	 * @param username
	 * @param pass
	 * @param partner_name
	 * @throws Exception
	 */
    public UpdateToken(String db_url,String username,String pass,String partner_name) throws Exception{
		 UpdateToken.setDbObj(db_url, username, pass);
		 UpdateToken.setPartnerID(partner_name);
    }
	
    /**
     * 
     * @return
     */
	private static DB getDbObj() {
		return UpdateToken.dbObj;
	}

	/**
	 * 
	 * @param db_url
	 * @param username
	 * @param pass
	 */
	private static void setDbObj(String db_url,String username,String pass) {
		UpdateToken.dbObj = new DB(db_url,username,pass);
	}
   
	/**
	 * 
	 * @return
	 */
    private static int getPartnerID() {
		return UpdateToken.partnerID;
	}

    /**
     * 
     * @param partner_name
     * @throws Exception
     */
	private static void setPartnerID(String partner_name) throws Exception {
		
		Connection con = UpdateToken.getConnection();
		String get_partnerid_query = "SELECT partner_id FROM partners_info WHERE partner_name = ?;";
		PreparedStatement ps = con.prepareStatement(get_partnerid_query);
		ps.setString(1,partner_name);
		ResultSet rs = null;
		rs = ps.executeQuery();
        
		if(rs.next()){
			UpdateToken.partnerID = rs.getInt(1) ;
		}
		
		ps.close();
		con.close();
	}
	
	/**
	 * 
	 * 
	 */
	public String getToken() throws Exception {
		
		Connection con = UpdateToken.getConnection();
		String get_token_query = "SELECT partner_token  FROM partners_tokens WHERE partner_id = ?;";
		PreparedStatement ps = con.prepareStatement(get_token_query);
		ps.setInt(1,UpdateToken.getPartnerID());
		ResultSet rs = null;
		rs = ps.executeQuery();
		String p_token = null;
		if(rs.next()){
		  p_token = rs.getString(1);
		}  
		return p_token;
	}
	
	
	
	/**
	 * 
	 * 
	 */
	public int isTokenValid() throws Exception {
		
		Connection con = UpdateToken.getConnection();
		String get_token_query = "SELECT partner_token,expiry_date FROM partners_tokens WHERE partner_id = ?;";
		PreparedStatement ps = con.prepareStatement(get_token_query);
		ps.setInt(1,UpdateToken.getPartnerID());
		ResultSet rs = null;
		rs = ps.executeQuery();
		
		if(rs.next()){
        	String p_token = rs.getString(1);
			String p_ex_date = rs.getString(2);
			boolean result = is_ex_date_passed(p_ex_date);
			if(result == true){
				return 0; // not valid
			}else{
				return 1; // valid
			}
        }else{
        	return -1; // does not exist
        }
		
	}

    /**
     * 
     * @param date_str
     * @return
     * @throws ParseException
     */
	private boolean is_ex_date_passed(String date_str) throws ParseException{
		
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = df.parse(date_str);
		Date new_date = new Date();
		return new_date.compareTo(date) > 0 ? true : false ;
		
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean hasPartnerKey() throws Exception{
		
		Connection con = UpdateToken.getConnection();
		String check_key_query = "SELECT has_key FROM partners_info WHERE partner_id = ?;";
		PreparedStatement ps = con.prepareStatement(check_key_query);
		ps.setInt(1,UpdateToken.getPartnerID());
		ResultSet rs = null;
		rs = ps.executeQuery();
		if(rs.next()){
		   String result = rs.getString(1);
		   if(result.equals("YES")){
			   ps.close();
			   con.close();
			   return true;
		   }else{
			   ps.close();
			   con.close();
			   return false;
		   }
		}else{
			ps.close();
			con.close();
	    	return false;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean hasPartnerSecret() throws Exception{
		
		Connection con = UpdateToken.getConnection();
		String check_secret_query = "SELECT has_secret FROM partners_info WHERE partner_id = ?;";
		PreparedStatement ps = con.prepareStatement(check_secret_query);
		ps.setInt(1,UpdateToken.getPartnerID());
		ResultSet rs = null;
		rs = ps.executeQuery();
		
		if(rs.next()){
		   String result = rs.getString(1);
		   if(result.equals("YES")){
			   ps.close();
			   con.close();
			   return true;
		   }else{
			   ps.close();
			   con.close();
			   return false;
		   }
		}else{
			ps.close();
			con.close();
	    	return false;
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getPartnerKey() throws Exception{
	  
		Connection con = UpdateToken.getConnection();
		String get_key_query = "SELECT partner_key FROM partners_keys WHERE partner_id = ?;";
		PreparedStatement ps = con.prepareStatement(get_key_query);
		ps.setInt(1,UpdateToken.getPartnerID());
		ResultSet rs = null;
		rs = ps.executeQuery();
		
		if(rs.next()){
		  return rs.getString(1);
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getPartnerSecret() throws Exception{
	  
		Connection con = UpdateToken.getConnection();
		String get_secret_query = "SELECT partner_secret FROM partners_secrets WHERE partner_id = ?;";
		PreparedStatement ps = con.prepareStatement(get_secret_query);
		ps.setInt(1,UpdateToken.getPartnerID());
		ResultSet rs = null;
		rs = ps.executeQuery();
		
		if(rs.next()){
		  return rs.getString(1);
		}else{
			return null;
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public Map<String, String> getApiCredentials() throws Exception {
		
		Map<String,String> creds = new HashMap<String,String>();
		if(this.hasPartnerKey()){
			creds.put("key",this.getPartnerKey());
		}
		
		if(this.hasPartnerSecret()){
			creds.put("secret",this.getPartnerSecret());
		}
	
		return creds;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	private String getUrl(String type) throws Exception{
		Connection con = UpdateToken.getConnection();
		String get_key_query = "SELECT url FROM partners_urls WHERE partner_id = ? AND url_type = ?;";
		PreparedStatement ps = con.prepareStatement(get_key_query);
		ps.setInt(1,UpdateToken.getPartnerID());
		ps.setString(2,type);
		ResultSet rs = null;
		rs = ps.executeQuery();
		if(rs.next()){
		  return rs.getString(1);
		}else{
			return null;
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws MalformedURLException 
	 * 
	 */
	private JSONObject getTokenFromSource() throws Exception{
		
		URL url = new URL(this.getUrl("Token"));
		Map<String,String> creds = this.getApiCredentials();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        
        con.setDoOutput(true); // indicated this is a Post request
        con.setDoInput(true); // indicates server will return a response
        
        JSONObject user=new JSONObject();
        user.put("clientKey",creds.get("key"));
        user.put("clientSecret",creds.get("secret"));
                       
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
		writer.write(user.toString());
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
			// print result
			//System.out.println(response.toString());
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(response.toString());
            JSONObject reponse_json = (JSONObject) obj;
            return (JSONObject) reponse_json.get("responseEntity");
                   	            
		} else {
			return null;
		}
		
	}
	
	
	/**
	 * @throws IOException 
	 * 
	 */
	@Override
	public void updateToken(boolean token_exist) throws Exception {
		
		JSONObject jsonObject = this.getTokenFromSource();
		String token = (String) jsonObject.get("encodedHeader");
        String ex_date = (String) jsonObject.get("expiresDate");
        String UpdateQuery = "UPDATE partners_tokens SET partner_token = ? , expiry_date = ? WHERE partner_id = ?;";
        String InsertQuery = "INSERT INTO partners_tokens(partner_id,partner_token,expiry_date) VALUES(?,?,?)";
        
        Connection con = UpdateToken.getConnection();
        if (token_exist == false){
        	PreparedStatement ps = con.prepareStatement(InsertQuery);
    		ps.setInt(1,getPartnerID());
    		ps.setString(2,token);
    		ps.setString(3,ex_date);
    		ps.executeUpdate();
    		ps.close();
    	}else if (token_exist == true){
    		PreparedStatement ps = con.prepareStatement(UpdateQuery);
    		ps.setInt(3,getPartnerID());
    		ps.setString(1,token);
    		ps.setString(2,ex_date);
    		ps.executeUpdate();
    		ps.close();
    	}	
        con.close();
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Connection getConnection() throws Exception{
		Connection con = UpdateToken.getDbObj().getConnection();
		return con;
	}
    //"jdbc:mysql://mysql3000.mochahost.com:3306/bwwd_admin","main_root","551360" 
}
