var mainpath, id, position;
$(function() {
	$("ul.main-menu li:eq(13)").addClass("active");
});
function init(_mainpath) {
	mainpath = _mainpath;
	id = sessionStorage.id;
	printDetailMessage("#detailmessage", id);
	$("#confirm").click(function() {
		console.log("lalala");
		MesConfirm("#confirm", id);
	});

}
function check(telephone) {
	var reg = /^\d{11}$/;
	if (telephone != null) {
		if (reg.test(telephone)) {
			$("#telephone").append(
					"<span class='glyphicon glyphicon-ok'></span>");
			$("#telephone").css("border-color", "green");
			return true;
		} else {
			$("#telephone").append(
					"<span class='glyphicon glyphicon-ok'></span>");
			$("#telephone").css("border-color", "red");
			return false;
		}
	} else {
		return false;
	}
}
// 确认消息
function MesConfirm(confirmId, id) {
	console.log(confirmId + " " + id);
	ajaxUtil({
		"id" : id
	}, mainpath + "/userAdmin/MesConfirm", function(response) {
		console.log("mesconfirm" + response.msg);
		$(confirmId).html("已确认");
		console.log($(confirmId));
		$(confirmId).attr("disabled", true);
	}, function(response, reason) {
		alert(response.msg);
		if (response.msg == "确认告警消息状态成功") {
			$(confirmId).html("已确认");
			$(confirmId).attr("disabled", true);
		} else {
			$(confirmId).html("确认");
			$(confirmId).removeAttr("disabled");
		}
	})
}
// 派发告警
function MesHandle(place, netType, startTime, strength) {
	var telephone = $("#telephone").val();
	strength = strength.toString();
	if (check(telephone)) {
		ajaxUtil({
			"id" : id,
			"telephone" : telephone,
			"position" : place,
			"netType" : netType,
			"date" : startTime,
			"strength" : strength.toString()
		}, mainpath + "/userAdmin/MesHandle", function(response) {
			if (response.msg == "处理告警消息状态成功") {
				$("#alarm").html("已派发");
				$("#alarm").attr("disabled", true);
				$("#confirm").html("已确认");
				$("#confirm").attr("disabled", true);
			}
		}, function(response, reason) {
			alert(response.msg);
		})
	} else {
		alert("请输入正确的手机号");
	}
}

function printDetailMessage(tableId, id) {
	ajaxUtil({
		"id" : id
	}, mainpath + "/userAdmin/messageDetail",
			function(response) {
				console.log(response.list);
				var list = response.list;
				var totalCount = list.length;
				var startTime = list[0].startTime;
				var endTime = list[0].endTime;
				var province = list[0].province;
				var city = list[0].city;
				var region = list[0].region;
				var name = list[0].name;
				var floor = list[0].floor;
				var description = list[0].description;
				var position = list[0].position;
				var operator = list[0].operator;
				var netType = list[0].netType;
				var cid = list[0].cid;
				var floor = list[0].floor;
				var x = list[0].x;
				var y = list[0].y;
				var value = list[0].value;
				var isRead = list[0].isRead;
				var wv = 0;
				var warningType = list[0].timeType;
				if (warningType == 'standard') {
					warningType = "低于运营商标准";
				} else {
					wv = list[0].fragmentValue;
					warningType = "低于历史15天平均值";
				}
				var place = province + city + region + name + floor + "层"
						+ description;
				var table = $(tableId);
				var theHtml = "<dt>告警地点:</dt><dd>" + province + "|" + city
						+ "|" + region + "|" + name + "|" + floor + "层"
						+ description + "</dd><br/>";
				theHtml += "<dt>告警设备:</dt><dd>MAC地址(" + position + ")|室内坐标("
						+ x + "," + y + ")</dd><br/>";
				theHtml += "<dt>小区类型:</dt><dd>" + operator + "|" + netType
						+ "|小区CI(" + cid + ")</dd><br/>";
				theHtml += "<dt>告警场强:</dt><dd>" + value + "dBm</dd><br/>";
				theHtml += "<dt>告警类型:</dt><dd>" + warningType + " :" + wv+
				"dBm</dd>";
				table.html(theHtml);
				if (isRead == "未处理") {
					$("#alarm").html("派发告警");
					$("#confirm").html("确认");
				} else if (isRead == "已确认") {
					$("#alarm").html("派发告警");
					$("#confirm").html("已确认");
					$("#confirm").attr("disabled", true);
				} else if (isRead == "已处理") {
					$("#confirm").html("已确认");
					$("#confirm").attr("disabled", true);
					$("#alarm").html("已处理");
					$("#alarm").attr("disabled", true);
				}
				// 为“派发告警”按钮绑定点击事件
				$("#alarm").click(function() {
					MesHandle(place, netType, startTime, value);
				});
			}, function(response, reason) {
				alert(reason);
			});
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
}
