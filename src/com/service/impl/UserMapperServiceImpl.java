package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.impl.BaseServiceImpl;
import com.dao.UserMapperDao;
import com.entity.User;
import com.service.UserMapperService;

@Service("userMapperService")
public class UserMapperServiceImpl extends BaseServiceImpl<User> implements UserMapperService{

	@Autowired
	UserMapperDao userMapperDao;

}
