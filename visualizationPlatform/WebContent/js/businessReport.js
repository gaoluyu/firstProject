var warningValue;
function reportInit(role) {
	if (role == -1) {
		$("ul.main-menu li:eq(12)").addClass("active");
	} else {
		$("ul.main-menu li:eq(7)").addClass("active");
	}
	loadRegion("#region");
	$("#search").click();
}
function loadRegion(selectId) {
	ajaxUtil({}, mainpath + "/re/loadWPRegion", function(response) {
		console.log(response.url);
		var list = response.list;
		var theHtml = "<option value=\"all_region\">全部区域</option>";
		for (var i = 0; i < list.length; i++) {
			if (list[i] != null)
				theHtml += "<option value=\"" + list[i] + "\">"
						+ list[i] + "</option>";
		}
		$(selectId).html(theHtml);
	}, function(response, reason) {
	});
}
function onSearch() {
	$("#search").click(function() {
		printBusinessList("#businessList", "#caption", "", "", 1, 100);
	});

	$("#export").click(function() {
		downloadReport();
	});
}
function downloadReport() {
	ajaxUtil({
		"startTime" : getStartTime(),
		"endTime" : getEndTime(),
		"startPage" : 1,
		"pageSize" : 100,
		"network" : $("#network").val(),
		"region" : $("#region").val()
	}, mainpath + "/re/downBusinessReport", function(response) {
		console.log(response.url);
		var url = response.url;
		window.location.href = url;
	}, function(response, reason) {
	});

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

/** 打印业务报表* */
function printBusinessList(tableId, captionId, url, data, pageNo, pageSize) {
	ajaxUtil({
		"startTime" : getStartTime(),
		"endTime" : getEndTime(),
		"startPage" : pageNo,
		"pageSize" : pageSize,
		"network" : $("#network").val(),
		"region" : $("#region").val()
	}, mainpath + "/re/businessReport", function(response) {
		console.log(response);
		var businessReport = response.businessReport;
		var totalCount = response.total;
		var province = response.province;
		var city = response.city;
		var startTime = response.startTime;
		var comStartTime = new Date(startTime);
		comStartTime = comStartTime.toCommonCase();
		var endTime = response.endTime;
		var comEndTime = new Date(endTime);
		comEndTime = comEndTime.toCommonCase();
		var table = $(tableId);
		var caption = $(captionId);
		var theHtml = "<thead>"
				+ "<tr class='active'><th>小区CI</th><th>LAC</th><th>频段</th>"
				+ "<th>小区中文名</th><th>小区英文名</th><th>小区地址</th>"
				+ "<th>隶属</th><th>覆盖场景</th><th>区域"
				+ "</th><th>eNB</th><th>经度</th><th>纬度</th><th>信号强度（dBm）</th>"
				+ "<th>平均RSRP</th><th>平均RSRQ</th><th>平均SINR</th><th>网络类型</th>"
				+ "</tr></thead>";
		theHtml += "<tbody>";
		var theCaption = "<thead>" + "<tr ><th> <th>省份:</th>" + "<th>"
				+ province + "&nbsp;&nbsp;</th>" + "<th>市地:</th>" + "<th>"
				+ city + "&nbsp;&nbsp;</th>" + "<th>开始时间:</th>" + "<th>"
				+ comStartTime + "&nbsp;&nbsp;</th>" + "<th>结束时间:</th>"
				+ "<th>" + comEndTime + "&nbsp;&nbsp;</th>" + "</tr></thead>";
		caption.html(theCaption);

		for (var i = 0; i < businessReport.length; i++) {
			theHtml += "<tr >";
			theHtml += "<td style='min-width:70px'>" + businessReport[i].ci
					+ "</td>";
			theHtml += "<td style='min-width:70px'>" + businessReport[i].lac
					+ "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].freqChannel + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].cellNameCh + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].cellNameEng + "</td>";
			theHtml += "<td style='min-width:200px'>"
					+ businessReport[i].cellAddress + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].belonging + "</td>";
			theHtml += "<td style='min-width:100px'>" + businessReport[i].scene
					+ "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].region + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].enodeb + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].longitude + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].latitude + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].avg_strength + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].avg_rsrp + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].avg_rsrq + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].avg_sinr + "</td>";
			theHtml += "<td style='min-width:100px'>"
					+ businessReport[i].network + "</td>";
			theHtml += "</tr>";
		}
		theHtml += "</tbody>";
		table.html(theHtml);
		var totalPage = Math.ceil(totalCount / pageSize);
		printPage(tableId, document.getElementById("paging"), totalPage, url,
				data, pageNo, pageSize, "printBusinessList");
		setIcRank();
	}, function(response, reason) {
	});

}