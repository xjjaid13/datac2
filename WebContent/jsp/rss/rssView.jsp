<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
	.topic-avatar {
	    float: left;
	    height: 50px;
	    margin-top: 4px;
	    position: relative;
	    width: 50px;
	}
	
	.topic-item-content {
	    margin: -3px 0 0 64px;
	}
	.topic-item-title {
	    margin-bottom: 6px;
	    word-break: break-all;
	}
	.topic-feed-item {
	    margin-bottom: 6px;
	}
	a {
	    color: #259;
	    text-decoration: none;
	}
	.zg-clear:before, .zg-clear:after {
	    content: " ";
	    display: block;
	    height: 0;
	    visibility: hidden;
	}
	.topic-item-title-link {
	    color: #222;
	}
	.zg-gray {
	    color: #999;
	    font-size: 12px;
	    font-weight: 400;
	}
	.cancelSubscribe{
		color : grey;
		font-size : 12px;
	}
</style>
<div id="rssDetailContent" style="margin:10px;">
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
</div>