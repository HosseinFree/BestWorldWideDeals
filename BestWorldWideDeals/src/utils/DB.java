package utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB{
	
	 private String db;
	  
	 public DB(String DB){
		 this.db = DB;
	 }

	 public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NamingException{
		 Connection con;
		 Context ctx = new InitialContext();
		 Context envContext = (Context) ctx.lookup("java:comp/env");
		 DataSource ds = (DataSource) envContext.lookup(this.db);
		 con = ds.getConnection();
		 return con;
	 }
}
