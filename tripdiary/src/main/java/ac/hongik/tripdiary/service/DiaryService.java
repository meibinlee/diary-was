package ac.hongik.tripdiary.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import ac.hongik.tripdiary.data.Diary;
import ac.hongik.tripdiary.data.Result;

@Service
public class DiaryService {
	private Logger logger = LoggerFactory.getLogger(DiaryService.class);
	@Autowired NamedParameterJdbcTemplate namedJdbcTemplate;
	
	public Result listDiary(String user_id) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT * FROM diaries");
	       	sql.append(" WHERE user_id=:user_id");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user_id);
	
	       	List<Diary> list = namedJdbcTemplate.query(sql.toString(), map, new DiaryRowMapper());
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
	
	public Result createDiary(Diary diary) {
		Result result = new Result();
		
		Diary item = selectDiary(diary.user_id, diary.city_id);
		if(item != null) {
       		result.result = Result.FAIL;
       		result.error = "Diary Duplicated.";
       		return result;
		}
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("INSERT INTO diaries");
	       	sql.append(" (user_id, title, city_id)");
	       	sql.append(" VALUES(:user_id, :title, :city_id) ");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", diary.user_id);
	        map.put("title", diary.title);
	        map.put("city_id", diary.city_id);
	
	        int row = namedJdbcTemplate.update(sql.toString(), map);
	        if(row > 0) {
	        	result.result = Result.SUCCESS;
	        }
	        else {
	        	result.result = Result.FAIL;
	        	result.error = "Insert Error.";
	        }

	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	private Diary selectDiary(String user_id, int city_id) {
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT * FROM diaries");
	       	sql.append(" WHERE user_id=:user_id AND city_id=:city_id");
	        logger.debug(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user_id", user_id);
	        map.put("city_id", city_id);
	
	       	List<Diary> list = namedJdbcTemplate.query(sql.toString(), map, new DiaryRowMapper());
	       	if(list.size() > 0) {
	       		return list.get(0);	//dairy목록 
	       	}
	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
	   	}
	   	return null;
	}
}
