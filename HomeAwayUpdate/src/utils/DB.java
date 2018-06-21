package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB{
	 public static String DB_base_url = "jdbc:mysql://localhost:3306/";
	 //public static String DB_base_url = "jdbc:mysql://ec2-35-172-5-53.compute-1.amazonaws.com:3306/";
	 private String db;
	 private String username;
	 private String pass;
	 
	 public DB(String DB,String usern,String pass){
		 this.setDb(DB+"?useSSL=false");
		 this.setUsername(usern);
		 this.setPass(pass);
	 }
     
	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Connection getConnection() throws Exception{
		    Connection con = null;
		    Class.forName("com.mysql.jdbc.Driver");
            String url = this.getDb();
            String user = this.getUsername();
            String password = this.getPass();
            con = DriverManager.getConnection(url,user,password);
           return con;
	 }
}
