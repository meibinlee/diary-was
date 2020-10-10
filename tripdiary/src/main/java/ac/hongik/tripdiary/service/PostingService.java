package ac.hongik.tripdiary.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ac.hongik.tripdiary.data.Posting;
import ac.hongik.tripdiary.data.Result;
import ac.hongik.tripdiary.tools.DateTool;

@Service
public class PostingService {
	private Logger logger = LoggerFactory.getLogger(PostingService.class);
	@Autowired NamedParameterJdbcTemplate namedJdbcTemplate;
	@Autowired private Environment env;

	public Result listPosting(String diary_id) {
		Result result = new Result();
		
	   	try {
	        StringBuffer sql = new StringBuffer();
	       	sql.append("SELECT * FROM postings");
	       	sql.append(" WHERE diary_id=:diary_id");
	        logger.debug(">>>> SQL] " + sql.toString());
	
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
	        logger.debug(">>>> SQL] " + sql.toString());
	
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
	
	public Result addPicture(MultipartFile file, Posting posting) {
		Result result;
    	try {
    		int offset = posting.photo.lastIndexOf("."); //file name sent from browser
    		String ext = posting.photo.substring(offset);
    	   	String dir = env.getProperty("dir.upload");
    	   	String fname = posting.diary_id + "-" + DateTool.toDatestamp() + ext;
            Path filepath = Paths.get(dir, fname);
            file.transferTo(filepath);

    	   	posting.photo = fname; //file name with diary_id - timestamp
    	   	result = createPosting(posting);
    	}
		catch(Exception e) {
			result = new Result();
    		result.result = Result.FAIL;
    		result.error = e.toString();
		}
		return result;
	}
}
