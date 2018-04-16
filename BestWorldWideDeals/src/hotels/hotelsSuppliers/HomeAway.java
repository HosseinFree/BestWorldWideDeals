package hotels.hotelsSuppliers;

import java.util.Map;

import hotels.interfaces.UpdateAuthToken;


public class HomeAway implements UpdateAuthToken {

	@Override
	public boolean isTokenValid() {
		return false;
	}

	@Override
	public void updateToken() {
		
	}

	@Override
	public Map<String, String> getApiCredentials() {
		
		return null;
	}

}
