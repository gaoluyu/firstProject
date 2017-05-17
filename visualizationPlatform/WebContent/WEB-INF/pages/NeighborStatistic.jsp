<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<head>
<meta charset=utf-8 />
<title>邻区覆盖分析</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css" />
<link rel="stylesheet"
	href="${mainpath}/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" href="${mainpath}/css/jquery-ui.min.css" />

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

<script src="${mainpath}/js/NeighborStatistic.js"></script>

<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/exporting.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/highcharts-3d.js"></script>

</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />

				<div class="col-md-10 content">
					<div id="legend" class="">
						<legend class="">邻区分析</legend>
					</div>
					<div class="row-fluid" style="margin-bottom: 65px">
						<div class="col-md-2">
							<label>起始时间</label> <input class=" form-control inputs"
								type="text" value="" id="starttime" />
						</div>
						<div class="col-md-2">
							<label>结束时间</label> <input type="text"
								class="form-control inputs " value="" id="endtime" />
						</div>

						<div class="col-md-2 hide">
							<label>网络</label> <select id="networktype"
								class="form-control inputs">
								<c:choose>
									<c:when
										test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.All }">
										<option value="移动">移动</option>
										<option value="联通">联通</option>
										<option value="电信">电信</option>
									</c:when>
									<c:when
										test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaMobile }">
										<option value="移动" selected="selected">移动</option>
									</c:when>
									<c:when
										test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaUnion }">
										<option value="联通" selected="selected">联通</option>
									</c:when>
									<c:when
										test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaTelecom }">
										<option value="电信" selected="selected">电信</option>
									</c:when>
								</c:choose>
							</select>

						</div>
						<div class="col-md-2">
							<label>网络类型</label> <select id="networktypeSelect"
								class="form-control inputs">
								<option value="2G">2G</option>
								<option value="3G">3G</option>
								<option value="4G" selected>4G</option>
							</select>
						</div>

						<div class="col-md-2">
							<label>楼宇</label> <input id="buildingSearch"
								class="form-control inputs" /> <input id="buildingSearchedId"
								type="hidden" />
						</div>

						<div class="col-md-2">
							<label>楼层</label> <select id="floorSelector"
								class="form-control inputs">
								<option>请先选择小区</option>
							</select>
						</div>

						<div class="col-md-2">
							<a> </a><br>
							<button id="search" class="btn btn-info inputs" type="button">筛选</button>
						</div>
					</div>
					<br>
					<div
						style="width: 1200px; overflow-x: auto; overflow-y: hidden; margin-top: 20 px">
						<table id="cellTable" class="table tables">
						</table>
					</div>
					<div id="hist" style="width: 90%; height: 400px"></div>
					<div id="curve" style="width: 90%; height: 400px"></div>
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
		trigger();
	});
</script>