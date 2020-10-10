package ac.hongik.tripdiary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import ac.hongik.tripdiary.data.RanksAllAndAge;
import ac.hongik.tripdiary.data.RanksSex;
import ac.hongik.tripdiary.data.Result;

@Service
public class RanksService {
	private final static int LIMIT_ALL = 5;
	private final static int LIMIT_SEX = 3;
	private final static int LIMIT_AGE = 1;
	private Logger logger = LoggerFactory.getLogger(RanksService.class);
	@Autowired NamedParameterJdbcTemplate namedJdbcTemplate;
	
	public Result getAll() {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT c.city, ucf.city_id, count(ucf.city_id) as count");  
	        sql.append(" FROM cities as c, user_cities_favorite as ucf");
	        sql.append(" WHERE c.city_id = ucf.city_id");  
	        sql.append(" GROUP BY ucf.city_id");
	        sql.append(" ORDER BY count desc");  
	        sql.append(" LIMIT " + LIMIT_ALL);
	        
	        
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	
	       	List<RanksAllAndAge> list = namedJdbcTemplate.query(sql.toString(), map, new RanksAllAndAgeRowMapper());
	       	if(list.size() > 0) {
	       		result.result = Result.SUCCESS;
	       		result.body = list;	
	       	}
	       	else {
	       		result.result = Result.FAIL;
	       		result.error = "Ranks Not Found.";
	       	}

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	public Result getFemale() {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT u.sex, c.city, ucf.city_id, count(ucf.city_id) as count");  
	        sql.append(" FROM cities as c, user_cities_favorite as ucf, users as u");
	        sql.append(" WHERE c.city_id = ucf.city_id and u.sex = 'F' and ucf.user_id = u.user_id");  
	        sql.append(" GROUP BY ucf.city_id");
	        sql.append(" ORDER BY count desc");  
	        sql.append(" LIMIT " + LIMIT_SEX);
	            
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	
	       	List<RanksSex> list = namedJdbcTemplate.query(sql.toString(), map, new RanksSexRowMapper());
	       	if(list.size() > 0) {
	       		result.result = Result.SUCCESS;
	       		result.body = list;	
	       	}
	       	else {
	       		result.result = Result.FAIL;
	       		result.error = "Ranks Not Found.";
	       	}

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	public Result getMale() {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT u.sex, c.city, ucf.city_id, count(ucf.city_id) as count");  
	        sql.append(" FROM cities as c, user_cities_favorite as ucf, users as u");
	        sql.append(" WHERE c.city_id = ucf.city_id and u.sex = 'M' and ucf.user_id = u.user_id");  
	        sql.append(" GROUP BY ucf.city_id");
	        sql.append(" ORDER BY count desc");  
	        sql.append(" LIMIT " + LIMIT_SEX);
	            
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	
	       	List<RanksSex> list = namedJdbcTemplate.query(sql.toString(), map, new RanksSexRowMapper());
	       	if(list.size() > 0) {
	       		result.result = Result.SUCCESS;
	       		result.body = list;	
	       	}
	       	else {
	       		result.result = Result.FAIL;
	       		result.error = "Ranks Not Found.";
	       	}

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	public Result getAge() {
		Result result = new Result();
		List<RanksAllAndAge> list = new ArrayList<RanksAllAndAge>();

   		for(int i=1; i<=5; i++) {
   			RanksAllAndAge ra = selectAge(i);
   			if(ra != null) {
   				list.add(ra);
   			}
   		}
   		result.body = list;
		return result;
	}
	
	private RanksAllAndAge selectAge(int i) {
	   	try {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT age_ranks.city, age_ranks.city_id, sum(age_ranks.count) as count FROM (");  
	        sql.append(" SELECT YEAR(CURDATE()) - YEAR(u.birthdate) AS age, c.city, ucf.city_id, count(ucf.city_id) as count");
	        sql.append(" FROM cities as c, user_cities_favorite as ucf, users as u");  
	        sql.append(" WHERE c.city_id = ucf.city_id and ucf.user_id = u.user_id  AND YEAR(CURDATE()) - YEAR(u.birthdate) LIKE '"+ i +"%'");
	      //  sql.append(" WHERE c.city_id = ucf.city_id and ucf.user_id = u.user_id  AND YEAR(CURDATE()) - YEAR(u.birthdate) LIKE '1%'");
	        sql.append(" GROUP BY ucf.city_id, age");  
	        sql.append(" ) as age_ranks");
	        sql.append(" GROUP BY age_ranks.city_id");
	        sql.append(" ORDER BY count desc");
	        sql.append(" LIMIT " + LIMIT_AGE);
	        
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	
	       	List<RanksAllAndAge> list = namedJdbcTemplate.query(sql.toString(), map, new RanksAllAndAgeRowMapper());
	       	if(list.size() > 0) {
	       		return list.get(0); //ranks age
	       	}
	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
	   	}
	   	return null;
	}
}


