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

public class AdminLoginFilter implements Filter {
	  
	  public void init(FilterConfig filterConfig){
		 
	  }
	  public void destroy() {
		      
	  } 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session  = req.getSession(false);
		
		if(session != null){
		    
		    String attr = (String) session.getAttribute("userlogedin");
		    
		    if( attr != null ){
		    
		    	res.sendRedirect(req.getContextPath()+"/Admin/Index_Admin.jsp");
    	        return;
    	        
		    }
		    
		}
		
		fc.doFilter(request, response);
	}

}
