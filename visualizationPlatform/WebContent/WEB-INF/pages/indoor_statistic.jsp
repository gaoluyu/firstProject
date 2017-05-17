<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>移动互联网终端分析系统</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<link rel="stylesheet"
	href="${mainpath}/css/bootstrap-datetimepicker.css" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.fr.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/search.js"></script>
<script src="${mainpath}/js/indoor_statistic.js"></script>
<script type="text/javascript"
	src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>

</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<jsp:include page="./common/nav.jsp" />

			<div class="col-md-10 content">
				<div id="legend" class="">
					<legend class="">室分图表</legend>
				</div>
				<div class="row-fluid">
					<div class="col-md-2">
						<label>起始时间</label> <input class=" form-control inputs"
							type="text" value="" id="starttime" />
					</div>
					<div class="col-md-2">
						<label>结束时间</label> <input type="text"
							class="form-control inputs " value="" id="endtime" />
					</div>
					<div class="col-md-2">
						<label>小区地址</label> <select id="cellSelector"
							class="form-control inputs">
						</select>
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
				<br> <br> <br> <br>
				<div class="row-fluid">
					<table id="indoorList" class="table tables">
						<thead>
							<tr class="active">
								<th>小区</th>
								<th>楼层</th>
								<th>位置</th>
								<th>描述</th>
								<th>平均场强（dBm）</th>
								<th>最大场强(dBm)</th>
								<th>最小场强(dBm)</th>
								<th>查看场强走势</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<div class="page" id="paging"></div>
					<div class="pointnum">
						共有定位监测点<span id="totalCountId"></span>个
					</div>
				</div>
				<div id="curve" style="width: 90%; height: 400px"></div>
			</div>
		</div>

	</div>


</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		initSearch("${mainpath}", onSearch);
		initTimePicker();
		initCell();
		/* 		$("#floorSelector").change(function() {
		 sessionStorage.floor=$("#floorSelector").val();
		 });
		 if(sessionStorage.floor==null){
		 }else{
		 $("#floorSelector").val(sessionStorage.floor);
		 } */
		trigger();
	});
</script>