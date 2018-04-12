package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.DB;

public class GetPartnersList extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    String partner_cat = req.getParameter("partner_cat");
	    StringTokenizer str = new StringTokenizer(partner_cat,"_");
		String category = str.nextToken();
		String sub_category = str.nextToken();
		
		String all_partners_query ="SELECT A.partner_id,partner_name,partner_category,partner_subcategory,partner_data_format,partner_languages,partner_currencies,has_key,has_secret,"
				                   + "has_token,partner_key,partner_secret,partner_token,expiry_date"
				                   + " FROM partners_info A "
				                   + " LEFT JOIN partners_keys B ON A.partner_id = B.partner_id"
				                   + " LEFT JOIN partners_secrets C ON A.partner_id = C.partner_id"
				                   + " LEFT JOIN partners_tokens D ON A.partner_id = D.partner_id "
				                   + " ORDER BY partner_name ASC;";
		
		String spec_partners_query ="SELECT A.partner_id,partner_name,partner_category,partner_subcategory,partner_data_format,partner_languages,partner_currencies,has_key,has_secret,"
                + "has_token,partner_key,partner_secret,partner_token,expiry_date"
                + " FROM partners_info A "
                + " LEFT JOIN partners_keys B ON A.partner_id = B.partner_id"
                + " LEFT JOIN partners_secrets C ON A.partner_id = C.partner_id"
                + " LEFT JOIN partners_tokens D ON A.partner_id = D.partner_id "
                + " WHERE A.partner_category = ? AND A.partner_subcategory = ? "
                + " ORDER BY partner_name ASC;";
		
		String spec_all_cat_partners_query ="SELECT A.partner_id,partner_name,partner_category,partner_subcategory,partner_data_format,partner_languages,"
				+ "partner_currencies,has_key,has_secret,has_token,partner_key,partner_secret,partner_token,expiry_date"
                + " FROM partners_info A "
                + " LEFT JOIN partners_keys B ON A.partner_id = B.partner_id"
                + " LEFT JOIN partners_secrets C ON A.partner_id = C.partner_id"
                + " LEFT JOIN partners_tokens D ON A.partner_id = D.partner_id "
                + " WHERE A.partner_category = ?"
                + " ORDER BY partner_name ASC;";
		
		
		
		String partners_url_query = "SELECT url_type,type_other,url,description FROM partners_urls WHERE partner_id = ? ;";
		
		DB database = new DB("jdbc/bwwd_adminDB");
		Connection con = null;
		
		try{
			
			con = database.getConnection();
			PreparedStatement ps = null ;
			Map<String,Map<String,String>> partners = new HashMap<String,Map<String,String>>();
			ResultSet rs = null;
			
			if( category.equals("All" ) ){
				
				if( sub_category.equals("Partners") ){ 
					
				    ps = con.prepareStatement(all_partners_query);
				    rs = ps.executeQuery();
				    
				}else{
					
					ps = con.prepareStatement(spec_all_cat_partners_query);
					ps.setString(1,sub_category);
					rs = ps.executeQuery();
					
				}
				
		    }else{
		    	
				  ps = con.prepareStatement(spec_partners_query);
				  ps.setString(1,category);
				  ps.setString(2,sub_category);
				  rs = ps.executeQuery();
				  
		    }
			
			while(rs.next()){
				
				 Map<String,String> temp_map = new HashMap<String,String>();
				 String p_name = rs.getString(2);
				 temp_map.put("p_id",rs.getString(1));
				 temp_map.put("p_name",p_name);
				 temp_map.put("p_cat",rs.getString(3));
				 temp_map.put("p_subcat",rs.getString(4));
				 temp_map.put("p_data_form",rs.getString(5));
				 temp_map.put("p_languages",rs.getString(6));
				 temp_map.put("p_currencies",rs.getString(7));
				 temp_map.put("p_has_key",rs.getString(8));
				 temp_map.put("p_has_secret",rs.getString(9));
				 temp_map.put("p_has_token",rs.getString(10));
				 temp_map.put("p_key",rs.getString(11));
				 temp_map.put("p_secret",rs.getString(12));
				 temp_map.put("p_token",rs.getString(13));
				 temp_map.put("p_expiry_date",rs.getString(14));
				 partners.put(p_name,temp_map);
				 
		    }
			
			JSONArray output = new JSONArray();
			ps = con.prepareStatement(partners_url_query);
			
			for(String key:partners.keySet() ){
				
				JSONObject obj = new JSONObject();
				Map<String,String> temp_map = partners.get(key);
				Integer p_id = Integer.parseInt(temp_map.get("p_id"));
				
				for(String t_key:temp_map.keySet() ){
					obj.put(t_key, temp_map.get(t_key));
				}
				
				ps.setInt(1,p_id);
				rs = ps.executeQuery();
				JSONArray urls = new JSONArray();
				
				while(rs.next()){
					
					JSONObject temp_obj = new JSONObject();
					temp_obj.put("url_type",rs.getString(1));
					temp_obj.put("type_other",rs.getString(2));
					temp_obj.put("url",rs.getString(3));
					temp_obj.put("description",rs.getString(4));
					urls.add(temp_obj);
					
				}
				
				rs.close();
				obj.put("urls",urls.toJSONString());
				output.add(obj);
				
			}
			
			resp.setContentType("text/json");
	        resp.setHeader("Cache-Control","no-cache");
	        resp.getWriter().write(output.toJSONString());
		    con.close();
		    ps.close();
		    
		}catch(Exception e){
				e.printStackTrace();
		}

	}

}
