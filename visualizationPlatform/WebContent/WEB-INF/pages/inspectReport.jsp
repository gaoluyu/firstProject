<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>移动互联网终端分析系统</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<c:if test="${sessionScope.loginedUser.role==3}">
<link rel="stylesheet" href="${mainpath}/css/green.css" />	
</c:if>
<link rel="stylesheet"
	href="${mainpath}/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" href="${mainpath}/css/jquery-ui.min.css" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/jquery-ui.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.fr.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/search.js"></script>
<script src="${mainpath}/js/inspectReport.js"></script>
<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"
	type="text/javascript"></script>


</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<jsp:include page="./common/nav.jsp" />

			<div class="col-md-10 content">
				<div class="row-fluid">
					<div id="legend" class="">
						<legend class="">巡检报表</legend>
					</div>
					<div class="col-md-2">
						<label>起始时间</label> <input class=" form-control inputs"
							type="text" value="" id="starttime" />
					</div>
					<div class="col-md-2">
						<label>结束时间</label> <input type="text"
							class="form-control inputs " value="" id="endtime" />
					</div>
					<div class="col-md-2">
							<label>楼宇</label> <input id="buildingSearch"
								class="form-control inputs" /> <input id="buildingSearchedId"
								type="hidden" />
						</div>
					<div class="col-md-2">
						<a> </a><br>
						<button id="search" class="btn btn-info inputs" type="button">筛选</button>
					</div>
					<div class="col-md-2">
						<a> </a><br>
						<button id="export" class="btn btn-info inputs" type="button">导出</button>
					</div>
				</div>
<br>
<br>
<br>
				<div class="row-fluid" id="">
					<CAPTION>
						<div class="" id="caption" style="">
						</div>
					</CAPTION>
					<div style="width:1000px;overflow-x:auto;overflow-y:hidden;">
					<table id="inspectList" class="table tables">
					</table>
					<table id="inspectTimesList" class="table tables">
					</table>
					</div>
					<div class="page" id="paging"></div>
				</div>
				<div class="tabbable pull-left" id="tabs-753109"
					style="margin-top: 20px">
					<div class="tab-pane" id="panel-281468">
						<div id="curve" style="width: auto; height: 400px"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		initSearch("${mainpath}", onSearch);
		initTimePicker();
		reportInit("${sessionScope.loginedUser.role}");
		initBuildingSearch();
		trigger();
	});
</script>