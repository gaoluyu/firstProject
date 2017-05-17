<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8" />
<title>工参录入</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${mainpath}/css/jquery-ui.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/jquery-ui.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/tooltip.js"></script>
<script src="${mainpath}/js/popover.js"></script>
<script src="${mainpath}/js/building.js"></script>
<script src="${mainpath}/js/utils.js"></script>
</head>
<style>
#SouSuo{
margin-left:15px;
margin-top:5px;
width:10px;
}
#buildingIdSearch{
margin-left:10px;
margin-top:-24px;
}
</style>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />
				<div class="col-md-10 content">
					<div class="row-fluid" style="margin-bottom: 65px">
						<div id="legend" class="">
						<legend class="">楼宇列表</legend>
					</div>
						<div class="col-md-2">
							<span style="color:gray;float:left;padding:5px 2px">
							<i class="glyphicon glyphicon-search" id="SouSuo"></i>
							 <input id="buildingIdSearch" placeholder="&nbsp;搜索楼编号或楼名"
							 class="form-control inputs" style="min-width:160px;padding-left:20px;" />
							</span>
							
						</div>
					
							<div class="col-md-2">
							<button type="button" id="add" name="add"
								class="btn btn-warning start">
								<a href="./addBuilding" style="color: white"> <i
									class="glyphicon glyphicon-plus-sign"></i> <span>添加楼宇</span>
								</a>
							</button>

						</div>
						<br>
						<br>
						<table id="buildingList" class="table tables"  >
						</table>
						<div class="page" id="paging"></div>
					

					</div>
					<div>
						<table id="workparameterTable" class="table table-hover"
							style="font-size: 12px">
						</table>
						<div class="page" id="paging"></div>
					</div>


				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	//var fileName;

	$(document).ready(function() {
		init("${mainpath}");
		initBuildingSearch(onBuildingSearch);
	});
</script>
</html>
