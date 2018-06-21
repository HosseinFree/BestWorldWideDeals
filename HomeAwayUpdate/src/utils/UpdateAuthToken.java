package utils;

import java.util.Map;

public interface UpdateAuthToken {
   public int isTokenValid() throws Exception; // checks if the token is stil valid
   public void updateToken(boolean token_exist) throws Exception; // updates the token if it is expired
   public Map<String,String> getApiCredentials() throws Exception; // returns credentials required to get token  
}
