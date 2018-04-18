package hotels.utils;

import java.util.Date;

public class HotelAuthToken {
	
    private String token;
    private Date expiryDate;
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
 
}