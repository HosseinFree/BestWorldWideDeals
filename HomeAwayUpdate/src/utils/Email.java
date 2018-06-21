package utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	private String username;
	private String pass;
	private String email_Server;
    private int port;
	
	public Email(String email_serve, String UserName, String Password, int port) {
		this.setEmail_Server(email_serve);
		this.setUsername(UserName);
		this.setPass(Password);
        this.setPort(port);
	}

	private int getPort() {
		return port;
	}

	private void setPort(int port) {
		this.port = port;
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

	public String getEmail_Server() {
		return email_Server;
	}

	public void setEmail_Server(String email_Server) {
		this.email_Server = email_Server;
	}

	private Session getEmailSession() {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", this.getEmail_Server());
		prop.put("mail.smtp.port", this.getPort());
		prop.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(prop);
		return session;
	}

	private Message createMessage(Session session, String from, String emailLabel, String to, String subject,
			String content, String contentType) throws Exception {
		Message msg = new MimeMessage(session);
		Address fromAddr = new InternetAddress(from, emailLabel);
		Address toAddr = new InternetAddress(to);
		msg.setFrom(fromAddr);
		msg.setRecipient(RecipientType.TO, toAddr);
		msg.setSubject(subject);
		msg.setContent(content, contentType);
		return msg;
	}

	private Transport getTransport(Message msg, Session session) throws Exception {
		Transport t = session.getTransport("smtp");
		t.connect(this.getUsername(), this.getPass());
		return t;
	}

	public void sendEmail(String from, String emailLabel, String to, String subject, String content, String contentType)
			throws Exception {

		Session session = this.getEmailSession();
		Message msg = this.createMessage(session, from, emailLabel, to, subject, content, contentType);
		Transport tr = this.getTransport(msg, session);
		tr.sendMessage(msg, msg.getAllRecipients());
		tr.close();
	}

}
