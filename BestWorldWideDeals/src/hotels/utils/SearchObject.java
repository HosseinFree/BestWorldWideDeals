package hotels.utils;

import java.util.GregorianCalendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SearchObject {
	
	private double lat;
	private double lan;
	private String fromDate;
	private String toDate;
	private int room_type;
	private int num_of_rooms;
	private JSONArray rooms_info;
	private String locale;
	private long num_adults;
	private long num_childs;
	
	
	public SearchObject(JSONObject searchObj){
		this.setLat(Double.parseDouble((String) ((JSONObject) searchObj.get("destination")).get("lat") ) );
		this.setLan(Double.parseDouble((String) ((JSONObject) searchObj.get("destination")).get("lan") ) );
		this.setFromDate((String) searchObj.get("from_date"));
		this.setToDate((String) searchObj.get("to_date"));
		/*String fromDate[] = ((String) searchObj.get("from_date")).split("-");
		String toDate[] = ((String) searchObj.get("to_date")).split("-");
		this.setFromDate(new GregorianCalendar(Integer.parseInt (fromDate[2]),Integer.parseInt(fromDate[1]),Integer.parseInt(fromDate[0])));
		this.setToDate(new GregorianCalendar(Integer.parseInt(toDate[2]),Integer.parseInt(toDate[1]),Integer.parseInt(toDate[0])));*/
		this.setRoom_type(Integer.parseInt((String)searchObj.get("room_type") ) );
		this.setNum_of_rooms(Integer.parseInt( (String)searchObj.get("num_of_rooms") ) );
		this.setRooms_info((JSONArray)searchObj.get("rooms_info"));
		this.setLocale((String)searchObj.get("locale"));
		this.setNum_adults((Long)searchObj.get("num_adults"));
		this.setNum_childs((Long)searchObj.get("num_child"));
	}	
	
	public long getNum_adults() {
		return num_adults;
	}
	private void setNum_adults(long num_adults) {
		this.num_adults = num_adults;
	}
	public long getNum_childs() {
		return num_childs;
	}
	private void setNum_childs(long num_childs) {
		this.num_childs = num_childs;
	}
	public double getLat() {
		return lat;
	}
	private void setLat(double lat) {
		this.lat = lat;
	}
	public double getLan() {
		return lan;
	}
	private void setLan(double lan) {
		this.lan = lan;
	}
	public String getFromDate() {
		return fromDate;
	}
	private void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	private void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getRoom_type() {
		return room_type;
	}
	private void setRoom_type(int room_type) {
		this.room_type = room_type;
	}
	public int getNum_of_rooms() {
		return num_of_rooms;
	}
	private void setNum_of_rooms(int num_of_rooms) {
		this.num_of_rooms = num_of_rooms;
	}
	public JSONArray getRooms_info() {
		return rooms_info;
	}
	private void setRooms_info(JSONArray rooms_info) {
		this.rooms_info = rooms_info;
	}
	public String getLocale() {
		return locale;
	}
	private void setLocale(String locale) {
		this.locale = locale;
	}
}
