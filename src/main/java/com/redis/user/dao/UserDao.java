package com.redis.user.dao;

import java.util.List;

import com.redis.user.bean.User;

public interface UserDao {

	public List<User> getUser();
}