

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Report_lib {
     
	/**
	 * 
	 * @param db
	 * @param title
	 * @throws Exception 
	 */
	public static void store_report_to_DB(DB db,String title) throws Exception {
	   Connection con;
       con = db.getConnection();
	   String query = "INSERT INTO system_reports(rep_title) VALUES(?)";
	   PreparedStatement ps = con.prepareStatement(query);
	   ps.setString(1,title);
	   ps.executeUpdate();
	   con.close();
	}

	/**
	 * 
	 * @param db
	 * @param title
	 * @param repContent
	 * @throws Exception 
	 */
	public static void store_report_with_content_to_DB(DB db, String title, String repContent) throws Exception{
		
		Connection con;
	    con = db.getConnection();
 		con.setAutoCommit(false);
	    String query = "INSERT INTO system_reports(rep_title) VALUES(?)";
	    String content_query = "INSERT INTO system_reports_content(rep_id,report_content) VALUES(?,?)";
        String cols[] = {"rep_id"};
	    PreparedStatement ps = con.prepareStatement(query,cols);
	    ps.setString(1,title);
	    ps.executeUpdate();
	    ResultSet rs = ps.getGeneratedKeys();
	    int rep_id = -1;
	    if(rs.next()){
	       rep_id = rs.getInt(1);
	       ps.close();
	    }
	     
	    if(rep_id < 0 ){
	       con.rollback();
	       con.close();
	       throw new Exception("Could not store data in database.");
	    }else{
	       ps = con.prepareStatement(content_query);	
	       ps.setInt(1,rep_id);
	       ps.setString(2,repContent);
		   ps.executeUpdate();
		   con.commit();
		   ps.close();
		   con.close();
		}
	   
	}	
	
}
