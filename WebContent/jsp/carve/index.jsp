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
<script src="${base}/static/js/ajaxsubmit/jquery.form.js"></script>
<script src="${base}/static/js/config/bootstrap-dialog.min.js"></script>
<script>
	var jsTree;
	var datatables;
	$(function(){
		$('.btn-default').popover({
		    'trigger' : 'click'
		});
		$('#js-tree').jstree();
		$('#js-tree').on('changed.jstree', function (e, data) {
			alert('1');
		});
		$('#patternForm').bind('submit', function(e) {
			e.preventDefault();
			$(this).ajaxSubmit(function(data){
				ajaxHandle(data,function(){
					datatables.fnDraw();
				});
			});
		});
		$("#carveTypeTableForm").bind('submit',function(e){
			e.preventDefault();
			$(this).ajaxSubmit(function(data){
				ajaxHandle(data,function(){
					datatables.fnDraw();
				});
			});
		});
		$("#addPattern").click(function(){
			$("#patternForm")[0].reset();
			$("#patternDiv").dialog({
	            title:'新增',
	            autoOpen: true,
	            width: 628,
	            buttons: {
	                "保存": function () {
	                	$("#patternForm").submit();
	                	$(this).dialog("destroy");
	                    $(this).hide();
	                },
	                "取消": function () {
	                    $(this).dialog("destroy");
	                    $(this).hide();
	                }
	            }
	        });
		});
		
		$('#deletePattern').click(function(){
			BootstrapDialog.confirm('确认删除?', function(result){
	            if(result) {
	            	$("#carveTypeTableForm").submit();
	            }
	        });
		});
		datatables = $('#carveTypeTable').dataTable({
		    "sDom": "rt<'row dt_foot'<'col-md-4'i><'col-md-3'><'col-md-5'p>>",
			"bServerSide": true,
            "sAjaxSource": "${base}/carve/list",
            "aoColumnDefs": [ 
                    { "sClass": "center", "aTargets": [0,1] }
            ], 
            "fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
                $.ajax( {
                    "dataType": 'json',
                    "type": "POST",
                    "url": sSource,
                    "data": aoData,
                    "success": function(data){
                    	if(data.result == 'success'){
	                    	fnCallback(data);
                    	}else{
                    		tipMessage(data.message);
                    	}
                    }
                });
            },
            "fnDrawCallback": function ( oSettings ) {
                    var that = this;
                    this.$('td:first-child').each( function (i) {
                        that.fnUpdate(oSettings._iDisplayStart + i+1, this.parentNode, 0, false, false );
                    });
                    $(document).on("click",".checkAll",function(){
            			if($(this).is(":checked")){
            				$(this).closest("table").find("input:checkbox").attr("checked","checked");
            			}else{
            				$(this).closest("table").find("input:checkbox").removeAttr("checked");
            			}
            		});
                },
            "aoColumns" : [ {
                "sTitle" : "序号",
                "sName" : "carveTypeId",
                "mData" : "carveTypeId",
                "bSortable" : false
            }, {
                "sTitle" : "<input class='checkAll' type='checkbox'/>",
                "mData" : function(data,type,row){
                	return "<input type='checkbox' name='ids' value='"+data.carveTypeId+"'/>";
                },
                "sClass":"w5 center",
                "bSortable" : false
            },{
                "sTitle" : "名称",
                "sName" : "typeName",
                "mData" : function(data,type,row){
                    return data.typeName;
                }
            }, {
                "sTitle" : "url",
                "sName" : "url",
                "sClass":"w50",
                "mData" : function(data,type,row){
                    return data.url;
                }
            }, {
                "sTitle" : "pattern",
                "sName" : "pattern",
                "mData" : function(data,type,row){
                    return data.pattern;
                }
            }, {
                "sTitle" : "操作",
                "mData" : function(data,type,row){
                	return "<a href='javascript:updatePattern("+data.carveTypeId+")'><i class='fa fa-angellist'></i></a>";
                },
                "bSortable" : false
            }]
		});
	});
	function updatePattern(carveTypeId){
		$("#patternForm")[0].reset();
		$.ajax({
			url : '${base}/carve/returnCarveType',
			data : 'carveTypeId='+carveTypeId,
			success : function(data){
				$("#typeName").val(data.object.typeName);
				$("#url").val(data.object.url);
				$("#carveTypeId").val(data.object.carveTypeId);
				$("#patternDiv").dialog({
		            title:'修改',
		            autoOpen: true,
		            width: 628,
		            buttons: {
		                "保存": function () {
		                	$("#patternForm").submit();
		                	$(this).dialog("destroy");
		                    $(this).hide();
		                },
		                "取消": function () {
		                    $(this).dialog("destroy");
		                    $(this).hide();
		                }
		            }
		        });
			}
		});
	}
	function closeDialog(){
		$(".btn-default").popover('hide');
	}
	function tipMessage(message){
		$.stickyInfo(message);
	}
	function ajaxHandle(ajaxData,callback){
		if(ajaxData.result == 'success'){
			if(ajaxData.message != undefined){
				tipMessage(ajaxData.message);
			}
			if(callback != undefined){
				callback();
			}
		}else{
			tipMessage(ajaxData.message);
		}
	}
</script>
</head>
<body>
<div id="patternDiv" style="display:none;">
	<form class="form-horizontal" role="form" id="patternForm" action="${base}/carve/addOrUpdatePattern" method="post">
		<input type="hidden" name="carveTypeId" id="carveTypeId">
	    <div class="form-group">
		    <label for="typeName" class="col-sm-2 control-label">类型名称</label>
		    <div class="col-sm-10">
		        <input type="text" class="form-control" id="typeName" name="typeName" >
		    </div>
	    </div>
	    <div class="form-group">
		    <label for="url" class="col-sm-2 control-label">url</label>
		    <div class="col-sm-10">
		        <input type="text" class="form-control" id="url" name="url" >
		    </div>
	    </div>
	    <div class="form-group">
		    <label for="content" class="col-sm-2 control-label">内容</label>
		    <div class="col-sm-10">
		          <textarea class="col-sm-12 form-control" id="content" name="content" style="height:200px;"></textarea>
		    </div>
	    </div>
	    <div class="form-group">
		    <label for="content" class="col-sm-2 control-label">正则</label>
		    <div class="col-sm-10">
		          <textarea class="col-sm-12 form-control" id="pattern" name="pattern" style="height:200px;"></textarea>
		    </div>
	    </div>
	</form>
</div>
<jsp:include page="../top.jsp"></jsp:include>

<div style="margin-left:10px;;margin-right:auto;margin-top:60px;">
	
	<jsp:include page="../menu.jsp"></jsp:include>
	
	<div class="content-wrapper col-lg-12" style="padding-left:210px;min-width:500px;z-index:-1;">
		<div class="panel panel-default">
			<div class="panel-heading">
			    <div class="btn-group" role="group" aria-label="...">
				    <button type="button" id="addPattern" class="btn btn-default">新增</button>
				    <button type="button" id="deletePattern" class="btn btn-default">删除</button>
				</div>
			</div>
			<div class="main-content">
				
				<div class="rssList">
					<form id="carveTypeTableForm"  action="${base}/carve/deletePattern" method="post">
						<table id="carveTypeTable" class="table table-striped table-bordered" width="100%" cellspacing="0"></table>
					</form>
				</div>
				
			</div>
		</div>
	</div>
</div>
</body>
</html>