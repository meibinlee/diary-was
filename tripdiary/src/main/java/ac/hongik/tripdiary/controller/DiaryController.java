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

		Result res = diaryService.createDiary(diary);
		
		return res;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)	
	@ResponseBody
	public Result list(@RequestParam String user_id) {
        logger.info(">>>> user_id[" + user_id +"]");
		Result res = diaryService.listDiary(user_id);
		
		return res;
	}

}
