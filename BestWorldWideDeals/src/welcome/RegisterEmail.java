package welcome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class RegisterEmail extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String select_query = "SELECT ID FROM bwwd_emails WHERE(email=?);";
        String query = "INSERT INTO bwwd_emails(email) VALUES(?);"; 	  
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        resp.setContentType("text");
		resp.setHeader("Cache-Control","no-cache");
        try{
        	/*Class.forName("com.mysql.jdbc.Driver").newInstance();
    		String url ="jdbc:mysql://mysql3000.mochahost.com:3306/main_bwwd";
            con = DriverManager.getConnection(url,"main_root","551360");*/
			//con.setAutoCommit(false);
        	
        	Context ctx = new InitialContext();
   		    Context envContext = (Context) ctx.lookup("java:comp/env");
   		    DataSource ds = (DataSource) envContext.lookup("jdbc/main_bwwd");
   		    con = ds.getConnection();
        	
            ps = con.prepareStatement(select_query);
            ps.setString(1,email);
            rs = ps.executeQuery();
            if(rs.next()){ // this email already exist
            	con.close();
            	resp.getWriter().write("exist");
            }else{
            	ps = con.prepareStatement(query);
    			ps.setString(1,email);
    			int rows = ps.executeUpdate();
    			if(rows>=1){
    				con.close();
    				resp.getWriter().write("success");
    			}else{
    				con.close();
    				resp.getWriter().write("error");
    			}
    	    }
            
        }catch(Exception e){
        	resp.getWriter().write("error");
        	e.printStackTrace();
        }
     }
}	