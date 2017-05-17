<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset=utf-8 />
<title>用户注册</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="${mainpath}/css/buttons.css">
<script src="${mainpath}/js/jquery-1.11.2.min.js"></script>
<script src="${mainpath}/js/bootstrap.min.js"></script>
<script src="${mainpath}/js/utils.js"></script>
<script src="${mainpath}/js/reg.js"></script>
<style type="text/css">
.form-horizontal {
	position: absolute;
	margin-left: 35%;
	margin-top: 7%;
}

.controls {
	margin-bottom: 20px;
}

body {
	background-image: url(${mainpath}/images/background.png);
	font:
}

.btn btn-success {
	margin-left: 70%;
}
</style>
</head>
<body>
	<form class="form-horizontal" onsubmit="return false">
		<fieldset>
			<div id="legend">
				<legend>注册用户</legend>
			</div>
			<div class="control-group">
				<div class="controls">
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;省份：</strong> <select
						id="province" class="input-xlarge" style="width: 175px">
						<option>请选择省份</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市地：</strong> <select
						id="city" class="input-xlarge" style="width: 175px">
						<option>请选择市地</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<strong> &nbsp;&nbsp;&nbsp;用户名： </strong> <input
						class="input-xlarge" id="UserName" type="text"
						placeholder="请输入用户名" value="" onblur="checkUser()"><span id="User" style="font-size:small">可使用的字符为字母和数字及下划线长度为6-18位，注意不要使用空格</span>
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<strong>设置密码：</strong> <input class="input-xlarge" id="ps"
						type="password" placeholder="请输入密码" value="" onblur="Word()"><span id="Pass" style="font-size:small" >可使用的字符为字母和数字及下划线长度为6-18位，注意不要使用空格</span>
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<strong>确认密码：</strong> <input class="input-xlarge search-query"
						id="reps" type="password" placeholder="请再次输入密码" value=""
						onblur="Re_Word()"><span id="Re_Pass"></span>
				</div>

			</div>

			<div class="control-group">
				<div class="controls">
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;手机号：</strong> <input
						class="input-xlarge" id="telephone" type="text"
						placeholder="请输入手机号" value="" onblur="checkTelephoneNumber()">
					<button class="btn btn-success" id="getchecknum" onclick="getCheckNum()">获取验证码</button><span id="Telephone"></span>
				</div>
			</div>

			<div class="control-group">
				<div class="controls">
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;验证码：</strong> <input
						class="input-xlarge" type="text" placeholder="请输入验证码" value="" id="code"><span id="Code"></span>
				</div>
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"></label>
				<div class="controls">
					<button class="btn btn-success" id="submit"
						style="position: absolute; margin-left: 15%">确认提交</button>
				</div>
			</div>

		</fieldset>
	</form>

</body>
</html>
<script>
	$(document).ready(function() {
		regInit("${mainpath}");
	});
</script>