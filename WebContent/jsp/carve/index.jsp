<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rss</title>
<link rel="shortcut icon" type="image/x-icon" href="${base}/static/image/favicon.ico" media="screen" />
<%@include file="../css-file.jsp" %>
<link rel="stylesheet" href="${base}/static/js/jstree/dist/themes/default/style.min.css" />
<link rel="stylesheet" href="${base}/static/css/rss.css" />
<link href="${base}/static/js/datatables/css/datatables_bootstrap.css" rel="stylesheet" type="text/css"/>

<%@include file="../js-file.jsp" %>
<script src="${base}/static/js/jstree/dist/jstree.min.js"></script>
<script src="${base}/static/js/datatables/js/jquery.dataTables.min.js"></script>
<script src="${base}/static/js/datatables/js/jquery.dataTables.rowGrouping.js"></script>
<script src="${base}/static/js/datatables/js/ColReorderWithResize.js"></script>
<script src="${base}/static/js/datatables/extras/ColVis/js/ColVis.min.js"></script>
<script src="${base}/static/js/datatables/js/jquery.dataTables.bootstrap.js"></script>
<script src="${base}/static/js/jqueryUi/jquery.ui.dialog.js"></script>
<script>
	var jsTree;
	$(function(){
		$('.btn-default').popover({
		    'trigger' : 'click'
		});
		$('#js-tree').jstree();
		$('#js-tree').on('changed.jstree', function (e, data) {
			alert('1');
		});
		$('#example').dataTable({
		    "sDom": "<'row dt_foot'<'col-md-4'i><'col-md-3'><'col-md-5'p>>rt",
			"bServerSide": true,
            "sAjaxSource": "${base}/carve/test",
            "ordering": false,
            "aoColumnDefs": [ 
                    { "sClass": "center", "aTargets": [0,1] }
            ], 
            "fnDrawCallback": function ( oSettings ) {
                    var that = this;
                    this.$('td:first-child').each( function (i) {
                        that.fnUpdate(oSettings._iDisplayStart + i+1, this.parentNode, 0, false, false );
                    });
                },
            "aoColumns" : [ {
                "sTitle" : "<s:text name='common.table.NO'/>",
                "sName" : "rssId",
                "mData" : "rssId",
                "sClass":"table-number center"
            }, {
                "sTitle" : "<input type='checkbox'/>",
                "mData" : function(data,type,row){
                	return "<input type='checkbox' name='ids' value='"+data.rssId+"'/>";
                },
                "sClass":"table-number center"
            },{
                "sTitle" : "名称",
                "sName" : "rssTitle",
                "mData" : function(data,type,row){
                    return data.rssTitle;
                }
            }, {
                "sTitle" : "url",
                "sName" : "rssUrl",
                "sClass":"w50",
                "mData" : function(data,type,row){
                    return data.rssUrl;
                }
            }, {
                "sTitle" : "操作",
                "mData" : function(data,type,row){
                	return "<a href='javascript:void()' class='blue'><i class='icon-zoom-in bigger-130'></i></a>";
                }
            }]
		});
		$("#testDiv").dialog({
            title:'新增',
            autoOpen: true,
            width: 628,
            height : 500,
            buttons: {
                "保存": function () {
                    alert('aaa');
                },
                "取消": function () {
                    $(this).dialog("destroy");
                }
            }
        });
	});
	function closeDialog(){
		$(".btn-default").popover('hide');
	}
	function tipMessage(message){
		$.stickyInfo(message);
	}
	function ajaxHandle(ajaxData,callback){
		if(ajaxData.result == 'success'){
			callback();
		}else{
			tipMessage(ajaxData.message);
		}
	}
</script>
</head>
<body>
<div id="testDiv">
	aaaaa
</div>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">angrycat</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Rss</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.username} <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="${base}/login/out">quit</a></li>
          </ul>
        </li>
      </ul>
      <form class="navbar-form navbar-right" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div style="margin-left:10px;;margin-right:auto;margin-top:60px;">
	<div class="panel panel-default " style="width:200px;position:fixed;z-index:0;">
	    <div class="panel-heading">title</div>
		
		<div class="well" style="border-radius:0;margin-bottom:0;">
			<div id="js-tree">
			  <ul>
			    <li>区域抓取规则</li>
			  </ul>
			</div>
	    </div>
	</div>

	<div class="content-wrapper col-lg-12" style="padding-left:210px;min-width:500px;z-index:-1;">
		<div class="panel panel-default">
			<div class="panel-heading">
			    <button class="btn btn-default">返回</button>
			</div>
			<div class="panel-body">
				<div class="main-content">
					
					<div class="row rssList">
						<div id="rssDetailContent" style="margin:10px;">
							<table id="example" class="table table-striped table-bordered" width="100%" cellspacing="0"></table>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>