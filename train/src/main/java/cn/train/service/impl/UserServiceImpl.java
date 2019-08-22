package cn.train.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.train.util.StringUtils;
import org.springframework.stereotype.Service;

import cn.train.dao.UserDao;
import cn.train.entity.User;
import cn.train.service.UserService;


/*
 * @ Copyright (c) Create by JASON  Date:2018-04-14  All rights reserved.
 *
 * @ class description：用户信息service实现类，业务处理
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	

	@Override
	public User getLoginUser(User user) throws Exception {
		return userDao.getLoginUser(user);
	}

	@Override
	public int addUser(User user) throws Exception {
		return userDao.addUser(user);
	}

	@Override
	public List<User> getUserList() throws Exception{
		return userDao.getUserList();
	}

	@Override
	public User getUserById(int id) throws Exception{
		return userDao.getUserById(id);
	}

	@Override
	public String userAccept(Map param) throws Exception {
		String userName = StringUtils.getString(param.get("userName"));
		String passWord = StringUtils.getString(param.get("passWord"));
		String address = StringUtils.getString(param.get("address"));
		String phone = StringUtils.getString(param.get("phone"));
		String icNumber = StringUtils.getString(param.get("icNumber"));

		if(StringUtils.isNullOrEmpty(userName)){
			return "用户名为空";
		}
		if(StringUtils.isNullOrEmpty(passWord)){
			return "密码为空";
		}
		if(StringUtils.isNullOrEmpty(address)){
			return "地址为空";
		}

		User user = new User();
		user.setBirthday(StringUtils.getBirthDay(icNumber));
		user.setRealname(userName);
		user.setPassword(passWord);
		user.setTelphone(phone);
		int i = addUser(user);

		return "0000";
	}

}


