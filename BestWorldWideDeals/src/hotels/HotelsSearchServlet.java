package hotels;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class HotelsSearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONParser parser = new JSONParser();
		System.out.println(req.getParameter("data"));
		
		try{
			JSONObject obj = (JSONObject) parser.parse(req.getParameter("data"));
			
			System.out.println(obj.get("destination"));
			System.out.println(((JSONObject)obj.get("destination")).get("lat"));
			
		}catch(Exception e){
		   e.printStackTrace();
	    }
	}
}
