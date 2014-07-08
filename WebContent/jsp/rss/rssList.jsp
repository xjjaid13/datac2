<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<c:forEach items="${rssList}" var="rss" >
	  <div class="col-sm-6 col-md-4">
		<div class="thumbnail">
		  <img data-src="holder.js/300x300" alt="...">
		  <div class="caption">
			<h3 class="rss-title">${rss.rssTitle}</h3>
			<p>...</p>
			<p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
		  </div>
		</div>
	  </div>
	</c:forEach>
