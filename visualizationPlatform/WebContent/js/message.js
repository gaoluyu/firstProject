var mainpath;
function reportInit(role) {
	if (role == -1) {
		$("ul.main-menu li:eq(18)").addClass("active");
	} else {
		$("ul.main-menu li:eq(12)").addClass("active");
	}
}
function init(_mainpath) {
	mainpath = _mainpath;
	// loadMessageList();
	printMessageList("#messagelist", "", "", 1, 10);
}
/** 日期格式转换函数* */
Date.prototype.toCommonCase = function() {
	var xYear = this.getYear();
	xYear = xYear + 1900;

	var xMonth = this.getMonth() + 1;
	if (xMonth < 10) {
		xMonth = "0" + xMonth;
	}

	var xDay = this.getDate();
	if (xDay < 10) {
		xDay = "0" + xDay;
	}
	return xYear + "-" + xMonth + "-" + xDay;
}
function printMessageList(tableId, url, data, pageNo, pageSize) {
	ajaxUtil(
			{
				"startPage" : pageNo,
				"pageSize" : pageSize
			},
			mainpath + "/userAdmin/messageList",
			function(response) {
				console.log(response);
				var prehtml_nosolve = "<td style='color:#d9534f'><span class='glyphicon glyphicon-folder-close'></span> 未处理</td>";
				var prehtml_issolve = "<td style='color:#269926'><span class='glyphicon glyphicon-folder-open'></span> 已处理</td>";
				var prehtml_isconfirm = "<td style='color:#FF8200'><span class='glyphicon glyphicon-folder-open'></span> 已确认</td>";
				console.log(prehtml_nosolve);

				var list = response.list;
				var totalCount = response.totalCount;
				var warningValue = response.warningValue;

				var table = $(tableId);
				var html = "<thead>"
						+ "<tr class='active'><th>状态</th><th>主题</th><th>时间</th><th>告警类型</th></tr></thead><tbody>";
				for (var i = 0; i < list.length; i++) {
					var cid = list[i].cid;
					var startTime = list[i].statrTime;
					var endTime = list[i].endTime;
					var id = list[i].id;
					var netType = list[i].netType;
					var operator = list[i].operator;
					var value = list[i].value;
					var isRead = list[i].isRead;
					var network = operator + netType;// eg: 联通 + 3G
					var wv = null;// 警告值
					var warningType = list[i].timeType;

					if (warningType == 'standard') {
						for (var j = 0; j < warningValue.length; j++) {
							if (warningValue[j].netType == network) {
								wv = warningValue[j].warningValue
							}
						}
						warningType = "低于运营商标准";
					} else {
						wv = list[i].fragmentValue;
						warningType = "低于历史15天平均值";
					}
					/** 时间转换* */
					var comStartTime = new Date(startTime);
					comStartTime = comStartTime.toCommonCase();
					var comEndTime = new Date(endTime);
					comEndTime = comEndTime.toCommonCase();
					// console.log("从"+comStartTime+"到"+comEndTime);
					if (isRead == "未处理") {
						// 消息未处理
						html += "<tr style='font-weight:bold' onclick='javascript:printDetailMessage("
								+ id + ")'> " + prehtml_nosolve;
					} else if (isRead == "已确认") {
						// 消息已确认
						html += "<tr onclick='javascript:printDetailMessage("
								+ id + ")'> " + prehtml_isconfirm;
					} else if (isRead == "已处理") {
						// 消息已处理
						html += "<tr onclick='javascript:printDetailMessage("
								+ id + ")'> " + prehtml_issolve;
					}
					html += "<td>" + operator + netType + "电场强度为" + value
							+ "dBm" + "，低于告警阈值" + wv + "dBm</td>" + "<td>从"
							+ comStartTime + "到" + comEndTime + "</td><td>"
							+ warningType + "</td>" + "</tr>";
				}
				html += "</tbody>";
				table.html(html);
				var totalPage = Math.ceil(totalCount / pageSize);
				printPage(tableId, document.getElementById("paging"),
						totalPage, url, data, pageNo, pageSize,
						"printMessageList");
				setIcRank();
			}, function(response, reason) {
				alert(response.msg);
			});

}
function printDetailMessage(id) {
	window.location.href = mainpath + "/page/detailMessage?id=" + id;
	sessionStorage.id = id;
}
