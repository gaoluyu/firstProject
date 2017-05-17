var warningValue;
$(function() {
	$("ul.main-menu li:eq(3)").addClass("active");
});
function onSearch() {
	$("#search").click(function() {
		// 计数
		ajaxUtil({
			"floor" : $("#floorSelector").val(),
			"building" : getCiOrBuildingId("buildingId"),
			"network" : getCiOrBuildingId("network")
		}, mainpath + "/indoor/totalBeacon", function(response) {
			var totalNumber = response.totalCount;
			$("#totalCountId").html(totalNumber);
			// 柱状图
			/*
			 * ajaxUtil({ "startTime" : getStartTime(), "endTime" :
			 * getEndTime(), "floor" : $("#floorSelector").val(), "building" :
			 * getCiOrBuildingId("buildingId"), "ci" : getCiOrBuildingId("ci"),
			 * "network" : getCiOrBuildingId("network"), "startPage" : 1,
			 * "pageSize" : totalNumber }, mainpath + "/indoor/indoorList",
			 * function(response) { drawHist("#hist", response.list); },
			 * function(response, reason) { });
			 */
		}, function(response, reason) {
		});
		// 表格
		printIndoorList("#indoorList", "", "", 1, 100);
	});
}
function trigger() {
	// alert("trigger");
	if (getStartTime() != null && getEndTime() != null
			&& $("#floorSelector").val() != null
			&& $("#floorSelector").val().trim().length != 0
			&& getCiOrBuildingId("buildingId") != null
			&& getCiOrBuildingId("buildingId").trim().length != 0
			&& getCiOrBuildingId("network") != null
			&& getCiOrBuildingId("network").trim().length != 0
			&& getCiOrBuildingId("ci") != null
			&& getCiOrBuildingId("ci").trim().length != 0) {
		// alert("click");
		$("#search").trigger('click');
	}
}
function loadBeaconCurve(beaconId) {
	ajaxUtil({
		"startTime" : getStartTime(),
		"endTime" : getEndTime(),
		"beaconId" : beaconId,
		"network" : getCiOrBuildingId("network"),
		"ci" : getCiOrBuildingId("ci")
	}, mainpath + "/indoor/beaconCurve", function(response) {
		drawCurve("#curve", response.list);
	}, function(response, reason) {
	});
}
function printIndoorList(tableId, url, data, pageNo, pageSize) {
	ajaxUtil({
		"startTime" : getStartTime(),
		"endTime" : getEndTime(),
		"floor" : $("#floorSelector").val(),
		"building" : getCiOrBuildingId("buildingId"),
		"network" : getCiOrBuildingId("network"),
		"ci" : getCiOrBuildingId("ci"),
		"startPage" : pageNo,
		"pageSize" : pageSize
	}, mainpath + "/indoor/indoorList", function(response) {
		var list = response.list;
		var totalCount = response.totalCount;
		var warningBottomlist = response.warningBottomlist;
		var green_bottom = warningBottomlist[0].green_bottom;
		var blue_bottom = warningBottomlist[0].blue_bottom;
		var yellow_bottom = warningBottomlist[0].yellow_bottom;
		var red_bottom = warningBottomlist[0].red_bottom;
		var table = $(tableId);
		var theHtml = "<thead>"
				+ "<tr class='active'><th>楼编号</th><th>楼层</th><th>描述</th>"
				+ "<th>平均场强（dBm）</th><th>最大场强(dBm)</th><th>最小场强(dBm)"
				+ "</th><th>查看场强走势</th></tr></thead>";
		theHtml += "<tbody>"
		for (var i = 0; i < list.length; i++) {
			var style = null;
			var avers = list[i].averageStrength;
			if (avers < red_bottom) {
				style = "active";// 灰色
			} else if (avers > red_bottom && avers < yellow_bottom) {
				style = "danger";// 红色
			} else if (avers > yellow_bottom && avers < blue_bottom) {
				style = "warning";// 黄色
			} else if (avers > blue_bottom && avers < green_bottom) {
				style = "info";// 蓝色
			} else if (avers > green_bottom && avers < 0) {
				style = "success";// 绿色
			} else {
				style = "default";// 无色
			}
			theHtml += "<tr class='" + style + "'>";
			theHtml += "<td>" + list[i].building + "</td>";
			theHtml += "<td>" + list[i].floor + "</td>";
			theHtml += "<td>" + list[i].description + "</td>";
			theHtml += "<td>" + list[i].averageStrength + "</td>";
			theHtml += "<td>" + list[i].maxRssi + "</td>";
			theHtml += "<td>" + list[i].minRssi + "</td>";
			theHtml += "<td><a href='javascript:loadBeaconCurve(\""
					+ list[i].position + "\");'>查看走势</a></td>";
			theHtml += "</tr>";
		}
		theHtml += "</tbody>";
		table.html(theHtml);
		var totalPage = Math.ceil(totalCount / pageSize);
		printPage(tableId, document.getElementById("paging"), totalPage, url,
				data, pageNo, pageSize, "printIndoorList");
		setIcRank();
	}, function(response, reason) {
	});

}

