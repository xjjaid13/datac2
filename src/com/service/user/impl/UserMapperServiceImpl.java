package com.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.user.UserMapperDao;
import com.exception.common.ServiceException;
import com.po.user.User;
import com.service.user.UserMapperService;
import com.util.Md5Util;

@Transactional
@Service("userMapperService")
public class UserMapperServiceImpl implements UserMapperService{

	@Autowired
	UserMapperDao userMapperDao;

	@Override
	public User validUser(User user) {
		try {
			String password = user.getPassword();
			password = Md5Util.getMD5(password.getBytes());
			user.setPassword(password);
			user = userMapperDao.select(user);
			return user;
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

}
