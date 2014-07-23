<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:forEach items="${rssList}" var="rss">
	<div class="js-topic-item topic-item zg-clear">
		<div class="topic-item-content">
			<div>
				<h3 class="topic-item-title">
					<a class="topic-item-title-link">${rss.rssTitle}</a>
					<a attr="${rss.rssId}" class="cancelSubscribe pointer">取消rss</a>
				<!-- <a style="" class="js-pin zg-gray pin topic-hover-content" data-action="pin-topic" href="javascript:;"><i class="z-icon-pin"></i>固定话题</a>
				
				<a class="zg-right zg-gray unfollow topic-hover-content" data-action="unfollow" id="ft-19800" href="javascript:;">取消关注</a>
				 -->
				</h3>
			</div>
			<div class="topic-item-feed-digest">
				<c:forEach items="${rss.rssCrawlList}" var="rssCrawl">
					<div class="topic-feed-item">
						<a target="_blank" href="${rssCrawl.resourceUrl}">${rssCrawl.resourceTitle}</a>
						<span class="zg-gray time"><c:out value="${fn:substring(rssCrawl.updateTime, 0, 19)}" /></span>
					</div>
				</c:forEach>
				
				<a class="more-link pointer" attr1="${rss.rssId}" attr2="1" >more&nbsp;»</a>
			
			</div>
		</div>
	</div>
</c:forEach>
