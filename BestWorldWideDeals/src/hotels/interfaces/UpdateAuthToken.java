package hotels.interfaces;

import java.util.Map;

public interface UpdateAuthToken {
   public boolean isTokenValid(); // checks if the token is stil valid
   public void updateToken(); // updates the token if it is expired
   public Map<String,String> getApiCredentials(); // returns credentials required to get token  
}
