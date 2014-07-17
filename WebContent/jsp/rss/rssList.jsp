<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" role="form">
  <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">url</label>
    <div class="col-sm-7">
      <input type="email" class="form-control" id="rssUrl" placeholder="url">
    </div>
    <div class="col-sm-3">
      <button type="button" id="book" class="btn btn-default">book</button>
    </div>
  </div>
</form>
<div id="rssContentDiv">
	<c:forEach items="${rssList}" var="rss" >
	  <div class="col-sm-6 col-md-3">
		<div class="thumbnail" attr="${rss.rssId}">
		  <div class="caption">
			<h3 class="rss-title"><a class="pointer rssDetail">${rss.rssTitle}</a></h3>
			<p><a class="btn btn-primary cancelBook cursor" role="button">cancelBook</a></p>
		  </div>
		</div>
	  </div>
	</c:forEach>
</div>
