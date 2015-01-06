package com.service.carve.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.carve.CarveTypeMapperDao;
import com.dao.carve.CarveUrlMapperDao;
import com.service.carve.CarveUrlMapperService;

@Service()
public class CarveUrlMapperServiceImpl implements CarveUrlMapperService{

	@Autowired
	CarveUrlMapperDao carveUrlMapperDao;
	
	@Autowired
	CarveTypeMapperDao carveTypeMapperDao;
	
	@Override
	public void insertNew(int carveTypeId) {
		
	}
	
}
