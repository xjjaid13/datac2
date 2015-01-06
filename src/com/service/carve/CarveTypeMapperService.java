package com.service.carve;

import java.util.List;

import com.po.carve.CarveType;

public interface CarveTypeMapperService{
	
	public void insert(CarveType carveType);
	
	public List<CarveType> selectList(CarveType carveType);
	
	public Integer selectCount(CarveType carveType);
	
	public void deleteByIds(CarveType carveType);
	
	public CarveType select(CarveType carveType);
	
	public void update(CarveType carveType);
	
}
