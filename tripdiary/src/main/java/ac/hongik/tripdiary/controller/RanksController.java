package ac.hongik.tripdiary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.hongik.tripdiary.data.Result;
import ac.hongik.tripdiary.service.RanksService;

@Controller
@RequestMapping(value="/ranks")	
public class RanksController {
	private Logger logger = LoggerFactory.getLogger(RanksController.class);
	
	@Autowired
	RanksService ranksService;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)	
	@ResponseBody
	public Result all() {
		Result res = ranksService.getAll();
		return res;
	}
	
	@RequestMapping(value="/age", method=RequestMethod.GET)	
	@ResponseBody
	public Result age() {
		Result res = ranksService.getAge();
		return res;
	}
	
	@RequestMapping(value="/female", method=RequestMethod.GET)	
	@ResponseBody
	public Result female() {
		Result res = ranksService.getFemale();
		return res;
	}
	
	@RequestMapping(value="/male", method=RequestMethod.GET)	
	@ResponseBody
	public Result male() {
		Result res = ranksService.getMale();
		return res;
	}
}
