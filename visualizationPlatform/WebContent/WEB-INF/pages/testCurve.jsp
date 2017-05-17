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
	
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>?§???¨?o?è??????????ˉ???????3????</title>
<script src="./jquery-1.11.2.min.js"></script>
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
					<legend class="">??¤??????è?¨</legend>
				</div>
				<div class="row-fluid">
					<div class="col-md-2">
						<label>èμ·?§????é?′</label> <input class=" form-control inputs"
							type="text" value="" id="starttime" />
					</div>
					<div class="col-md-2">
						<label>?????????é?′</label> <input type="text"
							class="form-control inputs " value="" id="endtime" />
					</div>
					<div class="col-md-2">
						<label>?°???o??°???</label> <select id="cellSelector"
							class="form-control inputs">
						</select>
					</div>
					<div class="col-md-2">
						<label>?￥??±?</label> <select id="floorSelector"
							class="form-control inputs">
							<option>èˉ·???é??????°???o</option>
						</select>
					</div>

					<div class="col-md-2">
						<a> </a><br>
						<button id="search" class="btn btn-info inputs" type="button">?-?é??</button>
					</div>

				</div>
				<br> <br> <br> <br>
				<div class="row-fluid">
					<table id="indoorList" class="table tables">
						<thead>
							<tr class="active">
								<th>?°???o</th>
								<th>?￥??±?</th>
								<th>??????</th>
								<th>???è?°</th>
								<th>?13?????o??o???dBm???</th>
								<th>????¤§??o??o(dBm)</th>
								<th>????°???o??o(dBm)</th>
								<th>??￥?????o??oèμ°???</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<div class="page" id="paging"></div>
					<div class="pointnum">
						??±?????????????μ???1<span id="totalCountId"></span>??a
					</div>
				</div>
			</div>
		</div>

	</div>
	<div id="curve" style="width: 90%; height: 400px">123</div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {

	});
	$(function() {
		$('#curve')
				.highcharts(
						{
							title : {
								text : 'Monthly Average Temperature',
								x : -20
							// center
							},
							subtitle : {
								text : 'Source: WorldClimate.com',
								x : -20
							},
							xAxis : {
								categories : [ 'Jan', 'Feb', 'Mar', 'Apr',
										'May', 'Jun', 'Jul', 'Aug', 'Sep',
										'Oct', 'Nov', 'Dec' ]
							},
							yAxis : {
								title : {
									text : 'Temperature (?°C)'
								},
								plotLines : [ {
									value : 0,
									width : 1,
									color : '#808080'
								} ]
							},
							tooltip : {
								valueSuffix : '?°C'
							},
							legend : {
								layout : 'vertical',
								align : 'right',
								verticalAlign : 'middle',
								borderWidth : 0
							},
							series : [
									{
										name : 'Tokyo',
										data : [ 7.0, 6.9, 9.5, 14.5, 18.2,
												21.5, 25.2, 26.5, 23.3, 18.3,
												13.9, 9.6 ]
									},
									{
										name : 'New York',
										data : [ -0.2, 0.8, 5.7, 11.3, 17.0,
												22.0, 24.8, 24.1, 20.1, 14.1,
												8.6, 2.5 ]
									},
									{
										name : 'Berlin',
										data : [ -0.9, 0.6, 3.5, 8.4, 13.5,
												17.0, 18.6, 17.9, 14.3, 9.0,
												3.9, 1.0 ]
									},
									{
										name : 'London',
										data : [ 3.9, 4.2, 5.7, 8.5, 11.9,
												15.2, 17.0, 16.6, 14.2, 10.3,
												6.6, 4.8 ]
									} ]
						});

	});
	alert(1)
</script>