package com.redis.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.base.service.AbstractBaseRedisService;
import com.redis.user.bean.User;
import com.redis.user.dao.UserDao;
import com.redis.user.service.IUserService;

@Service
public class UserServiceImpl extends AbstractBaseRedisService<String, User>
		implements IUserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> queryUser() {
		return userDao.getUser();
	}



}