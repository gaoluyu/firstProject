$(function() {
	$("ul.main-menu li:eq(6)").addClass("active");
});

function onSearch() {
	$("#search").click(
			function() {
				var requestdata = {
					"startTime" : getStartTime(),
					"endTime" : getEndTime(),
					"network" : $("#networktypeSelect").val(),
					"buildingId" : $("#buildingSearchedId").val(),
					"floor" : $("#floorSelector").val()
				};
				ajaxUtil(requestdata, mainpath + "/neighbor/getCleanTable",
						function(response) {
							printNeighborList(response);

						}, function(response, reason) {
						});
			});
}
function printNeighborList(response) {
	var list = response.list;
	// var totalCount = response.totalCount;
	var table = $("#cellTable");
	var theHtml = "<thead><tr class='active'>"
			+ "<th>楼名</th><th>楼层</th><th>位置</th><th>网络类型</th><th>平均场强</th>"
			+ "<th>最小收集到的邻区数目</th><th>最大收集到的邻区数目</th>"
			+ "<th>平均最大邻区场强</th><th>平均最大主邻区差值</th>><th>查看场强走势</th><th>查看邻区分布</th></tr>"
			+ "</thead>";
	theHtml += "<tbody>"
	for (var i = 0; i < list.length; i++) {
		console.log(list);
		theHtml += "<tr>";
		theHtml += "<td>" + list[i].name + "</td>";
		theHtml += "<td>" + list[i].floor + "</td>";
		theHtml += "<td>" + list[i].description + "</td>";
		theHtml += "<td>" + list[i].networkType + "</td>";
		theHtml += "<td>" + list[i].avg_ss + "</td>";
		theHtml += "<td>" + list[i].min_nn + "</td>";
		theHtml += "<td>" + list[i].max_nn + "</td>";
		theHtml += "<td>" + list[i].avg_maxNS + "</td>";
		theHtml += "<td>" + list[i].avg_mainNeighborGap + "</td>";
		theHtml += "<td><a href='javascript:loadNeighborCurve(\""
				+ list[i].position + "\");'>查看走势</a></td>";
		theHtml += "<td><a href='javascript:loadNeighborHist(\""
				+ list[i].position + "\");'>查看分布</a></td>";
		theHtml += "</tr>";
	}
	theHtml += "</tbody>";
	table.html(theHtml);
}
function loadNeighborCurve(mac) {

	var requestdata = {
		"startTime" : getStartTime(),
		"endTime" : getEndTime(),
		"buildingId" : $("#buildingSearchedId").val(),
		"position" : mac,
		"network" : $("#networktypeSelect").val()
	};
	ajaxUtil(requestdata, mainpath + "/neighbor/getNeighborCurve", function(
			response) {
		drawNeighborCurve("#curve", response.list);

	}, function(response, reason) {
	});

}
function loadNeighborHist(mac) {
	var requestdata = {
		"startTime" : getStartTime(),
		"endTime" : getEndTime(),
		"buildingId" : $("#buildingSearchedId").val(),
		"position" : mac,
		"network" : $("#networktypeSelect").val()
	};
	ajaxUtil(requestdata, mainpath + "/neighbor/getNeighborHist", function(
			response) {
		drawNeighborHist("hist", response.list);

	}, function(response, reason) {
	});
}
function trigger() {
	if (getStartTime() != null && getEndTime() != null
			&& $("#floorSelector").val() != null
			&& $("#floorSelector").val().trim().length != 0
			&& $("#networkTypeSelect").val() != null
			&& $("#networkTypeSelect").val().trim().length != 0
			&& $("#buildingSearchedId").val() != null
			&& $("#buildingSearchedId").val().trim().length != 0
			&& $("#floorSelector").val() != null
			&& $("#floorSelector").val().trim().length != 0) {
		// alert("click");
		$("#search").trigger('click');
	}
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
 *            [{date,mainServingCid,neighborServingCid,distrubNeighbor}]
 */
function drawNeighborCurve(divID, list) {
	var x = [];
	var y = [ {
		'name' : '每日主区与最大邻区差值',
		'data' : []
	} ];

	for (var i = 0; i < list.length; i++) {
		x.push(list[i].date);
		var item = {
			'y' : list[i].distrubNeighbor,
			'mainCid' : list[i].mainServingCid,
			'nCid' : list[i].neighborServingCid
		};
		y[0].data.push(item);
	}
	console.log(JSON.stringify(x));
	console.log(JSON.stringify(y));
	$(function() {
		$(divID).highcharts(
				{
					title : {
						text : '每日主区与最大邻区差值',
						x : -20
					// center
					},
					xAxis : {
						categories : x
					},
					yAxis : {
						title : {
							text : '差值大小(dBm)'
						},
						plotLines : [ {
							value : 0,
							width : 1,
							color : '#808080'
						} ]
					},

					tooltip : {
						formatter : function() {
							return '<b>差值:</b>' + this.y + ', 主小区Id:'
									+ this.point.mainCid + ', 最大邻区Id:'
									+ this.point.nCid;
						}
					},
					series : y
				});
	});
}
function drawNeighborHist(divID, list) {
	var cidList = [];
	var neighborStrengthList = [];
	var _title = "";
	var _subTitle = "";
	var floor = "";
	var description = "";
	for (var i = 0; i < list.length; i++) {
		cidList.push(list[i].cid);
		neighborStrengthList.push(list[i].neighborStrength);
		floor = list[i].floor;
		description = list[i].description;
	}
	_title = floor + "层监测到的所有邻区"
	_subTitle = "位置：" + description
	$(function() {
		// Set up the chart
		var chart = new Highcharts.Chart({
			chart : {
				renderTo : divID,
				type : 'column',
				margin : 75,
				options3d : {
					enabled : true,
					alpha : 15,
					beta : 15,
					depth : 50,
					viewDistance : 25
				}
			},
			title : {
				text : _title
			},
			subtitle : {
				text : _subTitle
			},
			plotOptions : {
				column : {
					depth : 25
				}
			},
			xAxis : {
				categories : cidList

			},
			yAxis : {
				title : {
					text : '场强 (dBm)'
				}
			},
			series : [ {
				name : '邻区场强',
				data : neighborStrengthList
			} ]
		});
	});
}