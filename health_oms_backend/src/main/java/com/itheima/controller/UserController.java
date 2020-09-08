package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Reference
	private UserService userService;


	@RequestMapping("/loginSuccess")
	public ModelAndView loginSuccess(){
		ModelAndView modelAndView = new ModelAndView();
//		forward: 请求转发
//		redirect: 重定向
		modelAndView.setViewName("redirect:http://localhost:83/pages/main.html");
		return modelAndView;
	}


	@RequestMapping("/loginFail")
	public ModelAndView loginFail(){
		ModelAndView modelAndView = new ModelAndView();
//		forward: 请求转发
//		redirect: 重定向
		modelAndView.setViewName("redirect:http://localhost:83/login.html");
		return modelAndView;
	}


//	@RequestMapping("/login")
//	public Result login(String username, String password){
//		log.debug("oms backend,user:"+username+" ,password:"+password);
//		if(userService.login(username,password)){
//			log.debug("login ok!!!");
//			return new Result(true, MessageConst.ACTION_SUCCESS);
//		}else{
//			log.debug("login fail");
//			return new Result(false,MessageConst.ACTION_FAIL);
//		}
//	}
}