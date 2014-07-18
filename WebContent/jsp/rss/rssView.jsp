<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="rssDetailContent" style="margin:10px;">
	<c:forEach items="${rssCrawlList}" var="rssCrawl" >
		<div class="media">
		  <div class="media-body">
		    <h4 class="media-heading"> <a target="_blank" href="${rssCrawl.resourceUrl}">${rssCrawl.resourceTitle}</a></h4>
		    ${rssCrawl.resourceDesc} <br/>${rssCrawl.updateTime}
		  </div>
		</div>
	</c:forEach>
</div>