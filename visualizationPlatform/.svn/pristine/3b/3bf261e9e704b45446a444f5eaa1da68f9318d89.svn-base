<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>移动互联网终端分析系统</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css" />
<link rel="stylesheet"
	href="${mainpath}/css/bootstrap-datetimepicker.css" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.fr.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/search.js"></script>
<script src="${mainpath}/js/indoor_map.js"></script>
<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"
	type="text/javascript"></script>
</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<jsp:include page="./common/nav.jsp" />

			<div class="col-md-10 content">
			<div id="legend" class="">
						<legend class="">室分地图</legend>
					</div>
				<div class="alert alert-warning hide " id="alertID">
					<a href="#" class="close" data-dismiss="alert">&times; </a> <strong>提示！</strong>该层尚未上传室内地图，请您到楼宇管理页面上传室内地图。
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
<br>
<br>
				<div class="tabbable pull-left" id="tabs-753109"
				>
					<div class="tab-pane  active" id="panel-529525">
						<div
							style="width:100%; height: 400px; float: left; margin-bottom: 50px">
							<canvas id="canvas" width="600" height="400"
								style="border: 1px #cccccc solid;margin:2% 10%; background-image: url('/androidServer/images/noneMap.png')">你的浏览器不支持canvas</canvas>
							<div
								style="width:80px;position:absolute;left:760px;top:112px; height: 400px; border: 0px #cccccc solid;   padding: 10 10 10 0;">
								<table class="table legend"
									style="border: 1px solid #cccccc; margin-top: 180px" id="tuli">
								</table>
							</div>
						</div>
						<table id="indoorList" class="table tables"
							style="margin-top: 15px">
							<thead>
								<tr class="active">
									<th>小区</th>
									<th>楼层</th>
									<th>描述</th>
									<th>MAC</th>
									<th>平均场强</th>
									<th>最大场强</th>
									<th>最小场强</th>
									<th>覆盖率</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div class="page" id="paging"></div>
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
		initCell();
		$("#floorSelector").change(function() {
			loadFloorMap("#floorSelector", "#canvas");
			/*to yuanzhe ,这个sessionStorage在我这里跑不通，我改回为服务端session保存*/
			//sessionStorage.floor=$("#floorSelector").val();
		});
		/* 		if(sessionStorage.floor==null){
		 }else{
		 $("#floorSelector").val(sessionStorage.floor);
		 } */
		trigger();
		//preSearch();
	});
</script>