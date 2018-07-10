package hotels.interfaces;

import org.json.simple.JSONObject;

import hotels.utils.SearchObject;

public interface HotelSearch {
   JSONObject getSearchResults(int dist_from_dest) throws Exception;
}
