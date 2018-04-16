package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import utils.DB;

public class UpdatePartnerInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONParser parser = new JSONParser();
		String action = req.getParameter("action");
		try{
			if (action.equals("edit")){
			   update_partner_record(req, resp, parser);
			}else if (action.equals("remove")){
			   remove_partner_record(req, resp, parser);
			}
		}catch(Exception e){
		   e.printStackTrace();
		   send_error(resp);
        }
		
	}
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	protected void delete_from_TABLES(HttpServletRequest req,HttpServletResponse resp,String p_id) throws Exception{
	 
          String Delete_url_query = "DELETE FROM partners_urls WHERE partner_id="+p_id+";";
          String Delete_key_query = "DELETE FROM partners_keys WHERE partner_id="+p_id+";";
          String Delete_secret_query = "DELETE FROM partners_secrets WHERE partner_id="+p_id+";";
          String Delete_partner_lang_query = "DELETE FROM partners_languages WHERE partner_id="+p_id+";";
          String Delete_partner_curren_query = "DELETE FROM partners_currencies WHERE partner_id="+p_id+";";
          
          DB database = new DB("jdbc/bwwd_adminDB");
  		  Connection con = null;
  		  con = database.getConnection();
  		  con.setAutoCommit(false);
  		  Statement stmt = con.createStatement();
  		  stmt.addBatch(Delete_url_query);
  		  stmt.addBatch(Delete_key_query);
  		  stmt.addBatch(Delete_secret_query);
  		  stmt.addBatch(Delete_partner_lang_query);
  		  stmt.addBatch(Delete_partner_curren_query);
  		  stmt.executeBatch();
  		  con.commit();
  		  con.close();
	 }
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @param p_id
	 */
	protected void update_partner_record(HttpServletRequest req,HttpServletResponse resp,JSONParser parser) throws Exception{
		
		JSONObject obj = (JSONObject) parser.parse(req.getParameter("partner_new_data"));
		JSONArray urls = (JSONArray) obj.get("urls");
				
		String p_name = (String) obj.get("partner_name");
		String p_id = (String) obj.get("partner_id");
		
		p_name = p_name.toLowerCase();
		p_name = p_name.substring(0,1).toUpperCase()+p_name.substring(1);
		StringTokenizer str = new StringTokenizer((String) obj.get("partner_category"),"_");
		String category = str.nextToken();
		String sub_category = str.nextToken();
		String has_key = (String) obj.get("has_key");
		String has_secret = (String) obj.get("has_secret");
		String data_format = (String) obj.get("data_format");
		String partner_languages = (String) obj.get("languages");
		String partner_currencies = (String) obj.get("currencies");
		String has_token = (String) obj.get("has_token");
	    String api_key = null;
	    String api_secret = null;
	    String token_url = null;
		
	    delete_from_TABLES(req,resp,p_id);
	    
	    String Partner_info_update_query = "UPDATE partners_info "
	    		                         + "SET partner_category=?,partner_subcategory=?,partner_data_format=?,partner_languages=?,partner_currencies=?,has_key=?,"
               	                         + "has_secret=?,has_token=? WHERE partner_id = ?;";

	    String Partner_key_query = "INSERT INTO partners_keys(partner_id,partner_key) VALUES(?,?);";
	    String Partner_secret_query = "INSERT INTO partners_secrets(partner_id,partner_secret) VALUES(?,?);";
	    String Partner_url_query = "INSERT INTO partners_urls(partner_id,url_type,type_other,url,description) VALUES(?,?,?,?,?);";
	    String get_languageid_query = "SELECT lang_id FROM supported_languages WHERE lang_val = ?";
	    String Partner_language_query = "INSERT INTO partners_languages(lang_id,partner_id) VALUES(?,?);";
	    String get_currencyid_query = "SELECT curren_id FROM supported_currencies WHERE curren_val = ?";
	    String Partner_currency_query = "INSERT INTO partners_currencies(curren_id,partner_id) VALUES(?,?);";
	    
	    
	    DB database = new DB("jdbc/bwwd_adminDB");
		Connection con = database.getConnection();;  
	    PreparedStatement ps = con.prepareStatement(Partner_info_update_query);
		ps.setString(1,category);
		ps.setString(2,sub_category);
		ps.setString(3,data_format);
	    ps.setString(4,partner_languages);
	    ps.setString(5,partner_currencies);
		ps.setString(6,has_key);
		ps.setString(7,has_secret);
		ps.setString(8,has_token);
		ps.setString(9,p_id);
		ps.executeUpdate();
	    ps.close();
		
		if ( has_key.equals("YES") ){
	    	api_key = (String) obj.get("api_key");
	    	ps = con.prepareStatement(Partner_key_query);
	    	ps.setInt(1,Integer.parseInt(p_id));
	    	ps.setString(2,api_key);
	    	ps.executeUpdate();
	    	ps.close();
		}
		
		if ( has_secret.equals("YES") ){
	    	api_secret = (String) obj.get("api_secret");
	    	ps = con.prepareStatement(Partner_secret_query);
	    	ps.setInt(1,Integer.parseInt(p_id));
	    	ps.setString(2,api_secret);
	    	ps.executeUpdate();
	    	ps.close();
		}	
		
		if ( has_token.equals("YES") ){
	    	token_url = (String) obj.get("api_token_url");
	    	ps = con.prepareStatement(Partner_url_query);
	    	ps.setInt(1,Integer.parseInt(p_id));
	    	ps.setString(2,"Token");
	    	ps.setString(3,null);
	    	ps.setString(4,token_url);
	    	ps.setString(5,"This url is used to obtain authenticaton token. ");
	    	ps.executeUpdate();
	    	ps.close();
		}
		
		ps = con.prepareStatement(Partner_url_query);
		  Iterator<JSONObject> it = urls.iterator();
		  
		  while(it.hasNext()){
			JSONObject url_obj = it.next();
			ps.setInt(1,Integer.parseInt(p_id));
			String url_type = (String) url_obj.get("url_type");
	    	ps.setString(2,url_type.trim());
	    	if(url_type.trim().equals("Other")){
	    		ps.setString(3,(String) url_obj.get("url_type_other"));
	    	}else{
	    		ps.setString(3,null);
	    	}
	    	ps.setString(4,(String) url_obj.get("url"));
	    	ps.setString(5,(String) url_obj.get("url_describ"));
	    	ps.addBatch();
	      }
		  ps.executeBatch();
		  
		  /* Languages and partners*/
		  ResultSet rs = null;
		  StringTokenizer tokens = new StringTokenizer(partner_languages,",");
		  ps = con.prepareStatement(get_languageid_query);
		  PreparedStatement ps2 = con.prepareStatement(Partner_language_query);
		  while(tokens.hasMoreTokens()){
			  ps.setString(1,(String)tokens.nextToken());
			  rs = ps.executeQuery();
			  rs.next();
			  int lang_id = rs.getInt(1);
			  ps2.setInt(1,lang_id);
			  ps2.setString(2,p_id);
			  ps2.addBatch();
		  }
		  ps2.executeBatch();
		  
		  /* Currencies and partners*/
		  tokens = new StringTokenizer(partner_currencies,",");
		  ps = con.prepareStatement(get_currencyid_query);
		  ps2 = con.prepareStatement(Partner_currency_query);
		  while(tokens.hasMoreTokens()){
			  ps.setString(1,(String)tokens.nextToken());
			  rs = ps.executeQuery();
			  rs.next();
			  int curren_id = rs.getInt(1);
			  ps2.setInt(1,curren_id);
			  ps2.setString(2,p_id);
			  ps2.addBatch();
		  }
		  
		  ps2.executeBatch();
		  con.close();
    }
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @param parser
	 */
	protected void remove_partner_record(HttpServletRequest req,HttpServletResponse resp,JSONParser parser) throws Exception{
		JSONObject obj = (JSONObject) parser.parse(req.getParameter("partner_new_data"));
		String p_id = (String) obj.get("partner_id");
		String Partner_delete_query = "DELETE FROM partners_info WHERE partner_id = ?;";
		
		DB database = new DB("jdbc/bwwd_adminDB");
		Connection con = database.getConnection();;  
	    PreparedStatement ps = con.prepareStatement(Partner_delete_query);
		ps.setString(1,p_id);
		ps.executeUpdate();
    }
	
	/**
	 * 
	 * @param resp
	 * @throws IOException
	 */
    protected void send_error(HttpServletResponse resp) throws IOException {
    	resp.setContentType("text");
		resp.setHeader("Cache-Control", "no-cache");
		resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
	 
}
