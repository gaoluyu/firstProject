<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<head>
<meta charset=utf-8 />
<title>巡检管理</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css" />
<c:if test="${sessionScope.loginedUser.role==3}">
	<link rel="stylesheet" href="${mainpath}/css/green.css" />
</c:if>
<link rel="stylesheet"
	href="${mainpath}/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" href="${mainpath}/css/jquery-ui.min.css" />
<style>
#heatmapContainer {
	width: 400px;
	margin-top: 0px;
	margin-right: auto;
	margin-bottom: 0px;
	margin-left: auto;
	height: 400px;
}
</style>
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script
	src="http://api.map.baidu.com/api?v=2.0&ak=CB2ede775afeb6e413abd40261396a69"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.fr.js"></script>
<script src="${mainpath}/js/jquery-ui.min.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/search.js"></script>
<script src="${mainpath}/js/inspectAdmin.js"></script>
<!-- <script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
 -->
<script src="${mainpath}/js/gmaps-heatmap.js"></script>
<script src="${mainpath}/js/heatmap.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/highcharts-3d.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/highcharts-more.js"></script>
<script src="${mainpath}/lib/exporting.js"></script>
<script src="${mainpath}/lib/canvas-tools.js"></script>
<script src="${mainpath}/lib/highcharts-export-clientside.js"></script>
</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />

				<div class="col-md-10 content">
					<div class="row-fluid" style="margin-bottom: 65px">
						<div id="legend" class="">
							<legend class="">巡检管理</legend>
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
							<label>手机号</label> <select id="imsi" class="form-control inputs">
								<option>请选择手机号</option>
							</select>
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
					<div id="date_container" class="graphs"></div>
					<div id="boxPlot_container" class="graphs"></div>
					<div id="container" class="graphs"></div>
					<div class="graphs">
						<div style="width: 600px; height: 400px; margin: 50px 100px"
							id="heatmapContainer"></div>
					</div>
					<!-- Modal弹出窗口式样 -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Modal title</h4>
								</div>
								<div class="modal-body" id="myModalBody">...</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">关闭</button>
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
		initBuildingSearch();
		reportInit("${sessionScope.loginedUser.role}");
		heatMapInit();
		trigger();
	});
</script>