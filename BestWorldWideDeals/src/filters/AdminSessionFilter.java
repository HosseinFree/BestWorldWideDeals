package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminSessionFilter implements Filter {

	  public void init(FilterConfig filterConfig){
		  
	  }
	  public void destroy() {
		      
	  } 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
	    HttpSession session = null;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String req_type = req.getParameter("req_type");
		if( req_type != null && req_type.equals("ajax") ){
			session = req.getSession(false);
		    if(session == null){
				response.getWriter().write("logedout");
				return;
			}else{
			    String attr = (String) session.getAttribute("userlogedin");
				if( attr == null ){
					response.getWriter().write("logedout");
					return;
				}
			}
		}else{
			session = req.getSession(false);
			if(session == null){
				res.sendRedirect(req.getContextPath()+"/Admin_Login.jsp");
    	    	return;
			}else{
				String attr = (String) session.getAttribute("userlogedin");
				if( attr == null ){
					res.sendRedirect(req.getContextPath()+"/Admin_Login.jsp");
					return;
				}
			}
		}
		fc.doFilter(request, response);
	}
}
