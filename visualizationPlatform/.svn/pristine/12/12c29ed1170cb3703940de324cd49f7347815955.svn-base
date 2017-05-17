<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8" />
<title>用户信息管理</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" href="${mainpath}/css/default.css"
	type="text/CSS" />
<c:if test="${sessionScope.loginedUser.role==3}">
	<link rel="stylesheet" href="${mainpath}/css/green.css" />
</c:if>
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/userAdmin.js"></script>
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
						<legend class="">用户信息</legend>
					</div>
					<dl class="dl-horizontal" id="userInfo">
					</dl>
					<div id="legend" class="">
						<legend class="">信息管理</legend>
					</div>
					<div class="container">
						<div class="row clearfix">
							<div class="col-md-12 column">
								<a id="modal-370154" href="#modal-container-370154"
									role="button" class="btn btn-info" data-toggle="modal"
									style="margin: 10 10 10 100">修改密码</a>

								<div class="modal fade" id="modal-container-370154"
									role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-hidden="true">
													<i class="glyphicon glyphicon-remove"></i>
												</button>
												<h4 class="modal-title" id="myModalLabel">修改密码</h4>
											</div>
											<div class="modal-body">
												<dl class="dl-horizontal">
													<dt>
														<strong>原密码：</strong>
													</dt>
													<dd>
														<input class="input-xlarge" id="oldpassword"
															type="password" placeholder="请输入密码" value="" onblur="">
													</dd>
													<br />
													<dt>
														<strong>新密码：</strong>
													</dt>
													<dd>
														<input class="input-xlarge" id="ps" type="password"
															placeholder="请输入密码" value="" onblur="Word()"> <span
															id="Pass"></span>
													</dd>
													<br />
													<dt>
														<strong>确认密码：</strong>
													</dt>
													<dd>
														<input class="input-xlarge search-query" id="reps"
															type="password" placeholder="请再次输入密码" value=""
															onblur="Re_Word()"> <span id="Re_Pass"></span>
													</dd>
												</dl>
												<div class="modal-footer">
													<button type="button" class="btn btn-default" id="submit"
														onclick="submit()">提交</button>
													<button type="button" class="btn btn-primary"
														data-dismiss="modal" aria-hidden="true">取消</button>
												</div>
											</div>

										</div>

									</div>

								</div>
							</div>
						</div>
					</div>
					<!--  <div id="legend" class="">
						<legend class="">告警阈值</legend>
					</div>
					<dl class="dl-horizontal" id="warningValueBox">
					</dl>
					<button style="margin:10 10 10 120"  onclick="motify()" class="btn btn-info" type="button">修改告警阈值</button>
					<div id="legend" class="">
						<legend class="">覆盖率指标</legend>
					</div>
					<dl class="dl-horizontal" id="CoverageList">
					</dl>
					<button style="margin:10 10 10 120"  onclick="motify()" class="btn btn-info" type="button">修改覆盖率指标</button>
					<div id="legend" class="">
						<legend class="">信号质量等级指标</legend>
					</div>
					<table class="table" id="NetworkList">
					
					</table>
					<button style="margin:10 10 10 120"  onclick="motify()" class="btn btn-info" type="button">修改信号等级指标</button>
-->
				</div>
			</div>
		</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		init("${mainpath}");
		loadUserInfo();
		reportInit("${sessionScope.loginedUser.role}");
	});
</script>
</html>
