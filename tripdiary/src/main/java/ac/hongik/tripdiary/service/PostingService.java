package ac.hongik.tripdiary.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import ac.hongik.tripdiary.data.Posting;
import ac.hongik.tripdiary.data.Result;

@Service
public class PostingService {
	private Logger logger = LoggerFactory.getLogger(PostingService.class);
	@Autowired NamedParameterJdbcTemplate namedJdbcTemplate;
	
	public Result listPosting(String diary_id) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT * FROM postings");
	       	sql.append(" WHERE diary_id=:diary_id");
	        logger.info(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("diary_id", diary_id);
	
	       	List<Posting> list = namedJdbcTemplate.query(sql.toString(), map, new PostingRowMapper());
	       	if(list.size() >= 0) {
	       		result.result = Result.SUCCESS;
	       		result.body = list;
	       	}
	    } catch(Exception e) {
	   		logger.error(e.toString());
	   		e.printStackTrace();
       		result.result = Result.FAIL;
       		result.error = e.toString();
	   	}
		
		return result;
	}
	
	public Result createPosting(Posting posting) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("INSERT INTO postings");
	       	sql.append(" (diary_id, photo, diaryment, posting_date)");
	       	sql.append(" VALUES(:diary_id, :photo, :diaryment, :posting_date) ");
	        logger.info(">>>> SQL] " + sql.toString());
	
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("diary_id", posting.diary_id);
	        map.put("photo", posting.photo);
	        map.put("diaryment", posting.diaryment);
	        map.put("posting_date", posting.posting_date);
	
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
}
