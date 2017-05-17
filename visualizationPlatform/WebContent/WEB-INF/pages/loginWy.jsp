<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" charset=utf-8 " />
<title>登录系统</title>
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/login.js"></script>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css" />

<style type="text/css">
.form-signin {
	max-width: 330px;
}
.login {
   width:500px;
   hight:500px;
	padding: 30px;
	margin: auto;
	position: absolute;
	left: 35%;
	top: 15%;
/* 	background-color:rgba(21, 116, 171, 0.4) ; */
	
}

.btn-block {
	width: 50px;
}
.checkbox {
	margin-left: 20px
}
.form-signin-heading {
	text-align: center;
}
.form-control{
margin-top:2px;

/* background-image:url('http://www.poluoluo.com/jzxy/UploadFiles_333/201107/20110718211004938.gif'); */
/* 	background-repeat:no-repeat; */
/* 	padding-left:20px; */
}
.btn{
width:130px;
}
body {
	background-image: url(${mainpath}/images/wuYe.png) ;background-repeat:no-repeat;background-size: 100%;
	
}
#envelope{
margin-top:200px;
margin-left:100px
}
#welcome {
 color:white;
 font-size:40px;
 text-align：center;
 font-weight: normal;
}
/* .text{border:solid 2px #ccc;width:400px;height:40px;background:url(http://d.lanrentuku.com/down/png/1211/blueberry/user_friend.png) no-repeat 0 center;} */
/* .text input{float:left; border:none;background:none;height:40px;line-height:30px;width:100%; text-indent:32px;} */
</style>
</head>
<body>
<%-- 	<img src="${mainpath}/images/title1.png" --%>
<!-- 		style="position: absolute; left: 5%; top: 10%"> -->
	<div class="login">
		<form class="form-signin" role="form" name="form1" onSubmit="return false">
			<h2 class="form-signin-heading" id="welcome">欢迎登录</h2>
<!-- 	<span class="glyphicon glyphicon-envelope" aria-hidden="true" id=“envelope“></span> -->
			<input type="text" class="form-control " placeholder="用户名"
				name="usernm" reqired autofocus ></br>
				
				</input>
				<i></i>
			<input type="password"
				class="form-control" placeholder="密码" name="userpwd"
				onclick="" / reqired /> <label class="checkbox"> 
			</label>

		</form>
		
		<button class="btn btn-warning" onClick="loginWuye()" >登 录</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		

		<button class="btn btn-warning" onClick="newDoc()">注 册</button>
	</div>
</body>
</html>
<script>
	$(document).ready(function() {
		loginInit("${mainpath}");
	});
</script>