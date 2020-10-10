package ac.hongik.tripdiary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ac.hongik.tripdiary.data.Posting;
import ac.hongik.tripdiary.data.Result;
import ac.hongik.tripdiary.service.PostingService;

@Controller
@RequestMapping(value="/posting")	
public class PostingController {
	private Logger logger = LoggerFactory.getLogger(PostingController.class);
	
	@Autowired
	PostingService postingService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)	
	@ResponseBody
	public Result create(@RequestBody Posting posting) { 

		logger.debug("REQ] /posting/create diary_id=" + posting.diary_id);
		
		Result res = postingService.createPosting(posting);

		logger.info("WAS] /posting/create result="+ res.result + " error="+ res.error +" user_id=null" + " diary_id=" + posting.diary_id);
		
		return res;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)	
	@ResponseBody
	public Result list(@RequestParam String diary_id) {
		
		logger.debug("REQ] /posting/list diary_id=" + diary_id);
		
		Result res = postingService.listPosting(diary_id);
		
		logger.info("WAS] /posting/list result="+ res.result + " error="+ res.error +" user_id=null" + " diary_id=" + diary_id);
		
		return res;
	}

	@RequestMapping(value="/upload", method=RequestMethod.POST)	
	@ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, 
    		 @RequestParam("diary_id") String diary_id,
    		 @RequestParam("photo") String photo, 
    		 @RequestParam("diaryment") String diaryment, 
    		 @RequestParam("posting_date") String posting_date) {
		
		logger.debug("REQ] /posting/upload diary_id=" + diary_id);
		
		Posting posting = new Posting();
		posting.diary_id = Integer.parseInt(diary_id);
		posting.photo = photo;
		posting.diaryment = diaryment;
		posting.posting_date = posting_date;
		
    	Result res = postingService.addPicture(file, posting);

		logger.info("WAS] /posting/upload result="+ res.result + " error="+ res.error +" user_id=null" + " diary_id=" + diary_id);
				
		return res;
	}
}
