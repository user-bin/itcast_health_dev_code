package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.UserDao;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Override
	public boolean login(String username, String password) {
		log.debug("service_provide...u:"+username+" p:"+password);
		if("admin".equals(username) && "123".equals(password)){
			return true;
		}else{
			throw new BusinessRuntimeException("登录失败!!!");
		}
	}

	@Autowired
	UserDao userDao;
	@Override
	public SysUser findByUsername(String username) {
		return userDao.findByUsername(username);
	}
}