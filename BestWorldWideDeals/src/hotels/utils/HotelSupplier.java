package hotels.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class HotelSupplier {
	
    private String name;
	private Map<String,String> api_creds;
	private Map<String,String> api_urls;
	
    public HotelSupplier(String supplier_name){
		this.name = supplier_name;
		api_creds = new HashMap<String,String>();
		api_urls = new HashMap<String,String>();
	}
	
	public abstract void populate_api_creds();
	
	public abstract void populate_api_urls();
	
	protected void add_cred(String credName,String credValue){
	   this.api_creds.put(credName, credValue);
    }
	
	protected void add_url(String urlName,String urlValue){
		   this.api_urls.put(urlName, urlValue);
	}
	
	public String getName() {
		return name;
	}

	public Map<String, String> getApi_creds() {
		return api_creds;
	}

	public Map<String, String> getApi_urls() {
		return api_urls;
	}
	
}
