<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row-fluid">
	<div class="span12">
		<div class="navbar navbar-bg">
			<div class="navbar-inner">
				<img src="${mainpath}/images/logo.gif" class="nav nav-logo"></img>

				<c:choose>
					<c:when test="${sessionScope.loginedUser.role==3}">
						<span class="nav-title" href="#">巡检管理系统</span>

					</c:when>
					<c:otherwise>
						<span class="nav-title" href="#">智慧室分大数据测评与预警平台</span>
					</c:otherwise>
				</c:choose>


				<div class="btn-group pull-right" id="right"
					style="margin-top: 10px">
					<button class="btn btn-default dropdown-toggle"
						data-toggle="dropdown">
						<i class="glyphicon glyphicon-user"></i><span> <c:if
								test="${sessionScope.loginedUser.role==-1}">
								<span>管理员</span>
							</c:if>
						</span> <span>${sessionScope.loginedUser.username}</span> <span
							class="caret"></span>
					</button>
					<ul class="dropdown-menu ">
						<c:choose>
							<c:when test="${sessionScope.loginedUser.role==3}">
								<li><a href="${mainpath}/page/userAdminWy"><i
										class="glyphicon glyphicon-user"></i> 账户管理</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${mainpath}/page/userAdmin"><i
										class="glyphicon glyphicon-user"></i> 账户管理</a></li>
							</c:otherwise>
						</c:choose>

						<li class="divider"></li>
						<li><a href="${mainpath}/page/logout"><i
								class="glyphicon glyphicon-share"></i> 退出系统</a></li>
					</ul>
				</div>
				<c:if test="${sessionScope.loginedUser.role!=3}">
					<div class="pull-right"
						style="position: relative; margin-top: 10px">
						<a href="${mainpath}/page/message" class="btn btn-default "
							style="margin-right: 10px"> <i
							class="glyphicon glyphicon-envelope red"></i> <span> 消息</span> <span
							class="notification red" id="userMessageNumber"></span></a>
					</div>
				</c:if>
				<span class="pull-right navbar-text"
					style="margin-top: 10px; color: #ffffff"><i
					class="glyphicon glyphicon-map-marker"></i>
					${sessionScope.loginedUser.province}${sessionScope.loginedUser.city}
					<c:choose>
<<<<<<< .mine

						<c:when test='${sessionScope.loginedUser.network_mnc=="\'03\'"}'>电信</c:when>
						<c:when test='${sessionScope.loginedUser.network_mnc=="\'01\'"}'>联通  </c:when>

||||||| .r444
						<c:when test="${sessionScope.loginedUser.network_mnc=='\"03\"'}">电信</c:when>
						<c:when test="${sessionScope.loginedUser.network_mnc=='\"01\"'}">联通  </c:when>
=======
						<c:when test="${sessionScope.loginedUser.network_mnc=='\"03\"'}">电信</c:when>
						<c:when
							test="${sessionScope.loginedUser.network_mnc=='\"00\",\"02\"'}">移动 </c:when>
						<c:when test="${sessionScope.loginedUser.network_mnc=='\"01\"'}">联通  </c:when>
>>>>>>> .r459
						<c:otherwise>全制式 </c:otherwise>
					</c:choose> </span>
			</div>
			<script type="text/javascript">
				function getMessageCount() {
					ajaxUtil({}, "${mainpath}/userAdmin/messageNumber",
							function(response) {
								$("#userMessageNumber").html(response.count);
							}, function(response, reason) {
							});
				}
				getMessageCount();
				setInterval("getMessageCount()", 60 * 1000);//1min
			</script>
		</div>
	</div>
</div>