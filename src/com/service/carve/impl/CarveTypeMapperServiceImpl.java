package com.service.carve.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.carve.CarveTypeMapperDao;
import com.exception.common.ServiceException;
import com.po.carve.CarveType;
import com.service.carve.CarveTypeMapperService;
import com.util.CarveHandle;
import com.util.DataHandle;
import com.vo.SelectorVO;

@Service("carveTypeMapperService")
public class CarveTypeMapperServiceImpl implements CarveTypeMapperService{

	@Autowired
	CarveTypeMapperDao carveTypeMapperDao;
	
	@Autowired
	CarveHandle carveHandle;

	@Override
	public void insert(CarveType carveType) {
		try{
			SelectorVO selectorVO = carveHandle.returnTemplatePattern(carveType.getContent(), carveType.getUrl());
			carveType.setSelector(selectorVO.getSelectString());
			carveType.setSeqNum(selectorVO.getSeqNum());
			carveTypeMapperDao.insert(carveType);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<CarveType> selectList(CarveType carveType) {
		return carveTypeMapperDao.selectList(carveType);
	}

	@Override
	public Integer selectCount(CarveType carveType) {
		return carveTypeMapperDao.selectCount(carveType);
	}

	@Override
	public void deleteByIds(CarveType carveType) {
		carveTypeMapperDao.deleteByIds(carveType);
	}

	@Override
	public CarveType select(CarveType carveType) {
		return carveTypeMapperDao.select(carveType);
	}

	@Override
	public void update(CarveType carveType) {
		try{
			if(DataHandle.isNotNullOrEmpty(carveType.getContent())){
				SelectorVO selectorVO = carveHandle.returnTemplatePattern(carveType.getContent(), carveType.getUrl());
				carveType.setSelector(selectorVO.getSelectString());
				carveType.setSeqNum(selectorVO.getSeqNum());
			}
			carveTypeMapperDao.update(carveType);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

}
