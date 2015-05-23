package com.task.carve;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	@Scheduled(fixedDelay = 300000)  
	public void run(){
		CarveType sratchCarveType = new CarveType();
		sratchCarveType.setStartPage(-1);
		List<CarveType> carveTypeList = carveTypeMapperService.selectList(sratchCarveType);
		if(DataHandle.isNotNullOrEmpty(carveTypeList)){
			for(CarveType carveType : carveTypeList){
				if(carveType.getEnable() == 1){
					carveUrlMapperService.insertNew(carveType.getCarveTypeId());
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String content = "<a href=\"/p/220595.html\" data-no-turbolink=\"true\" onclick=\"javascript:_gaq.push(['_trackPageview', '/click/index_newtab/']);\" target=\"_blank\" title=\"欺骗的艺术：苹果如何保密及其汽车项目的蛛丝马迹\">欺骗的艺术：苹果如何保密及其汽车项目的蛛丝马迹</a>";
		Pattern pattern = Pattern.compile("<a(.*?)href=\"(.*?)\"(.*?)onclick(.*?)\">(.*?)</a>");
		Matcher matcher = pattern.matcher(content);
		while(matcher.find()){
			System.out.println(matcher.group(5));
		}
	}
	
}
