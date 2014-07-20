package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.impl.BaseServiceImpl;
import com.dao.BlogTypeMapperDao;
import com.po.BlogType;
import com.service.BlogTypeMapperService;

@Service("blogTypeMapperService")
public class BlogTypeMapperServiceImpl extends BaseServiceImpl<BlogType> implements BlogTypeMapperService{

	@Autowired
	BlogTypeMapperDao blogTypeMapperDao;

}
