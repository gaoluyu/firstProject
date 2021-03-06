<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8" />
<title>用户信息管理</title>
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<link rel="stylesheet" href="${mainpath}/css/buttons.css"
	type="text/CSS" />
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/detailMessage.js"></script>
<script src="${mainpath}/js/utils.js"></script>

</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />
				<div class="col-md-10 content">
					<div id="legend" class="">
						<legend class="">告警详情</legend>
					</div>
					<dl class="dl-horizontal" id="detailmessage">
					</dl>
					<div class="col-md-1">
					</div>
					<div class="col-md-1">
						<button type="button" class="btn btn-warning" id="confirm"></button>
					</div>
					<div class="col-md-1">
						<button type="button" class="btn btn-success" id="alarm"></button>
					</div>
					<div class="col-md-2">
						<input type="text" class="form-control" placeholder="请输入手机号"
							id="telephone">
					</div>
				</div>


			</div>
		</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		init("${mainpath}");
	});
</script>
</html>
