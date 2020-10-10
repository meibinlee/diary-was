package ac.hongik.tripdiary.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import ac.hongik.tripdiary.data.Code;
import ac.hongik.tripdiary.data.Result;

@Service
public class CodeService {
	private Logger logger = LoggerFactory.getLogger(CodeService.class);
	@Autowired NamedParameterJdbcTemplate namedJdbcTemplate;
	
	public Result getCountry() {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT distinct(country)");  
	        sql.append(" FROM trip_diary_db.cities");

	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	
	       	List<String> list = namedJdbcTemplate.query(sql.toString(), map, new CountryRowMapper());
	       	if(list.size() > 0) {
	       		result.result = Result.SUCCESS;
	       		result.body = list;	
	       	}
	       	else {
	       		result.result = Result.FAIL;
	       		result.error = "Diary Not Found.";
	       	}

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	public Result getCity(String country) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT city_id, country, city,");
	        sql.append(" ST_X(map_location) as x, ST_Y(map_location) as y");        
	        sql.append(" FROM trip_diary_db.cities");
	       	if(country != null && !country.equals("")) {
	       		sql.append(" WHERE country LIKE '%"+ country +"%'");
	       	}
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	
	       	List<Code> list = namedJdbcTemplate.query(sql.toString(), map, new CodeRowMapper());
	       	if(list.size() > 0) {
	       		result.result = Result.SUCCESS;
	       		result.body = list;	
	       	}
	       	else {
	       		result.result = Result.FAIL;
	       		result.error = "Diary Not Found.";
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
