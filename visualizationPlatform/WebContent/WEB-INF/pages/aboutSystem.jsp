<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8" />
<title>关于系统</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/utils.js"></script>
</head>                                                                                                                                                       
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />
				<div class="col-md-10 content">
					
				<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12" >
		<div id="legend" class="">
						<legend class="">关于系统</legend>
					</div>
			<p style="text-indent:2em; width:800px;">
				    智慧室分大数据测评与预警系统，利用移动终端众包方式，
				      采集大量室分末梢端网络侧和业务统计数据，利用智慧大数据分析手段，
				      全方位立体式评测室分系统性能，并与地理信息系统（GIS）结合，
				      直观、实时监控室分系统各项性能参数；并通过大数据挖掘平台，
				      对室分网络故障进行深度预警并给出最佳解决方案。
			</p>
			
		</div>
	</div>
</div>
				</div>
			</div>
		</div>
</body>
<script type="text/javascript">
$(function() {
	$("ul.main-menu li:eq(19)").addClass("active");
});
</script>
</html>
