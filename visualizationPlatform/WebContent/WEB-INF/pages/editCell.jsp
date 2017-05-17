<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>工参录入</title>
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/editCell.js"></script>

<script type="text/javascript" src="${mainpath}/js/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=CB2ede775afeb6e413abd40261396a69"></script>
</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />

				<div class="col-md-10 content">

					<form class="form-horizontal" onsubmit="return false;">
						<fieldset>
							<div id="legend" class="">
								<legend class="">修改小区信息</legend>
							</div>
							<dl class="dl-horizontal">
								<div class="control-group">
									<dt>
										<label class="control-label">省份</label>
									</dt>
									<dd>
										<div class="controls">
											<select id="province" class="input-xlarge"
												style="width: 130px">
												<option></option>
											</select>
										</div>
									</dd>
									<br />
								</div>

								<div class="control-group">
									<dt>
										<label class="control-label">市地</label>
									</dt>
									<dd>
										<div class="controls">
											<select id="city" class="input-xlarge" style="width: 130px">
												<option></option>
											</select>
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label">县区</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="region" type="text" placeholder="请输入小区的ID"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label">网络类型</label>
									</dt>
									<dd>
										<div class="controls">
											<select id="network" class="input-xlarge"
												style="width: 130px" onchange="networkChanged()">

												<c:if
													test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaMobile ||sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.All }">
													<option value="移动2G">移动2G</option>
													<option value="移动3G" selected="selected">移动3G</option>
													<option value="移动4G">移动4G</option>
												</c:if>
												<c:if
													test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaUnion ||sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.All }">
													<option value="联通2G">联通2G</option>
													<option value="联通3G">联通3G</option>
													<option value="联通4G">联通4G</option>
												</c:if>
												<c:if
													test="${sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.ChinaTelecom ||sessionScope.loginedUser.network_mnc==sessionScope.networkConstants.All }">
													<option value="电信2G">电信2G</option>
													<option value="电信3G">电信3G</option>
													<option value="电信4G">电信4G</option>
												</c:if>
											</select>
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label">小区ID</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="ci" type="text" placeholder="请输入小区的ID"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div id="enodebDiv" class="control-group">
									<dt>
										<label class="control-label">eNodeB</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="enodeb" type="text" placeholder="请输入enodeb"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label">LAC</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="lac" type="text" placeholder="请输入LAC"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label">频段</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="freqChannel" type="text" placeholder="请输入信号频段"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label" for="input01">小区英文名称</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="cellNameEng" type="text" placeholder="请输入小区英文名称"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label">小区中文名称</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="cellNameCh" type="text" placeholder="请输入小区中文名称"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label">小区地址</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="cellAddress" type="text" placeholder="请输入小区详细地址"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label" for="input01">归属区域</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="belonging" type="text" placeholder="请输入归属区域"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label" for="input01">覆盖场景</label>
									</dt>
									<dd>
										<div class="controls">
											<input id="scene" type="text" placeholder="请输入覆盖场景"
												class="input-xlarge">
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<dt>
										<label class="control-label">覆盖类型</label>
									</dt>
									<dd>
										<div class="controls">
											<input type="radio" value="1" data-par="1" name="isIndoor"
												checked="checked"> 室内 <input type="radio" value="0"
												data-par="0" name="isIndoor"> 室外
										</div>
									</dd>
									<br />
								</div>
								<div class="control-group">
									<!-- Text input-->
									<dt>
										<label class="control-label" for="input01">经纬度坐标</label>
									</dt>
									<dd>
										<div class="controls">
											<input type="text" id="lng" placeholder="经度坐标"
												class="input-xlarge"> <input type="text" id="lat"
												placeholder="纬度坐标" class="input-xlarge">
											<button class="btn btn-info" onclick="mark()">定位</button>
											<br /> <br />

											<div id="allmap"></div>
										</div>
									</dd>

								</div>
								<style type="text/css">
#allmap {
	width: 50%;
	height: 200px;
}
</style>
								<script type="text/javascript">
									var map = new BMap.Map("allmap");
									var marker = null;
									map.addEventListener("click", getInfo);
									function initMarker() {
										var lng1 = $("#lng").val();
										var lat1 = $("#lat").val();
										var point1 = new BMap.Point(lng1, lat1);
										map.centerAndZoom(point1, 11);
										marker = new BMap.Marker(point1);
										map.addOverlay(marker);
									}
									function getInfo(e) {
										//map.clearOverlay();
										var lng = e.point.lng;
										var lat = e.point.lat;
										var point = new BMap.Point(lng, lat);
										$("#lng")[0].value = lng;
										$("#lat")[0].value = lat;
										map.setCenter(point);
										marker.setPosition(point);//设置标注的地理坐标
									}
									function mark() {
										var lng = $("#lng").val();
										var lat = $("#lat").val();
										var point = new BMap.Point(lng, lat);
										map.setCenter(point);
										marker.setPosition(point); //设置标注的地理坐标
									}
								</script>

								<div class="control-group">
									<label class="control-label"></label>

									<!-- Button -->
									<dd>
										<div class="controls">
											<button id="submit" class="btn btn-success">修改</button>
										</div>
									</dd>
								</div>
							</dl>
						</fieldset>
					</form>

					<div></div>

				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	//var fileName;

	$(document).ready(function() {
		editInit("${mainpath}");

	});
</script>
</html>
