package com.service.carve.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
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
import com.exception.common.ServiceException;
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
			log.info("进行统计,carveTypeId=" + carveTypeId);
			CarveType selectCarveType = new CarveType();
			selectCarveType.setCarveTypeId(carveTypeId);
			selectCarveType = carveTypeMapperDao.select(selectCarveType);

			String httpContent = HttpClientHandle.returnHtmlContent(selectCarveType.getUrl());
			if(StringUtils.isEmpty(selectCarveType.getSelector())){
				Pattern pattern = Pattern.compile(selectCarveType.getPattern());
				Matcher matcher = pattern.matcher(httpContent);
				//to do
			}else{
				Document document = Jsoup.parse(httpContent);
				Elements elements = document.select(selectCarveType.getSelector());
				if(DataHandle.isNullOrEmpty(elements)){
					throw new ServiceException("jsoup无法获得内容,carveTypeId:" + carveTypeId);
				}
				
				Element firstElement = null;
				
				if(selectCarveType.getSeqNum() == -1){
					firstElement = elements.get(0);
				}else{
					firstElement = document.select(selectCarveType.getSelector()).get(selectCarveType.getSeqNum());
				}
				
				String hash = Md5Util.getMD5(firstElement.text().getBytes());
				if(hash.equals(selectCarveType.getHash())){
					return;
				}else{
					CarveUrl searchCarveUrl = new CarveUrl();
					searchCarveUrl.setStartPage(0);
					searchCarveUrl.setPage(1);
					searchCarveUrl.setCarveTypeId(carveTypeId);
					searchCarveUrl.setCondition("order by carveUrlId desc");
					List<CarveUrl> carveUrlList = carveUrlMapperDao.selectList(searchCarveUrl);
					
					if(DataHandle.isNotNullOrEmpty(carveUrlList)){
						searchCarveUrl = carveUrlList.get(0);
					}
					
					String elementHtml = firstElement.html();
					
					if(!StringUtils.isEmpty(selectCarveType.getPattern())){
						String patternGroup = selectCarveType.getPatternGroup();
						String[] group = patternGroup.split(";");
						List<CarveUrl> carveUrlListReverse = new ArrayList<CarveUrl>();
						if(selectCarveType.getSeqNum() == -1){
							for(Element element : elements){
								Pattern pattern = Pattern.compile(selectCarveType.getPattern());
								Matcher matcher = pattern.matcher(element.html());
								
								if(matcher.find()){
									String url = HtmlHandle.joinUrl(selectCarveType.getUrl(), matcher.group(Integer.parseInt(group[0])));
									String title = matcher.group(Integer.parseInt(group[1]));
									if(title.equals(searchCarveUrl.getTitle())){
										break;
									}
									CarveUrl carveUrl = new CarveUrl();
									carveUrl.setCarveTypeId(carveTypeId);
									carveUrl.setCreateTime(TimeHandle.currentTime());
									carveUrl.setTitle(title);
									carveUrl.setUrl(url);
									carveUrlListReverse.add(carveUrl);
								}
							}
						}else{
							Pattern pattern = Pattern.compile(selectCarveType.getPattern());
							Matcher matcher = pattern.matcher(elementHtml);
							if(!matcher.find()){
								throw new ServiceException("正则无法取得结果,carveTypeId:" + carveTypeId);
							}
							matcher = matcher.reset();
							while(matcher.find()){
								String url = HtmlHandle.joinUrl(selectCarveType.getUrl(), matcher.group(Integer.parseInt(group[0])));
								String title = matcher.group(Integer.parseInt(group[1]));
								
								if(title.equals(searchCarveUrl.getTitle())){
									break;
								}
								CarveUrl carveUrl = new CarveUrl();
								carveUrl.setCarveTypeId(carveTypeId);
								carveUrl.setCreateTime(TimeHandle.currentTime());
								carveUrl.setTitle(title);
								carveUrl.setUrl(url);
								carveUrlListReverse.add(carveUrl);
								
							}
						}
						if(DataHandle.isNotNullOrEmpty(carveUrlListReverse)){
							for(int i = carveUrlListReverse.size() - 1; i > -1; i--){
								carveUrlMapperDao.insert(carveUrlListReverse.get(i));
							}
						}
					}else{
						Elements childrenElement = firstElement.children();
						if(DataHandle.isNotNullOrEmpty(childrenElement)){
							for(int i = childrenElement.size() - 1; i > -1; i--){
								Element childElement = childrenElement.get(i);
								Elements links = childElement.select("a[href]");
								if(DataHandle.isNotNullOrEmpty(links)){
									Element link = links.get(0);
									if(DataHandle.isNotNullOrEmpty(selectCarveType.getHash()) && link.text().equals(searchCarveUrl.getTitle())){
										break;
									}
									CarveUrl carveUrl = new CarveUrl();
									carveUrl.setCarveTypeId(carveTypeId);
									carveUrl.setCreateTime(TimeHandle.currentTime());
									carveUrl.setTitle(link.text());
									carveUrl.setUrl(HtmlHandle.joinUrl(searchCarveUrl.getUrl(), link.attr("href")));
									carveUrlMapperDao.insert(carveUrl);
									
								}
							}
						}
					}
					
					selectCarveType.setHash(hash);
					carveTypeMapperDao.update(selectCarveType);
				}
			}
		}catch(Exception e){
			log.error("carve exception",e);
		}
	}
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("<a(.*?)href=\"(.*?)\"(.*?)>(.*?)</a>");
		Matcher matcher = pattern.matcher("<h1> <a href=\"http://36kr.com/p/532003.html\" target=\"_blank\" class=\"info_flow_news_title\">在这个标题党横行的时代，Medium如何保持优质的内容和纯净的环境</a> </h1>");
		
		if(matcher.find()){
			System.out.println(matcher.group(2));
			System.out.println(matcher.group(4));
		}
	}

	@Override
	public List<CarveUrl> selectList(CarveUrl carveUrl) {
		return carveUrlMapperDao.selectList(carveUrl);
	}

	@Override
	public Integer selectCount(CarveUrl carveUrl) {
		return carveUrlMapperDao.selectCount(carveUrl);
	}
	
}
