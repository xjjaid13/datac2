<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="../css-file.jsp" %>
<link rel="stylesheet" href="${base}/static/js/jstree/dist/themes/default/style.min.css" />
<%@include file="../js-file.jsp" %>
<script src="${base}/static/js/jstree/dist/jstree.min.js"></script>
<script>
	$(function(){
		$('.btn-default').popover({
		    'trigger' : 'click'
		});
		$('#js-tree').jstree({ 'core' : 
			{
				"check_callback" : true,
				'data' :  {
				    'url' :  function (node) {
				    	return "${base}/rss/myReturnRssTypeTree";
				    },
				    'data' : function (node) {
				        return { 'id' : node.id };
			        }
			    }
			    
			}
		});
		$('#js-tree').on("select_node.jstree", function (e, data) {
			if(data.selected[0] == 0){
				$(".rssList").load("${base}/rss/myRssView");
			}else{
				$(".rssList").load("${base}/rss/myRssList?rssTypeId="+data.selected[0]);
			}
		});
		$(document).on("click","#book",function(){
			var rssUrl = $("#rssUrl").val();
			var jsTree = $('#js-tree').jstree(true),
			sel = jsTree.get_selected();
			if(!sel.length) { return false; }
			$.ajax({
				url : '${base}/rss/myAddRssAdnSubscribe',
				data : {'rssUrl':encodeURIComponent(rssUrl),"parentId" : sel[0]},
				type : 'post',
				dataType : 'json',
				success : function(ajaxData){
					if(ajaxData.result == 'success'){
						var rss = ajaxData.rss;
						var rssTitle = rss.rssTitle;
						if(rssTitle.length > 6){
							rssTitle = rssTitle.substring(0,6) + "...";
						}
						var rssListTemplateDiv = $("#rssListTemplateDiv").html();
						rssListTemplateDiv = rssListTemplateDiv.replace("#rssTitleTip#",rss.rssTitle);
						rssListTemplateDiv = rssListTemplateDiv.replace("#rssId#",rss.rssId);
						rssListTemplateDiv = rssListTemplateDiv.replace("#rssTitle#",rssTitle);
						$("#rssContentDiv").before(rssListTemplateDiv);
					}
				}
			});
		}).on("click",".rssDetail",function(){
			var $thumbnail = $(this).closest(".thumbnail")
			var rssId = $thumbnail.attr("attr");
			$(".rssList").load("${base}/rss/myRssDetail?rssId="+rssId);
		}).on("click",".cancelBook",function(){
			var $thumbnail = $(this).closest(".thumbnail")
			var rssId = $thumbnail.attr("attr");
			$.ajax({
				url : '${base}/rss/myCancelSubscribe',
				data : 'rssId='+rssId,
				dataType : 'json',
				success : function(ajaxData){
					if(ajaxData.result == 'success'){
						$thumbnail.parent().fadeOut();
					}
				}
			});
		});
	});
	function addRssType(){
		var jsTree = $('#js-tree').jstree(true),
		sel = jsTree.get_selected();
		if(!sel.length) { return false; }
		jsTree.open_node(sel,function(){
			var typeName = $("#typeName").val();
			$.ajax({
				url : '${base}/rss/myAddRssType',
				data : 'parentId='+sel[0]+"&typeName="+typeName,
				dataType : 'json',
				success : function(ajaxData){
					jsTree.create_node(sel, {"type":"file","id":ajaxData.rssTypeId,"text":typeName});
					jsTree.open_node(sel);
					$(".btn-default").popover('hide');
				}
			});
		});
	}
	function renameRssType(){
		var jsTree = $('#js-tree').jstree(true),
		sel = jsTree.get_selected();
		if(!sel.length) { return false; }
		var typeName = $("#typeName").val();
		$.ajax({
			url : '${base}/rss/myUpdateRssType',
			data : 'rssTypeId='+sel[0]+"&typeName="+typeName,
			dataType : 'json',
			success : function(ajaxData){
				jsTree.rename_node(sel, typeName);
				$(".btn-default").popover('hide');
			}
		});
	}
	function deleteType(){
		var jsTree = $('#js-tree').jstree(true),
		sel = jsTree.get_selected();
		if(!sel.length) { return false; }
		if(jsTree.is_leaf(sel)){
			$.ajax({
				url : '${base}/rss/myDleteRssType',
				data : 'rssTypeId='+sel[0],
				dataType : 'json',
				success : function(ajaxData){
					jsTree.delete_node(sel);
				}
			});
		}
	}
</script>
</head>
<body>
<div class="hide" id="rssListTemplateDiv">
	<div class="col-sm-6 col-md-3">
		<div class="thumbnail" attr="#rssId#">
		  <div class="caption">
			<h3 class="rss-title"><a class="pointer rssDetail" title="#rssTitleTip#">#rssTitle#</a></h3>
			<p><a href="#" class="btn btn-primary" role="button">cancelBook</a> </p>
		  </div>
		</div>
	</div>
</div>

<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Brand</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Rss</a></li>
      </ul>
      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div style="margin-left:auto;margin-right:auto;width:80%">
	<div class="panel panel-default " style="width:200px;position:fixed;z-index:0;">
	  <div class="panel-heading">title</div>
	  <div class="panel-body">

		 <div class="btn-group">
			  <button type="button" class="btn btn-default" data-placement="right" data-html="true" title="新增类型" data-content='<form role="form" class="form-inline" style="width:230px;">
			        <div class="form-group" >
			        <label for="exampleInputEmail2" class="sr-only">类型</label>
			        <input type="text" placeholder="类型" id="typeName" class="form-control">
			        </div>
			        <button class="btn btn-default" onClick="addRssType();" type="button">新增</button><button class="btn btn-default" onClick="closeDialog();" type="button">关闭</button>
			        </form>' title="" data-toggle="popover" class="btn btn-large btn-danger" href="#" data-original-title="标题" >新增
			  </button>
			  <button type="button" class="btn btn-default" data-placement="right" data-html="true" title="改名" data-content='<form role="form" class="form-inline" style="width:230px;">
			        <div class="form-group" >
			        <label for="exampleInputEmail2" class="sr-only">类型</label>
			        <input type="text" placeholder="类型" id="typeName" class="form-control">
			        </div>
			        <button class="btn btn-default" onClick="renameRssType();" type="button">修改</button>
			        </form>' title="" data-toggle="popover" class="btn btn-large btn-danger" href="#" data-original-title="标题" id="renameTypeNameBtn">修改
			  </button>
			  <button type="button" class="btn btn-default" onclick="deleteType();" id="deleteTypeNameBtn">删除</button>
			</div>
	  </div>
		
		<div class="well">
			<div id="js-tree">
				
			</div>
	    </div>
	</div>


	<div class="content-wrapper col-lg-12" style="padding-left:210px;min-width:500px;z-index:-1;">
		<div class="panel panel-default">
			<div class="panel-heading">Panel heading without title</div>
			<div class="panel-body">
				<div class="main-content">
					
					<div class="row rssList">
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>