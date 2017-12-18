package com.redis.user.RedisTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.alibaba.fastjson.JSONObject;
import com.redis.user.bean.User;
import com.redis.user.service.IUserService;

@ContextConfiguration(locations = { "classpath*:spring-mvc.xml" })
public class RedisJunitTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private IUserService userService;

	/**
	 * 新增 <br>
	 * ------------------------------<br>
	 */
//	@Test
	public void testAddUser() {
		User user = new User();
		user.setId("user1");
		user.setName("java2000_wl");
		user.setPassword("123456");
		String value = JSONObject.toJSONString(user);
		System.out.println(value);
		boolean result = userService.add(user.getId(), value);
		Assert.assertTrue(result);
	}
	
	/**
	 * 修改 <br>
	 * ------------------------------<br>
	 */
//	@Test
	public void testUpdate() {
		User user = new User();
		user.setId("user1");
		user.setName("new_password");
		user.setPassword("654321");
		String value = JSONObject.toJSONString(user);
		System.out.println(value);
		boolean result = userService.update(user.getId(), value);
		Assert.assertTrue(result);
	}
	

	/**
	 * 获取 <br>
	 * ------------------------------<br>
	 */
//	@Test
	public void testGetUser() {
		String id = "user1";
		Map<String, String> value = userService.get(id);
		System.out.println(value.get(id));
		Assert.assertNotNull(value);
	}

	/**
	 * 通过key删除单个 <br>
	 * ------------------------------<br>
	 */
//	@Test
	public void testDelete() {
		String key = "user1";
		userService.delete(key);
	}
	
	
	/**
	 * 批量新增 普通方式 <br>
	 * ------------------------------<br>
	 */
//	@Test
	public void testAddUsers1() {
		List<User> list = new ArrayList<User>();
		for (int i = 10; i < 50000; i++) {
			User user = new User();
			user.setId("user" + i);
			user.setName("java2000_wl" + i);
			list.add(user);
		}
		long begin = System.currentTimeMillis();
		for (User user : list) {
			userService.add(user.getId(), JSONObject.toJSONString(user));
		}
		System.out.println(System.currentTimeMillis() - begin);
	}


	/**
	 * 批量删除 50000 <br>
	 * ------------------------------<br>
	 */
//	@Test
	public void testDeletes1() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 50000; i++) {
			list.add("user" + i);
		}
		long begin = System.currentTimeMillis();
		userService.delete(list);
		System.out.println(System.currentTimeMillis() - begin);
	}
	
	/**
	 * 批量新增 pipeline方式 <br>
	 * ------------------------------<br>
	 */
//	@Test
	public void testAddUsers2() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 1500000; i++) {
			User user = new User();
			user.setId("user" + i);
			user.setName("java2000_wl" + i);
			user.setPassword("password"+i);
			Map<String, String> map = new HashMap<String, String>();
			map.put(user.getId(), JSONObject.toJSONString(user));
			list.add(map);
		}
		long begin = System.currentTimeMillis();
		boolean result = userService.add(list);
		System.out.println(System.currentTimeMillis() - begin);
		Assert.assertTrue(result);
	}
	
	/**
	 * 批量删除 1500000 <br>
	 * ------------------------------<br>
	 */
//	@Test
	public void testDeletes2() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 1500000; i++) {
			list.add("user" + i);
		}
		long begin = System.currentTimeMillis();
		userService.delete(list);
		System.out.println(System.currentTimeMillis() - begin);
	}
	
	/**
	 * 
	 * @Description: 综合测试，先从数据库拿数据，然后保存进redis，并随机数据其中一个数据
	 */
	@Test
	public void test() {
		List<User> ul = userService.queryUser();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < ul.size(); i++) {
			User user = ul.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("user" + i, JSONObject.toJSONString(user));
			list.add(map);
		}
		userService.add(list);
		Random ra = new Random();
		int num = ra.nextInt(list.size()) - 1;
		Map<String, String> valueMap = userService.get("user"+num);
		JSONObject json = JSONObject.parseObject(valueMap.get("user"+num));
		System.out.println(json);
		System.out.println(json.get("name"));
	}
	


}