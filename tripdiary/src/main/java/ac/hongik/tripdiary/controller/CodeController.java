package ac.hongik.tripdiary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.hongik.tripdiary.data.Result;
import ac.hongik.tripdiary.service.CodeService;

@Controller
@RequestMapping(value="/code")	
public class CodeController {
	private Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	@Autowired
	CodeService codeService;
	
	@RequestMapping(value="/country", method=RequestMethod.GET)	
	@ResponseBody
	public Result country() {
		Result res = codeService.getCountry();
		return res;
	}
	
	@RequestMapping(value="/city", method=RequestMethod.GET)	
	@ResponseBody
	public Result city(@RequestParam String country) {
        logger.info(">>>> country[" + country +"]");
		Result res = codeService.getCity(country);
		
		return res;
	}
}
