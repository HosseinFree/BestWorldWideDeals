package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
    String log_file_url;
	public Logger(String log_file_url){
		this.setLog_file_url(log_file_url);
	}
	
	private String getLog_file_url() {
		return log_file_url;
	}
	
	private void setLog_file_url(String log_file_url) {
		this.log_file_url = log_file_url;
	}
	
	public void log_report(String report,boolean with_time_stamp){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date new_date = new Date();
		FileWriter fw;
		try {
			fw = new FileWriter(this.getLog_file_url(),true);
		  	BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
		    if(with_time_stamp)
		       out.println("["+df.format(new_date)+"]  "+report);
		    else
		       out.println(report);	
		    out.close();
		    fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void main(String[] args){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date new_date = new Date();
		String log_file_url = df.format(new_date)+"-file.log";
		Logger logger = new Logger("./"+log_file_url);
		logger.log_report("Hello");
		logger.log_report("World!!!");
		
	}*/
	
}
