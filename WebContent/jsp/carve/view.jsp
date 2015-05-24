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
<link href="${base}/static/js/datatables/css/datatables_bootstrap.css" rel="stylesheet" type="text/css"/>
<%@include file="../js-file.jsp" %>
<script src="${base}/static/js/datatables/js/jquery.dataTables.min.js"></script>
<script src="${base}/static/js/datatables/js/jquery.dataTables.rowGrouping.js"></script>
<script src="${base}/static/js/datatables/js/ColReorderWithResize.js"></script>
<script src="${base}/static/js/datatables/extras/ColVis/js/ColVis.min.js"></script>
<script src="${base}/static/js/datatables/js/jquery.dataTables.bootstrap.js"></script>
<script>
	var datatables;
	var carveTypeId = -1;
	$(function(){
		$(".menu").removeClass("active");
		$("#menuView").addClass("active");
		$("#carveType").change(function(){
			carveTypeId = $(this).val();
			var oSettings = datatables.fnSettings();
			oSettings.sAjaxSource =  "${base}/carve/listDetail?carveTypeId=" + carveTypeId;
			datatables.fnDraw();
		});
		datatables = $('#carveUrlTable').dataTable({
		    "sDom": "rt<'row dt_foot'<'col-md-4'i><'col-md-3'><'col-md-5'p>>",
			"bServerSide": true,
            "sAjaxSource": "${base}/carve/listDetail?carveTypeId=" + carveTypeId,
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
                "sName" : "carveUrlId",
                "mData" : "carveUrlId",
                "sClass":"w50p center",
                "bSortable" : false
            }, {
                "sTitle" : "名称",
                "sName" : "title",
                "mData" : function(data,type,row){
                    return "<a href='"+data.url+"' target='_blank'>" + data.title + "</a>";
                }
            }, {
                "sTitle" : "时间",
                "sName" : "createTime",
                "sClass":"w160p",
                "mData" : function(data,type,row){
                    return data.createTime;
                }
            }, {
                "sTitle" : "操作",
                "sClass":"w50p",
                "mData" : function(data,type,row){
                	var str = "";
                	return str;
                },
                "bSortable" : false
            }]
		});
	});
</script>
</head>
<body>
	<jsp:include page="../top.jsp"></jsp:include>
	
	<div style="margin-left:10px;;margin-right:auto;margin-top:60px;">
		
		<jsp:include page="../menu.jsp"></jsp:include>
		
		<div class="content-wrapper col-lg-12" style="padding-left:160px;min-width:500px;z-index:-1;">
			<div class="panel panel-default">
				<div class="panel-heading">
					<select name="carveType" id="carveType">
						<option value="-1">请选择</option>
						<c:forEach items="${carveTypeList}" var="carveType">
							<option value="${carveType.carveTypeId}">${carveType.typeName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="main-content">
					<div>
						<form id="carveUrlTableForm"  action="" method="post">
							<table id="carveUrlTable" class="table table-striped table-bordered" width="100%" cellspacing="0"></table>
						</form>
					</div>
				</div>
			</div>
			
			
		</div>
		
	</div>
</body>
</html>