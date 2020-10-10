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

import ac.hongik.tripdiary.data.Diary;
import ac.hongik.tripdiary.data.Result;
import ac.hongik.tripdiary.service.DiaryService;

@Controller
@RequestMapping(value="/diary")	
public class DiaryController {
	private Logger logger = LoggerFactory.getLogger(DiaryController.class);
	
	@Autowired
	DiaryService diaryService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)	
	@ResponseBody
	public Result create(@RequestBody Diary diary) { 

		logger.debug("REQ] /diary/create user_id"+ diary.user_id +"diary_id=" + diary.diary_id);
		
		Result res = diaryService.createDiary(diary);

		logger.info("WAS] /diary/create result="+ res.result + " error="+ res.error +" user_id="+ diary.user_id + " diary_id=" + diary.diary_id);
	
		return res;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)	
	@ResponseBody
	public Result list(@RequestParam String user_id) {
		
		logger.debug("REQ] /diary/list user_id"+ user_id);
		
		Result res = diaryService.listDiary(user_id);
		
		logger.info("WAS] /diary/list result="+ res.result + " error="+ res.error +" user_id="+ user_id + " diary_id=null");

		return res;
	}

}
