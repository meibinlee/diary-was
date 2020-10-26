package ac.hongik.tripdiary.data;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LogMessage {
	public String path;
	public int result;
	public String error;
	public String user_id;
	public int diary_id;
	public String birthdate;
	public String sex;
	public ArrayList<CityName> city_list = new ArrayList<CityName>();
	
	public static LogMessage getLogMessage(String path, Result res, User user, Diary diary) {
		LogMessage msg = new LogMessage();
		msg.path = path;
		msg.result = res.result;
		msg.error = res.error;
		if(user != null) {
			msg.user_id = user.user_id;
			if(user.birthdate != null && user.birthdate.length() > 7) {
				msg.birthdate = user.birthdate;
			}
			if(user.sex != null) {
				msg.sex = user.sex;
			}
			if(user.city.size() > 0) {
				for(CityName city : user.city) {
					msg.city_list.add(city);
				}
			}
		}
		if(diary != null) {
			msg.diary_id = diary.diary_id;
		}
		return msg;
	}
	
	public String toString() {
		try {

			ObjectMapper jsonMapper = new ObjectMapper();
			return jsonMapper.writeValueAsString(this);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}
}
