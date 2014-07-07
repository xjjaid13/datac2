package com.service;

import com.entity.RssType;

public interface RssTypeMapperService extends BaseService<RssType>{
	
	public boolean isChildren(int parentId,int userId);
	
}
