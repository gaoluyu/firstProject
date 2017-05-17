<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset=utf-8 " />
<title>工参录入</title>
<link rel="stylesheet" href="${mainpath}/css/default.css" />
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/editBuilding.js"></script>
<script src="${mainpath}/js/ajaxfileupload.js"></script>
<script src="${mainpath}/js/utils.js"></script>
</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />

				<div class="col-md-10 content">

					<form class="form-horizontal" onsubmit="return false;">
						<div id="legend" class="">
							<legend class="">修改<span id="building_name" style="color:#5bc0de"></span>信息</legend>
						</div>
						<input id="id" type="hidden">
						<dl class="dl-horizontal">
							<dt>
								小区<br> 
							</dt>
				 			<dd>
								<div id="ciBox"></div>
								
								<a href="javascript:addCi('#ciBox',0,'','');"><i class="glyphicon glyphicon-plus-sign"> </i> 添加</a>
							</dd>
							<br/>
							<dt>省份</dt>
							<dd>
								<select id="province" class="input-xlarge" style="width: 130px">
									<option></option>
								</select>
							</dd>
							<br/>
							<dt>
								<label class="control-label">市地</label>
							</dt>
							<dd>
								<select id="city" class="input-xlarge" style="width: 130px">
									<option></option>
								</select>
							</dd>
							<br/>
							<dt>
								<label class="control-label">区域(具体地址)</label>
							</dt>
							<dd>
								<input id="region" type="text" placeholder="请输入所在区域"
									class="input-xlarge">
							</dd>
							<br/>
							<dt>
								<label class="control-label">单元</label>
							</dt>
							<dd>
								<input id="unit" type="text" placeholder="请输入单元"
									class="input-xlarge">
							</dd>
							<br/>
							<dt>
								<label class="control-label" for="input01">地上层数</label>
							</dt>
							<dd>
								<input id="floorUpGround" type="text" placeholder="请输入地上层数"
									class="input-xlarge">
							</dd>
							<br/>
							<dt>
								<label class="control-label">地下层数</label>
							</dt>
							<dd>
								<input id="floorUnderGround" type="text" placeholder="请输入地下层数"
									class="input-xlarge">
							</dd>
							<br/>
							<dt>
								<label class="control-label">名称</label>
							</dt>
							<dd>
								<input id="name" type="text" placeholder="请输入名称"
									class="input-xlarge">
							</dd>
							<br/>
							<dt>
							<label class="control-label">室内地图</label>
							</dt>
							<dd>
							<a href="javascript:addMap('#mapBox','','');">点击添加室内地图</a>
							<table ><tr id="mapBox"></tr></table>
							</dd>
							<dt></dt>
							<dd><button id="submit" class="btn btn-success">更新</button></dd>
							
						</dl>
					</form>
				</div>
			</div>
		</div>
</body>
<script type="text/javascript">
	//var fileName;

	$(document).ready(function() {
		init("${mainpath}");

	});
</script>
</html>
