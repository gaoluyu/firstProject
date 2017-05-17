<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>工参录入</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${mainpath}/css/jquery-ui.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/jquery-ui.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/gongcanluru.js"></script>
<!--<script src="${mainpath}/js/outdoor.js"></script>  -->
<script type="text/javascript" src="${mainpath}/js/ajaxfileupload.js"></script>
</head>
<style>
._mytable>thead>tr>th, ._mytable>tbody>tr>th, ._mytable>tfoot>tr>th,
	._mytable>thead>tr>td, ._mytable>tbody>tr>td, ._mytable>tfoot>tr>td {
	padding: 8px 3px;
	text-align: center;
	width: 30px
}

.
._mytable>thead {
	background-color: #f5f5f5
}
#SouSuo{
margin-left:15px;
margin-top:5px;

}
#CiSearch{
margin-left:10px;
margin-top:-22px;
}
</style>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />

				<div class="col-md-10 content">
					<div id="legend" class="">
						<legend class="">小区列表</legend>
					</div>
					<div class="row-fluid" style="margin-bottom: 65px">
						<div class="col-md-2">
							<span style="color:gray;float:left;padding:5px 2px">
							<i class="glyphicon glyphicon-search" id="SouSuo"></i>
							<input id="CiSearch" placeholder="搜索小区CI或中文名" class="form-control inputs" 
							style="min-width:160px;padding-left:20px;" /></span>
							
						</div>
						<div class="col-md-3">
							<form>
								<input class="btn btn-info" type="file" name="cell"
									id="cellUpload"  />
							</form>
						</div>
						<div class="col-md-2">
							<button type="button" id="upload" name="submit"
								class="btn btn-info start">
								<i class="glyphicon glyphicon-upload" style="margin-top:3px;margin-left:-5px;"></i> <span>开始上传</span>
							</button>
						</div>
						<div class="col-md-2">
							<button type="button" id="add" name="add"
								class="btn btn-warning start">
								<a href="./addCell" style="color: white"> <i
									class="glyphicon glyphicon-plus-sign"></i> <span>添加小区</span>
								</a>
							</button>
						</div>

						<div class="col-md-2">
							<button type="button" id="" name="" class="btn btn-primary start">

								<a href="http://115.28.164.238/report/parameterReport.xls"
									style="color: white"> <i
									class="glyphicon glyphicon-plus-sign"></i> <span>模板下载</span>
								</a>
							</button>
						</div>
					</div>
					<div>
						<table id="workparameterTable" class="_mytable table table-hover "
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
		uploadInit("${mainpath}");
		initCiSearch(onCiSearch);
	});
</script>
</html>
