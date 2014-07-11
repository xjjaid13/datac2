<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="rssDetailContent" style="margin:10px;">
	<c:forEach items="${rssDetailList}" var="map" >
		<div class="media">
		  <div class="media-body">
		    <h4 class="media-heading">${map.itemNo}<a target="_blank" href="${map.link}">${map.title}</a></h4>
		    ${map.description}${map.pubDate}
		  </div>
		</div>
	</c:forEach>
</div>