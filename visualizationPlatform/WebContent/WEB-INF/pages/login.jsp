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
	padding: 30px;
	margin: auto;
	position: absolute;
	left: 65%;
	top: 30%;
	background-color:rgba(21, 116, 171, 0.4) ;
	
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
}
.btn{
width:100px
}
body {
	background-image: url(${mainpath}/images/newTitle.png) ;background-repeat:no-repeat;background-size: 100%;
	
}
</style>
</head>
<body>
	<img src="${mainpath}/images/title1.png"
		style="position: absolute; left: 5%; top: 10%">
	<div class="login">
		<form class="form-signin" role="form" name="form1" onSubmit="return false">
			<h2 class="form-signin-heading">欢迎登录</h2>
			<input type="text" class="form-control" placeholder="User name"
				name="usernm" reqired autofocus /><input type="password"
				class="form-control" placeholder="Password" name="userpwd"
				onclick="" / reqired /> <label class="checkbox"> 
			</label>

		</form>
		
		<button class="btn btn-warning" onClick="login()" >登 录</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		<button class="btn btn-warning" onClick="newDoc()">注 册</button>
	</div>
</body>
</html>
<script>
	$(document).ready(function() {
		loginInit("${mainpath}");
	});
</script>