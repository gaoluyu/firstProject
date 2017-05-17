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
<script src="${mainpath}/js/editBeacon.js"></script>
<script src="${mainpath}/js/utils.js"></script>
</head>
<body class="bodybg">
	<div class="container-fluid">
		<jsp:include page="./common/header.jsp" />
		<div class="row-fluid">
			<div>
				<jsp:include page="./common/nav.jsp" />

				<div class="col-md-10 content">

					<legend class="">修改iBeacon信息</legend>
					<dl class="dl-horizontal">
						<dt>
							<label class="control-label">MAC</label>
						</dt>
						<dd>
							<input id="id" type="text" placeholder="请输入设备MAC"
								class="input-xlarge">
						</dd>
						<br />
						<dt>

							<label class="control-label">楼编号</label>
						</dt>
						<dd>
							<input id="building" type="text" placeholder="请输入楼编号"
								class="input-xlarge">

						</dd>
						<br />
						<dt>
							<label class="control-label">楼层</label>
						</dt>
						<dd>
							<input id="floor" type="text" placeholder="请输入设备所在楼层"
								class="input-xlarge">
						</dd>
						<br />
						<dt>

							<label class="control-label">状态</label>
						</dt>
						<dd>
							<input type="radio" value="1" data-par="1" name="inUse"
								checked="checked"> 在用 <input type="radio" value="0"
								data-par="0" name="inUse"> 停用
						</dd>
						<br />
						<dt>
							<label class="control-label" for="input01">描述</label>
						</dt>
						<dd>
							<input id="description" type="text" placeholder="请输入设备描述"
								class="input-xlarge">
						</dd>
						<br />
						<dt>
							<label class="control-label">x坐标</label>
						</dt>
						<dd>
							<input id="x" type="text" placeholder="请输入设备X坐标"
								class="input-xlarge">
						</dd>
						<br />
						<dt>
							<label class="control-label">y坐标</label>
						</dt>
						<dd>
							<input id="y" type="text" placeholder="请输入设备Y坐标"
								class="input-xlarge"> <br />
							<div
								style="width: 100%; height: 400px; float: left; margin-bottom: 10px">
								<canvas id="canvas" width="600" height="400"
									style="border: 1px #cccccc solid; float: left;">你的浏览器不支持canvas</canvas>
							</div>
						</dd>
						<br />
						<dt>
							<label class="control-label"></label>
						</dt>
						<dd>
							<div id="submit" class="controls">
								<button class="btn btn-success">修改</button>
							</div>
						</dd>
						<br />
				</div>
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
