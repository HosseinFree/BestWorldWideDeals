package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.DB;

public class HandleAdminLogin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String admin_login = "SELECT admin_id FROM admin_credentials WHERE admin_username = ? AND admin_pass=?;";
		DB database = new DB("jdbc/bwwd_adminDB");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String un = req.getParameter("username");
		String pass = req.getParameter("pass");
		try{
			
			con = database.getConnection();
		    ps = con.prepareStatement(admin_login);
		    ps.setString(1,un);
		    ps.setString(2,pass);
		    rs = ps.executeQuery();
		    
		    if(rs.next()){
		       HttpSession session = req.getSession();
		       session.setMaxInactiveInterval(1200);
		       session.setAttribute("userlogedin","true");
		       resp.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
		       resp.sendRedirect("./Admin/Index_Admin.jsp");
		    }else{
		    	resp.setContentType("text");
		        resp.setHeader("Cache-Control","no-cache");
		        req.getSession().setAttribute("admin_login_error","not_exist");
		        req.getHeader("Referer");
		        resp.sendRedirect("./Admin_Login.jsp");
		        //req.getRequestDispatcher("./Admin_Login.jsp").forward(req, resp);
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			resp.setContentType("text");
	        resp.setHeader("Cache-Control","no-cache");
	        req.setAttribute("admin_login_error","system_error");
	        req.getRequestDispatcher("./Admin_Login.jsp").forward(req, resp);
	     }
		
	}

}