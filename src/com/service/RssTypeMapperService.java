package com.service;

import java.util.List;
import com.po.RssType;

public interface RssTypeMapperService{
	
	public boolean isChildren(int parentId,int userId);
	
	public List<RssType> selectList(RssType rssType);
	
	public RssType select(RssType rssType);
	
	public void update(RssType rssType);
	
	public void delete(RssType rssType);
	
	public void insert(RssType rssType);
	
}
