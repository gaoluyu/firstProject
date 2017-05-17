var buildingMapList = [];
$(function() {

	$("ul.main-menu li:eq(2)").addClass("active");
});
function onSearch() {
	$("#search").click(function() {
		ajaxUtil({
			"startTime" : getStartTime(),
			"endTime" : getEndTime(),
			"floor" : $("#floorSelector").val(),
			"building" : getCiOrBuildingId("buildingId"),
			"network" : getCiOrBuildingId("network"),
			"ci" : getCiOrBuildingId("ci")
		}, mainpath + "/indoor/indoorMap", function(response) {
			var warningBottomlist = response.warningBottomlist;
			draw_Map(response.list, warningBottomlist);
		}, function(response, reason) {
		});
		printIndoorList("#indoorList", "", "", 1, 100);// 打印表格
	});
}
function trigger() {
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
function loadFloorMap(selectorId, mapId) {
	$("#alertID").show();
	var floor = $(selectorId).val();
	var find = false;
	var url;
	for (var i = 0; i < buildingMapList.length; i++) {
		var item = buildingMapList[i];
		if (item.floor == floor) {
			find = true;
			url = item.url;
			break;
		}
	}
	if (find) {
		var canvas = $(mapId);
		if (canvas == null)
			return false;
		canvas.css("backgroundImage", "url('/buildingMap/" + url + "')");
		canvas.css("backgroundSize", "100% 100%");// 使图片适配容器大小

	} else {
		ajaxUtil({
			"buildingId" : getCiOrBuildingId("buildingId"),
			"floor" : 0
		}, mainpath + "/indoor/mapUrl", function(response) {
			// update buildingMapList
			if (response.list != null && response.list.length > 0) {
				var _find = false;
				for (var i = 0; i < response.list.length; i++) {
					var item = response.list[i];
					if (item.floor == floor) {
						_find = true;
						break;
					}
				}
				if (_find) {
					buildingMapList = response.list;
					loadFloorMap("#floorSelector", "#canvas");
					return;
				}
			}
			alert("改层尚未上传室内地图");
		}, function(response, reason) {
			alert(reason);
		});

	}
}
function printIndoorList(tableId, url, data, pageNo, pageSize) {
	ajaxUtil(
			{
				"startTime" : getStartTime(),
				"endTime" : getEndTime(),
				"floor" : $("#floorSelector").val(),
				"building" : getCiOrBuildingId("buildingId"),
				"ci" : getCiOrBuildingId("ci"),
				"network" : getCiOrBuildingId("network"),
				"startPage" : pageNo,
				"pageSize" : pageSize
			},
			mainpath + "/indoor/indoorList",
			function(response) {
				var list = response.list;
				var totalCount = response.totalCount;
				console.log(response);
				var warningBottomlist = response.warningBottomlist;
				var green_bottom = warningBottomlist[0].green_bottom;
				var blue_bottom = warningBottomlist[0].blue_bottom;
				var yellow_bottom = warningBottomlist[0].yellow_bottom;
				var red_bottom = warningBottomlist[0].red_bottom;
				var table = $(tableId);
				var theHtml = "<thead >"
						+ "<tr class='active'><th>楼编号</th><th>楼层</th><th>描述</th><th>MAC</th>"
						+ "<th>平均场强</th><th>最大场强</th><th>最小场强"
						+ "</th><th>覆盖率</th></tr></thead>";
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
					theHtml += "<td>" + list[i].position + "</td>";
					theHtml += "<td>" + list[i].averageStrength + "dBm</td>";
					theHtml += "<td>" + list[i].maxRssi + "dBm</td>";
					theHtml += "<td>" + list[i].minRssi + "dBm</td>";
					theHtml += "<td>" + list[i].ratio * 100 + "%</td>";
					theHtml += "</tr>";
				}
				theHtml += "</tbody>";
				table.html(theHtml);
				var Html = '<tr class="active"><td><img src="/androidServer/images/gray.png"></img></td><td><'
						+ red_bottom
						+ 'dBm</td></tr>'
						+ '<tr class="danger"><td><img src="/androidServer/images/red.png"></img></td><td>'
						+ red_bottom
						+ '~'
						+ yellow_bottom
						+ 'dBm</td></tr>'
						+ '<tr class="warning"><td><img src="/androidServer/images/yellow.png"></img></td><td>'
						+ blue_bottom
						+ '~'
						+ yellow_bottom
						+ 'dBm</td></tr>'
						+ '<tr class="info"><td><img src="/androidServer/images/blue.png"></img></td><td>'
						+ blue_bottom
						+ '~'
						+ yellow_bottom
						+ 'dBm</td></tr>'
						+ '<tr class="success"><td><img src="/androidServer/images/green.png"></img></td><td>>'
						+ green_bottom
						+ 'dBm</td></tr>'
						+ '<tr class="default"><td><img src="/androidServer/images/white.png"></img></td><td>暂未使用</td></tr>';
				$("#tuli").html(Html);
				var totalPage = Math.ceil(totalCount / pageSize);
				printPage(tableId, document.getElementById("paging"),
						totalPage, url, data, pageNo, pageSize,
						"printIndoorList");
				setIcRank();
			}, function(response, reason) {
				alert(reason);
			});
}
function colorDecidor(avers, warningBottomlist) {
	// var green_bottom = warningBottomlist[0].green_bottom;
	// var blue_bottom = warningBottomlist[0].blue_bottom;
	// var yellow_bottom = warningBottomlist[0].yellow_bottom;
	// var red_bottom = warningBottomlist[0].red_bottom;
	// console.log(isNaN(avers)+" ");
	if (avers < warningBottomlist[0].red_bottom) {
		return "gray";// 灰色
	} else if (avers < warningBottomlist[0].yellow_bottom) {
		return "red";// 红色
	} else if (avers < warningBottomlist[0].blue_bottom) {
		return "yellow";// 黄色
	} else if (avers < warningBottomlist[0].green_bottom) {
		return "blue";// 蓝色
	} else if (avers < 0) {
		return "green";// 绿色
	} else {
		return "white";// 无色
	}
}
function draw_Map(canvas_json, warningBottomlist) {
	var canvas = document.getElementById("canvas");
	var ctx = canvas.getContext('2d');
	ctx.clearRect(0, 0, 600, 400);
	var centers = {};// 圆心
	for (var i = 0; i < canvas_json.length; i++) {
		var center = {
			'x' : canvas_json[i].x,
			'y' : canvas_json[i].y
		};
		eval("centers['" + canvas_json[i].id + "'] = center");
	}
	ajaxUtil({
		"startTime" : getStartTime(),
		"endTime" : getEndTime(),
		"floor" : $("#floorSelector").val(),
		"building" : getCiOrBuildingId("buildingId"),
		"network" : getCiOrBuildingId("network"),
		"ci" : getCiOrBuildingId("ci")
	}, mainpath + "/indoor/indoorHeatData", function(response) {
		var points = response.list;
		var distancePerMeter = 1.8;
		// console.log(JSON.stringify(points));
		// console.log(JSON.stringify(centers));
		for (var i = 0; i < points.length; i++) {
			var theta = 2 * Math.PI * Math.random();
			var distancePixel = distancePerMeter * points[i].distance / 100;

			var x = parseFloat(centers[points[i].position].x) + Math.cos(theta)
					* distancePixel;

			var y = parseFloat(centers[points[i].position].y) + Math.sin(theta)
					* distancePixel;

			var _avers = points[i].signalStrength;
			ctx.fillStyle = colorDecidor(_avers, warningBottomlist);
			if (ctx.fillStle == '#ffffff') {
				console.log('invalid heat point');
				continue;
			}
			ctx.strokeStyle = "white";
			ctx.beginPath();
			ctx.arc(x, y, 3, 0, 2 * Math.PI);
			ctx.closePath();
			ctx.fill();
			ctx.stroke();
			ctx.save();
		}
		// 显示室内beacon在地图上的位置
		for (var i = 0; i < canvas_json.length; i++) {
			var avers = canvas_json[i].averageStrength;
			ctx.fillStyle = colorDecidor(avers, warningBottomlist);
			ctx.strokeStyle = "black";// 边界黑色
			ctx.beginPath();
			ctx.arc(canvas_json[i].x, canvas_json[i].y, 4, 0, 2 * Math.PI);
			ctx.closePath();
			ctx.fill();
			ctx.stroke();
			ctx.save();
		}
	}, function(response, reason) {
		alert("热力图数据请求失败");
	});
}
