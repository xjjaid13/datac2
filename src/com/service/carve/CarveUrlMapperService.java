package com.service.carve;

import java.util.List;

import com.po.carve.CarveUrl;

public interface CarveUrlMapperService{
	
	public void insertNew(int carveTypeId);
	
	public List<CarveUrl> selectList(CarveUrl carveUrl);
	
	public Integer selectCount(CarveUrl carveUrl);
	
}
