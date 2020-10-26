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

import ac.hongik.tripdiary.data.LogMessage;
import ac.hongik.tripdiary.data.Result;
import ac.hongik.tripdiary.data.User;
import ac.hongik.tripdiary.service.UserService;

@Controller
@RequestMapping(value="/user")	
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)	
	@ResponseBody
	public Result login(@RequestBody User user) { 

		logger.debug("REQ] /user/login user_id=" + user.user_id);	
		
		Result res = userService.getUser(user);

		LogMessage msg = LogMessage.getLogMessage("/user/login", res, user, null);
		logger.info(msg.toString());
		return res;
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)	
	@ResponseBody
	public Result signup(@RequestBody User user) {
		
		logger.debug("REQ] /user/signup user_id=" + user.user_id);	
		
		Result res = userService.addUser(user);
		
		LogMessage msg = LogMessage.getLogMessage("/user/signup", res, user, null);
		logger.info(msg.toString());
		
		return res;
	}
	
	@RequestMapping(value="/check_id", method=RequestMethod.GET)	
	@ResponseBody
	public Result check_id(@RequestParam String user_id) {
        logger.debug(">>>> user_id[" + user_id +"]");
		Result res = userService.checkUser(user_id);
		
		return res;
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.POST)	
	@ResponseBody
	public Result setProfile(@RequestBody User user) {
		
		logger.debug("REQ] /user/profile user_id=" + user.user_id);
		
		Result res = userService.updateProfile(user);
		
		LogMessage msg = LogMessage.getLogMessage("/user/profile", res, user, null);
		logger.info(msg.toString());
	
		return res;
	}
}
