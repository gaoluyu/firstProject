﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<head>
<meta charset=utf-8 />
<title>移动互联网终端分析系统</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css" />

<link rel="stylesheet" href="${mainpath}/css/jquery-ui.min.css" />
<link rel="stylesheet"
	href="${mainpath}/css/bootstrap-datetimepicker.css" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/jquery-ui.min.js"></script>
<script
	src="http://api.map.baidu.com/api?v=2.0&ak=CB2ede775afeb6e413abd40261396a69"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.js"></script>
<script src="${mainpath}/js/bootstrap-datetimepicker.fr.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/search.js"></script>
<script src="${mainpath}/js/outdoor.js"></script>

</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />

				<div class="col-md-10 content">
				<div id="legend" class="">
						<legend class="">站点全景</legend>
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
						<div class="col-md-2">
							<label>网络类型</label> <select id="network"
								class="form-control inputs">
								<c:if
									test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaUnion ||sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.All }">
									<option value="联通2G">联通2G</option>
									<option value="联通3G">联通3G</option>
									<option value="联通4G"  selected="selected">联通4G</option>
								</c:if>
								<c:if
									test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaMobile ||sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.All }">
									<option value="移动2G">移动2G</option>
									<option value="移动3G">移动3G</option>
									<option value="移动4G"  selected="selected">移动4G</option>
								</c:if>
							
								<c:if
									test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaTelecom ||sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.All }">
									<option value="电信2G">电信2G</option>
									<option value="电信3G">电信3G</option>
									<option value="电信4G"  selected="selected">电信4G</option>
								</c:if>


							</select>
						</div>
						<div class="col-md-2">
							<label>小区CI或中文名</label> <input id="CiSearch"
								class="form-control inputs" />
						</div>
						<div class="col-md-2">
							<a> </a><br>
							<button id="search" class="btn btn-info inputs" type="button">筛选</button>
						</div>
					</div>
					<br>
					
					<div id="allmap" style="width: 100%; height: 700px;" class="map">

					</div>
					<div class="tuli" id="tuli"></div>
					<script type="text/javascript">
						// 百度地图API功能
						$(document).ready(function() {
							initSearch("${mainpath}", onSearch);
							initTimePicker();
							initCiSearch(onCiSearch);
							trigger();
						});

						var mp = new BMap.Map("allmap");
						var point = new BMap.Point(116.404, 39.915);
						mp.enableScrollWheelZoom();
						var mapStyle = {
							features : [ "road", "building", "water", "land" ],
							style : "grayscale"
						};
						mp.setMapStyle(mapStyle);
						var getPosition = true;
						var setLng = -1;
						var setLat = -1;
						if (setLng == -1) {
							setLng = 116.365459;
							getPosition = false;
						}
						if (setLat == -1) {
							setLat = 39.968429;
							getPosition = false;
						}
						var point = new BMap.Point(setLng, setLat);
						if (getPosition) {
							var marker = new BMap.Marker(point); // 创建标注    
							map.addOverlay(marker);
						}
						mp.centerAndZoom(point, 18); // 初始化地图，设置中心点坐标和地图级别
						mp.enableScrollWheelZoom(); // 允许滚轮缩放
						var radiusInZoom18 = 20;
						var points = [ {
							"lng" : 116.365459,
							"lat" : 39.968429,
							"count" : 100
						} ];

						if (!isSupportCanvas()) {
							alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
						}
						heatmapOverlay = new BMapLib.HeatmapOverlay({
							"radius" : radiusInZoom18
						});
						mp.addOverlay(heatmapOverlay);
						heatmapOverlay.setDataSet({
							data : points,
							max : 100
						});
						//是否显示热力图
						function openHeatmap() {
							heatmapOverlay.show();
						}
						function closeHeatmap() {
							heatmapOverlay.hide();
						}
						openHeatmap();
						mp.centerAndZoom(point, 18);
						var pixel1 = new BMap.Pixel(5, 5);
						var pixel2 = new BMap.Pixel(radiusInZoom18 + 5, 5);
						var point1 = mp.pixelToPoint(pixel1);
						var point2 = mp.pixelToPoint(pixel2);

						mp.addEventListener("zoomend", function() {
							var zoomedLevel = this.getZoom();

							var _p1 = mp.pointToPixel(point1);
							var _p2 = mp.pointToPixel(point2);
							var newRadius = _p2.x - _p1.x;
							if (newRadius <= 0)
								newRadius = 1;
							heatmapOverlay.setOptions({
								"radius" : newRadius
							});
						});
						function setGradient(type) {
							var gradient;
							if (type == 1) {
								gradient = {
									"0.45" : "#6666FF",
									"0.55" : "#66CCFF",
									"0.65" : "#99FF00",
									"0.85" : "#FFFF00",
									"0.99" : "#FF6600"
								};
							} else if (type == 2) {
								gradient = {
									"0.59" : "#6666FF",
									"0.69" : "#66CCFF",
									"0.79" : "#99FF00",
									"0.89" : "#FFFF00",
									"0.99" : "#FF6600"
								};
							} else if (type == 3) {
								gradient = {
									"0.45" : "#6666FF",
									"0.55" : "#66CCFF",
									"0.65" : "#99FF00",
									"0.85" : "#FFFF00",
									"0.99" : "#FF6600"
								};
							}
							heatmapOverlay.setOptions({
								"gradient" : gradient
							});
						}
						setGradient(3);
						mp.centerAndZoom(point, 17);
						var styleJson = [ {
							"featureType" : "all",
							"elementType" : "geometry",
							"stylers" : {
								"hue" : "#007fff",
								"saturation" : 89
							}
						}, {
							"featureType" : "water",
							"elementType" : "all",
							"stylers" : {
								"color" : "#ffffff"
							}
						} ]
						mp.setMapStyle({
							style : 'light'
						});

						//判断浏览区是否支持canvas
						function isSupportCanvas() {
							var elem = document.createElement('canvas');
							return !!(elem.getContext && elem.getContext('2d'));
						}
						ajaxUtil({}, "/androidServer/outdoor/strengthHeat",
								function(response) {
									var p = [];
									var list = response.list;
									var _lng = response.coord.lng;
									var _lat = response.coord.lat;
									//alert(_lng);
									var point = new BMap.Point(_lng, _lat);
									mp.centerAndZoom(point, 16);
									for (var i = 0; i < list.length; i++) {
										var item = {
											"lng" : list[i].lng,
											"lat" : list[i].lat,
											"count" : list[i].value
										};
										p.push(item);
									}
									heatmapOverlay.setDataSet({
										data : p,
										max : 100
									});

								}, function(response, reason) {
								});
					</script>

				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>