/**
 * @param x [
 *            'Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas' ]
 * @param y [ {
 *            name : 'John', data : [ 5, 3, 4, 7, 2 ], stack : 'male' }, { name :
 *            'Joe', data : [ 3, 4, 4, 2, 5 ], stack : 'male' }, { name :
 *            'Jane', data : [ 2, 5, 6, 2, 1 ], stack : 'female' }, { name :
 *            'Janet', data : [ 3, 0, 4, 4, 3 ], stack : 'female' } ]
 * 
 * @param divID
 * @returns
 */
function drawHist(divID, list) {
	var x = [];
	var y = [ {
		'name' : '平均场强',
		'data' : []
	}, {
		'name' : '最大场强',
		'data' : []
	}, {
		'name' : '最小场强',
		'data' : []
	} ];

	for (var i = 0; i < list.length; i++) {
		x.push(list[i].position);
		y[0].data.push(list[i].averageStrength);
		y[1].data.push(list[i].maxRssi);
		y[2].data.push(list[i].minRssi);
	}

	$(function() {
		$(divID).highcharts({
			chart : {
				type : 'bar',
				marginTop : 80,
				marginRight : 40
			},
			title : {
				text : '定位点场强'
			},
			plotOptions : {
				series : {
					stacking : 'normal'
				}
			},
			xAxis : {
				categories : x
			},
			yAxis : {
				title : {
					text : '场强(dBm)'
				}
			},
			plotOptions : {
				column : {
					stacking : 'normal',
				}
			},
			series : y
		});
	});
}
/**
 * @param x
 *            ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep',
 *            'Oct', 'Nov', 'Dec']
 * @param y [{
 *            name: 'Tokyo', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5,
 *            23.3, 18.3, 13.9, 9.6] }, { name: 'New York', data: [-0.2, 0.8,
 *            5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5] }, {
 *            name: 'Berlin', data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6,
 *            17.9, 14.3, 9.0, 3.9, 1.0] }, { name: 'London', data: [3.9, 4.2,
 *            5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8] }]
 * @param divID
 * @param list
 */
function drawCurve(divID, list) {
	var x = [];
	var y = [ {
		'name' : '平均场强',
		'data' : []
	}, {
		'name' : '最大场强',
		'data' : []
	}, {
		'name' : '最小场强',
		'data' : []
	} ];

	for (var i = 0; i < list.length; i++) {
		x.push(list[i].date);
		y[0].data.push(list[i].averageStrength);
		y[1].data.push(list[i].maxRssi);
		y[2].data.push(list[i].minRssi);
	}
	console.log(JSON.stringify(x));
	console.log(JSON.stringify(y));
	$(function() {
		$(divID).highcharts({
			title : {
				text : '场强趋势',
				x : -20
			// center
			},
			xAxis : {
				categories : x
			},
			yAxis : {
				title : {
					text : '场强大小(dBm)'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			tooltip : {
				valueSuffix : 'dBm'
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
				borderWidth : 0
			},
			series : y
		});

	});

}