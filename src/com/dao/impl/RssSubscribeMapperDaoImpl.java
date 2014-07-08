package com.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dao.RssSubscribeMapperDao;
import com.entity.Rss;
import com.entity.RssSubscribe;

@Repository
public class RssSubscribeMapperDaoImpl extends BaseDaoImpl<RssSubscribe> implements RssSubscribeMapperDao {

	@Override
	public List<Rss> selectTypeSubscribe(RssSubscribe rssSubscribe) {
		return sqlSessionTemplate.selectList("com.dao."+rssSubscribe.toString()+"MapperDao.selectTypeSubscribe", rssSubscribe);
	}

}
