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
import org.json.simple.parser.ParseException;

import utils.DB;

public class StoreNewPartnerInfo extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONParser parser = new JSONParser();
		try{
			store_to_DB(req, resp, parser);
		}catch(Exception e){
		   e.printStackTrace();
        }
		
	}
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @param parser
	 * @throws Exception
	 */
	protected void store_to_DB(HttpServletRequest req,HttpServletResponse resp,JSONParser parser) throws Exception{
		
		JSONObject obj = (JSONObject) parser.parse(req.getParameter("partner_data"));
		JSONArray urls = (JSONArray) obj.get("urls");
				
		String p_name = (String) obj.get("partner_name");
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
	    	    
	    String Partner_info_query = "INSERT INTO partners_info(partner_name,partner_category,partner_subcategory,partner_data_format,partner_languages"
	    		                    + ",partner_currencies,has_key,has_secret,has_token) VALUES(?,?,?,?,?,?,?,?,?);";
		
	    String Partner_key_query = "INSERT INTO partners_keys(partner_id,partner_key) VALUES(?,?);";
	    String Partner_secret_query = "INSERT INTO partners_secrets(partner_id,partner_secret) VALUES(?,?);";
	    String Partner_url_query = "INSERT INTO partners_urls(partner_id,url_type,type_other,url,description) VALUES(?,?,?,?,?);";
	    	    
	    DB database = new DB("jdbc/bwwd_adminDB");
		Connection con = null;
		try{
			
		  con = database.getConnection();
		  con.setAutoCommit(false);
		  String cols[] = {"partner_id"};
		  PreparedStatement ps = con.prepareStatement(Partner_info_query,cols);
		  ps.setString(1,p_name);
		  ps.setString(2,category);
		  ps.setString(3,sub_category);
		  ps.setString(4,data_format);
		  ps.setString(5,partner_languages);
		  ps.setString(6,partner_currencies);
		  ps.setString(7,has_key);
		  ps.setString(8,has_secret);
		  ps.setString(9,has_token);
		  ps.executeUpdate();
		  ResultSet rs = ps.getGeneratedKeys();
		  Integer p_id = -1;
		  if(rs.next()){
			  p_id = rs.getInt(1);
			  ps.close();
		  }else{
			  con.rollback();
			  con.setAutoCommit(true);
			  con.close();
			  ps.close();
			  send_error(resp);
		  }
		  if ( has_key.equals("YES") ){
		    	api_key = (String) obj.get("api_key");
		    	ps = con.prepareStatement(Partner_key_query);
		    	ps.setInt(1,p_id);
		    	ps.setString(2,api_key);
		    	int rows = ps.executeUpdate();
		    	ps.close();
		    	if( rows <= 0 ){
		    		con.rollback();
					con.setAutoCommit(true);
					con.close();
					send_error(resp);
		    	}
		  }
		  if ( has_secret.equals("YES") ){
		    	api_secret = (String) obj.get("api_secret");
		    	ps = con.prepareStatement(Partner_secret_query);
		    	ps.setInt(1,p_id);
		    	ps.setString(2,api_secret);
		    	int rows = ps.executeUpdate();
		    	ps.close();
		    	if( rows <= 0 ){
		    		con.rollback();
					con.setAutoCommit(true);
					con.close();
					send_error(resp);
		    	}
     	  }
		  if ( has_token.equals("YES") ){
		    	token_url = (String) obj.get("api_token_url");
		    	ps = con.prepareStatement(Partner_url_query);
		    	ps.setInt(1,p_id);
		    	ps.setString(2,"Token");
		    	ps.setString(3,null);
		    	ps.setString(4,token_url);
		    	ps.setString(5,"This url is used to obtain authenticaton token. ");
		    	int rows = ps.executeUpdate();
		    	ps.close();
		    	if( rows <= 0 ){
		    		con.rollback();
					con.setAutoCommit(true);
					con.close();
					send_error(resp);
		    	}
		  }
		  ps = con.prepareStatement(Partner_url_query);
		  Iterator<JSONObject> it = urls.iterator();
		  while(it.hasNext()){
			JSONObject url_obj = it.next();
			ps.setInt(1,p_id);
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
		  con.commit();  
		  con.close();
		  
		}catch(Exception e){
			e.printStackTrace();
			if(con != null ){
			   con.close();
			}   
			send_error(resp);
		}
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
