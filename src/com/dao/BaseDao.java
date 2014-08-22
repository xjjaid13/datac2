package com.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

public interface BaseDao<T> {

	int insert(T t) throws DataAccessException;
	
	int update(T t) throws DataAccessException;
	
	int delete(T t) throws DataAccessException;
	
	T select(T t) throws DataAccessException;
	
	int selectCount(T t) throws DataAccessException;
	
	List<T> selectList(T t) throws DataAccessException;
	
	int maxId(T t) throws DataAccessException;
	
	int deleteByIds(T t) throws DataAccessException;
	
	List<T> selectList(String sql,Object param) throws DataAccessException;
	
}
