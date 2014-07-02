package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.impl.BaseServiceImpl;
import com.dao.MenuMapperDao;
import com.entity.Menu;
import com.service.MenuMapperService;

@Service("menuMapperService")
public class MenuMapperServiceImpl extends BaseServiceImpl<Menu> implements MenuMapperService{

	@Autowired
	MenuMapperDao menuMapperDao;

}
