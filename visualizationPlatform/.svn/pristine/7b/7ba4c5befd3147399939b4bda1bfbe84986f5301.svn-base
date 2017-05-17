var mainpath;

function loginInit(_mainpath) {
	mainpath = _mainpath;
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			login();
		}
	};
}
function check() {
	if (form1.usernm.value == "") {
		alert("错误提示：用户名不能为空！");
		return false;
	} else if (form1.userpwd.value == "") {
		alert("错误提示：密码不能为空！");
		return false;
	}
	return true;
}
function login() {
	if (!!check()) {
		var userName = form1.usernm.value;
		var userPass = form1.userpwd.value;
		ajaxUtil({
			"username" : userName,
			"password" : userPass
		}, mainpath + "/lr/login", function(response) {
			window.location.href = mainpath + "/page/outdoor";
		}, function(response, reason) {
			alert(reason);
		});
	}
}
function loginWuye() {
	if (!!check()) {
		var userName = form1.usernm.value;
		var userPass = form1.userpwd.value;
		ajaxUtil({
			"username" : userName,
			"password" : userPass
		}, mainpath + "/lr/loginWy", function(response) {
			window.location.href = mainpath + "/page/inspectAdminWy";
		}, function(response, reason) {
			alert(reason);
		});
	}
}
function newDoc() {
	window.location.href = "./reg"
}