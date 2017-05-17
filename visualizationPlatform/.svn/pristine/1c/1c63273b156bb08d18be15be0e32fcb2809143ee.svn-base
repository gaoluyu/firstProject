<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8" />
<title>表单测试工具</title>
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/utils.js"></script>
</head>
<body class="bodybg">
	<div class="container-fluid">

		<div class="row-fluid">


			<div class="col-md-10 content">

				<div id="legend" class="">
					<legend class="">url</legend>
					<input type="text" class="form-control inputs " value="" id="url" />
					<button id="search" class="btn btn-info inputs" type="button">筛选</button>
					<legend class="">parameters:</legend>
					<textarea id="param" rows="30" cols="70"></textarea>
					<legend class="">结果</legend>
					<div id="result"></div>
				</div>

			</div>
		</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#search").click(function() {
			var param = $("#param").val();
			console.log('befor:' + param);
			var test = /^\{[.\s\S]*\}$/;
			if (!test.test(param)) {
				alert("error parameter format");
				param = "{}";
			}
			param = param.replace(/[\n\r]+/g, "");
			param = param.replace(/\"[\s\r\n]*\{/g, "'{");
			param = param.replace(/\}[\s\r\n]*\"/g, "}'");
			param = param.replace(/\"[\s\r\n]*\[/g, "'[");
			param = param.replace(/\][\s\r\n]*\"/g, "]'");
			console.log('after:' + param);

			param = eval('(' + param + ')');
			ajaxUtil(param, $("#url").val(), function(response) {
				$("#result").html(JSON.stringify(response));
			}, function(response, reason) {
			});
		});
	});
</script>
</html>
