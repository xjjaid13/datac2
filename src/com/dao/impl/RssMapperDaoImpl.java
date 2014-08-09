package com.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dao.RssMapperDao;
import com.po.Rss;
import com.po.RssType;

@Repository
public class RssMapperDaoImpl extends BaseDaoImpl<Rss> implements RssMapperDao {
	
	public Rss selectRssTopCrawl(Rss rss){
		return sqlSessionTemplate.selectOne("com.dao."+rss.toString()+"MapperDao.selectRssTopCrawl", rss);
	}

	@Override
	public List<Rss> selectRssByUser(RssType rssType) {
		return sqlSessionTemplate.selectList("com.dao.RssMapperDao.selectRssByUser", rssType);
	}
	
}
