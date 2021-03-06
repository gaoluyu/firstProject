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
<script src="${mainpath}/js/message.js"></script>
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
						<legend class="">告警消息</legend>
					</div>
					<table class="table table-hover" id="messagelist" style="font-size:13px">
					</table>
					<div class="page" id="paging"></div>
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
