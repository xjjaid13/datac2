package com.task.carve;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.po.carve.CarveType;
import com.service.carve.CarveTypeMapperService;
import com.service.carve.CarveUrlMapperService;
import com.util.DataHandle;

@Component
public class CarveTask {

	@Autowired
	CarveTypeMapperService carveTypeMapperService;
	
	@Autowired
	CarveUrlMapperService carveUrlMapperService;
	
	@Scheduled(fixedDelay = 100000)  
	public void run(){
		CarveType sratchCarveType = new CarveType();
		sratchCarveType.setStartPage(-1);
		List<CarveType> carveTypeList = carveTypeMapperService.selectList(sratchCarveType);
		if(DataHandle.isNotNullOrEmpty(carveTypeList)){
			for(CarveType carveType : carveTypeList){
				carveUrlMapperService.insertNew(carveType.getCarveTypeId());
			}
		}
	}
	
}
