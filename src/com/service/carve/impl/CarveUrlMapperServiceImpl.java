package com.service.carve.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.carve.CarveTypeMapperDao;
import com.dao.carve.CarveUrlMapperDao;
import com.po.carve.CarveType;
import com.po.carve.CarveUrl;
import com.service.carve.CarveUrlMapperService;
import com.util.DataHandle;
import com.util.HtmlHandle;
import com.util.HttpClientHandle;
import com.util.Md5Util;
import com.util.TimeHandle;

@Transactional
@Service()
public class CarveUrlMapperServiceImpl implements CarveUrlMapperService{

	private static Logger log = Logger.getLogger(CarveUrlMapperServiceImpl.class);
	
	@Autowired
	CarveUrlMapperDao carveUrlMapperDao;
	
	@Autowired
	CarveTypeMapperDao carveTypeMapperDao;
	
	@Override
	public void insertNew(int carveTypeId) {
		try{
			CarveType selectCarveType = new CarveType();
			selectCarveType.setCarveTypeId(carveTypeId);
			selectCarveType = carveTypeMapperDao.select(selectCarveType);

			String httpContent = HttpClientHandle.returnHtmlContent(selectCarveType.getUrl());
			Document document = Jsoup.parse(httpContent);
			Element element = document.select(selectCarveType.getSelector()).get(selectCarveType.getSeqNum());
			
			String hash = Md5Util.getMD5(element.text().getBytes());
			if(hash.equals(selectCarveType.getHash())){
				return;
			}else{
				CarveUrl searchCarveUrl = new CarveUrl();
				searchCarveUrl.setStartPage(0);
				searchCarveUrl.setPage(1);
				searchCarveUrl.setCarveTypeId(carveTypeId);
				searchCarveUrl.setCondition("order by carveUrlId desc");
				List<CarveUrl> carveUrlList = carveUrlMapperDao.selectList(searchCarveUrl);
				
				boolean newFlag = false;
				
				if(DataHandle.isNotNullOrEmpty(carveUrlList)){
					searchCarveUrl = carveUrlList.get(0);
				}else{
					newFlag = true;
				}
				
				if(!newFlag && element.html().indexOf(searchCarveUrl.getTitle()) == -1){
					newFlag = true;
				}
				
				Elements childrenElement = element.children();
				if(DataHandle.isNotNullOrEmpty(childrenElement)){
					for(int i = childrenElement.size() - 1; i >= 0; i--){
						Element childElement = childrenElement.get(i);
						Elements links = childElement.select("a[href]");
						if(DataHandle.isNotNullOrEmpty(links)){
							Element link = links.get(0);
							if(newFlag){
								CarveUrl carveUrl = new CarveUrl();
								carveUrl.setCarveTypeId(carveTypeId);
								carveUrl.setCreateTime(TimeHandle.currentTime());
								carveUrl.setTitle(link.text());
								carveUrl.setUrl(HtmlHandle.joinUrl(searchCarveUrl.getUrl(), link.attr("href")));
								carveUrlMapperDao.insert(carveUrl);
							}
							if(!newFlag && DataHandle.isNotNullOrEmpty(selectCarveType.getHash()) && link.text().equals(searchCarveUrl.getTitle())){
								newFlag = true;
							}
						}
					}
				}
				selectCarveType.setHash(hash);
				carveTypeMapperDao.update(selectCarveType);
			}
		}catch(Exception e){
			log.error("carve exception",e);
		}
	}
}
