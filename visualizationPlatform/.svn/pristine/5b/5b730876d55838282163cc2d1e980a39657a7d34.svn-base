<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8" />
<title>用户信息管理</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/systemsetting.js"></script>
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
						<legend class="">系统设置</legend>
					</div>
					<h4>告警阈值设置（信号强度）</h4><br/>
					<dl class="dl-horizontal" id="warningValueSetting"></dl>
					<button style="margin:10 10 10 200" id="modifyWarningValueSetting" onclick="modifyWarningValueSetting()" class="btn btn-info" type="button">提交阈值设置</button>
						<h4>覆盖率指标设置</h4><br/>
					<dl class="dl-horizontal" id="coverageRationSetting"></dl>
					<button style="margin:10 10 10 200" id="modifyCoverageRationSetting" onclick="modifyCoverageRationSetting()" class="btn btn-info" type="button">提交覆盖率设置</button>
					<h4>信号质量等级设置</h4><br/>
					<table class="table" id="networkListSetting"></table>
					<button style="margin:10 10 10 200" id="modifyNetworkListSetting" onclick="modifyNetworkListSetting()" class="btn btn-info" type="button">提交信号质量等级设置</button>
				</div>
			</div>
		</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		init("${mainpath}");
		reportInit("${sessionScope.loginedUser.role}");
	});
</script>
</html>
