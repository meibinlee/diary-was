package ac.hongik.tripdiary.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import ac.hongik.tripdiary.data.CityName;
import ac.hongik.tripdiary.data.Result;
import ac.hongik.tripdiary.data.User;
import ac.hongik.tripdiary.data.UserCity;

@Service
public class UserService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired NamedParameterJdbcTemplate namedJdbcTemplate;
	
	public Result getUser(User user) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT * FROM users");
	       	sql.append(" WHERE user_id=:user_id");
	        logger.debug(">>>> SQL] " + sql.toString());
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user.user_id);
	
	       	List<User> list = namedJdbcTemplate.query(sql.toString(), map, new UserRowMapper());
	       	if(list.size() > 0) {
	       		User item = list.get(0);
	       		List<CityName> cities = selectCityName(user.user_id);
	       		logger.debug(">>>> cities size] " + cities.size());
	       		for(int i = 0; i < cities.size(); i++) {
	       			CityName city = cities.get(i);
	       			item.city.add(city);
	       			logger.debug(">>>> city.city_id] " + city.city_id);
	       		}
	       		
	       		if(item.user_pw.equals(user.user_pw)) {
		       		result.result = Result.SUCCESS;
		       		result.body = item;
	       		}
		       	else {
		       		result.result = Result.FAIL;
		       		result.error = "Wrong Password.";
		       	} 		
	       	}
	       	else {
	       		result.result = Result.FAIL;
	       		result.error = "User Not Found.";
	       	}

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	public Result addUser(User user) {
		Result result = new Result();
		Result res = getUser(user);
		if(res.result == Result.SUCCESS) {
       		result.result = Result.FAIL;
       		result.error = "User Duplicated.";
       		return result;
		}
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("INSERT INTO users");
	       	sql.append(" (user_id, user_pw, user_name, birthdate, sex, signup_date)");
	       	sql.append(" VALUES(:user_id, :user_pw, :user_name, :birthdate, :sex, :signup_date) ");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user.user_id);
	        map.put("user_pw", user.user_pw);
	        map.put("user_name", user.user_name);
	        map.put("birthdate", user.birthdate);
	        map.put("sex", user.sex);
	        map.put("signup_date", new Date());
	
	        int row = namedJdbcTemplate.update(sql.toString(), map);
	        if(row > 0) {
	        	boolean b = true;
	        	for(int i = 0; i < user.city.size(); i++) {
	        		b = insertCities(user.user_id, user.city.get(i).city_id);
	        		if(b = false) {
	    	        	break;
	        		}
	        	}
	        	if(b = true) {
		        	result.result = Result.SUCCESS;
	        	}
	        	else {
    	        	result.result = Result.FAIL;
    	        	result.error = "Favorite Cities Insert Error.";
	        	}
	        }
	        else {
	        	result.result = Result.FAIL;
	        	result.error = "User Insert Error.";
	        }

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	public Result checkUser(String user_id) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT * FROM users");
	       	sql.append(" WHERE user_id=:user_id");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user_id);
	
	       	List<User> list = namedJdbcTemplate.query(sql.toString(), map, new UserRowMapper());
	       	if(list.size() > 0) {
	       		result.result = Result.FAIL;
	       		result.error = "User ID Duplicated.";
	       	}
	       	else {
	       		result.result = Result.SUCCESS;
	       	}

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;

	}
	
	public Result updateProfile(User user) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("UPDATE users");
	       	sql.append(" SET user_pw=:user_pw, user_name=:user_name");;
	       	sql.append(" WHERE user_id=:user_id");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user.user_id);
	        map.put("user_pw", user.user_pw);
	        map.put("user_name", user.user_name);
	
	        int row = namedJdbcTemplate.update(sql.toString(), map);
	        if(row > 0) {
	        	boolean b = true;
        		b = deleteCities(user.user_id);
	        	for(int i = 0; i < user.city.size(); i++) {
	        		b = insertCities(user.user_id, user.city.get(i).city_id);
	        		if(b == false) {
	    	        	break;
	        		}
	        	}
	        	if(b == true) {
		        	result.result = Result.SUCCESS;
		        	Result r = getProfile(user.user_id);
		        	result.body = r.body;
	        	}
	        	else {
    	        	result.result = Result.FAIL;
    	        	result.error = "Favorite Cities Update Error.";
	        	}
	        }
	        else {
	        	result.result = Result.FAIL;
	        	result.error = "User Update Error.";
	        }

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	private boolean insertCities(String user_id, int city_id) {
		List<UserCity> list = selectCities(user_id, city_id);
		if(list != null && list.size() > 0) {
			return true;
		}
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("INSERT INTO user_cities_favorite");
	       	sql.append(" (user_id, city_id)");
	       	sql.append(" VALUES(:user_id, :city_id) ");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user_id);
	        map.put("city_id", city_id);
	
	        int row = namedJdbcTemplate.update(sql.toString(), map);
	        if(row > 0) {
	        	return true;
	        }

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
	   	}
	   	return false;
	}
	
	private boolean deleteCities(String user_id) {
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("DELETE FROM user_cities_favorite");
	       	sql.append(" WHERE user_id=:user_id");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user_id);
	
	        int row = namedJdbcTemplate.update(sql.toString(), map);
	        if(row > 0) {
	        	return true;
	        }

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
	   	}
	   	return false;
	}
	
	private List<UserCity> selectCities(String user_id, int city_id) {
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT * FROM user_cities_favorite");
	       	if(city_id > 0) {
		       	sql.append(" WHERE user_id=:user_id AND city_id=:city_id");
	       	}
	       	else {
		       	sql.append(" WHERE user_id=:user_id");
	       	}
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user_id);
	        map.put("city_id", city_id);
	
	       	List<UserCity> list = namedJdbcTemplate.query(sql.toString(), map, new UserCityRowMapper());
	       	return list;
	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
	   	}
	   	return null;
	}
	
	private List<CityName> selectCityName(String user_id) {
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT city_id, city FROM trip_diary_db.cities");
	       	sql.append(" WHERE city_id IN");
	       	sql.append("(SELECT city_id FROM user_cities_favorite WHERE user_id=:user_id);");     	
	       	 
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user_id);
	
	       	List<CityName> list = namedJdbcTemplate.query(sql.toString(), map, new CityNameRowMapper());
	       	return list;
	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
	   	}
	   	return null;
	}
	
	public Result getProfile(String user_id) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT * FROM users");
	       	sql.append(" WHERE user_id=:user_id");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user_id);
	
	       	List<User> list = namedJdbcTemplate.query(sql.toString(), map, new UserRowMapper());
	       	if(list.size() > 0) {
	       		User user = list.get(0);
	       		List<CityName> cities = selectCityName(user_id);
	       		for(int i = 0; i < cities.size(); i++) {
	       			CityName city = cities.get(i);
	       			user.city.add(city);
	       		}
	       		result.result = Result.SUCCESS;
	       		result.body = user;
	       	}
	       	else {
	       		result.result = Result.FAIL;
	       		result.error = "User Not Found";
	       	}

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;

	}
}
