package com.redis.user.service;

import java.util.List;

import com.redis.base.service.BaseRedisService;
import com.redis.user.bean.User;

public interface IUserService extends BaseRedisService {

	public List<User> queryUser();

}