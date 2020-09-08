package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Reference
	private UserService userService;


	@RequestMapping("/getUsername")
	public Result getUsername(HttpSession session){
		//获取session所有的参数名【 枚举类型】 == SPRING_SECURITY_CONTEXT
//		Enumeration attributeNames = session.getAttributeNames();
		//SecurityContext(安全框架中的全局信息，包括用户认证信息)   ServletContext  ApplicationContext
		SecurityContext securityContext = SecurityContextHolder.getContext();
		log.debug("securityContext:"+securityContext);
		//获取认证对象
        Authentication authentication = securityContext.getAuthentication();
        //获取重要信息(认证信息)
        //principal 如果没有登录，或者没有携带认证信息，该对象默认为字符串类型
        //principal, 如果登录成功，  对应的是 User类型
        Object principal = authentication.getPrincipal();
        String username  = "";
        // 判断 principal 是否是User类型
        if(principal instanceof User){
            User user = (User) principal;
            username = user.getUsername();
        }
        return new Result(true, MessageConst.GET_USERNAME_SUCCESS, username) ;
	}


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
		modelAndView.setViewName("redirect:http://localhost:83/login.html?message=loginFail");
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