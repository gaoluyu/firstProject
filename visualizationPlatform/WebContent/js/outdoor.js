var markerOverlay = [];
var warningNum;
var coverageRationCache = 0.95;
$(function() {
	$("ul.main-menu li:eq(1)").addClass("active");
});
function onSearch() {
	$("#search").click(function() {
		ajaxUtil({
			"startTime" : getStartTime(),
			"endTime" : getEndTime(),
			"wpNetwork" : $("#network").val(),
			"ciMayDefault" : 0
		}, mainpath + "/outdoor/pointList", function(response) {
			var covergeRatio = response.covergeRatio;
			var warningBottomlist = response.warningBottomlist;
			drawTuli(covergeRatio, warningBottomlist);
			clearMarkers();
			drawMap(response);
		}, function(response, reason) {
		});
	});
}
function trigger() {
	if (getStartTime() != null && getEndTime() != null
			&& $("#network").val() != null
			&& $("#network").val().trim().length != 0) {
		// alert($("#network").val())
		// alert("click");
		$("#search").trigger('click');
	}
}

function onCiSearch(ci) {
	ajaxUtil({
		"startTime" : getStartTime(),
		"endTime" : getEndTime(),
		"wpNetwork" : $("#network").val(),
		"ciMayDefault" : ci
	}, mainpath + "/outdoor/pointList", function(response) {
		var covergeRatio = response.covergeRatio;
		var warningBottomlist = response.warningBottomlist;
		drawTuli(covergeRatio, warningBottomlist);
		clearMarkers();
		drawMap(response);

	}, function(response, reason) {
	});
}

function drawTuli(covergeRatio, warningBottomlist) {
	var html = '<p>超过<span style="color:#2fa4e7">'
			+ covergeRatio[0].coverageRatio + '%</span>的场强分布在</p>';
	coverageRationCache = parseInt(covergeRatio[0].coverageRatio);
	var green_bottom = warningBottomlist[0].green_bottom;
	var blue_bottom = warningBottomlist[0].blue_bottom;
	var yellow_bottom = warningBottomlist[0].yellow_bottom;
	var red_bottom = warningBottomlist[0].red_bottom;
	html += '<ul style="list-style:none;margin-left:-40px;line-height:30px">'
			+ '<li><img src="/androidServer/images/grayicon.png"></img>' + '~'
			+ red_bottom
			+ 'dBm</li>'
			+ '<li><img src="/androidServer/images/redicon.png"></img>'
			+ red_bottom
			+ '~'
			+ yellow_bottom
			+ 'dBm</li>'
			+ '<li><img src="/androidServer/images/yellowicon.png"></img>'
			+ yellow_bottom
			+ '~'
			+ blue_bottom
			+ 'dBm</li>'
			+ '<li><img src="/androidServer/images/blueicon.png"></img>'
			+ blue_bottom
			+ '~'
			+ green_bottom
			+ 'dBm</li>'
			+ '<li><img src="/androidServer/images/greenicon.png"></img>'
			+ green_bottom
			+ '~'
			+ 'dBm</li>'
			+ '<li><img src="/androidServer/images/whiteicon.png"></img>暂未使用</li>'
			+ '</ul>';

	$("#tuli").html(html);
}
function clearMarkers() {
	for ( var mk in markerOverlay) {
		mp.removeOverlay(markerOverlay[mk]);
		// mk.closeInfoWindow();
	}
	markerOverlay = [];
}
// function gps2bs09ll(dataList) {
// var coords = '';
// for (var i = 0; i < dataList.length; i++) {
// coords += (dataList[i].longitude + ',' + dataList[i].latitude + ';');
// }
//
// var requestdata = {
// 'coords' : coords,
// 'from' : 1,
// "to" : 5,
// 'ak' : 'NNu08gY8VCMhVPINhIk1Bw2r'
// };
//
// ajaxUtil(requestdata, "http:// api.map.baidu.com/geoconv/v1/", function(
// response) {
// if (status == 0) {
// var list = response.result
// } else {
// alert("坐标转换失败 status:" + status);
// }
// }, function(response, reason) {
// alert("坐标转换失败");
// });
//
// }
function drawMap(response) {
	var markerArr = calculateColor(response.list);
	for (var j = 0; j < markerArr.length; j++) {
		(function() { // 使用闭包(function(){})();给每一个循环变量添加点击事件
			var i = j;
			var json = markerArr[i];
			var nt = json.netType;// 网络类型
			var op = json.operator;// 运营商
			var avs = 0;// 平均场强
			var rsrq = 0;
			var sinr = 0;
			var dlbps = 0;
			var ulbps = 0;
			var warn = 0;
			var txt = json.name;// 建筑名称
			var prv = json.province;// 省份
			var ct = json.city;// 城市
			var ci = json.ci;// 小区编号
			var pintx = json.longitudeMap;// 经度
			var pinty = json.latitudeMap;// 维度
			if (pintx == 0)
				pintx = json.longitude
			if (pinty == 0)
				pinty = json.latitude
			var color = json.flag;// 点的颜色
			var bgcolor;// 信息窗口的颜色
			var titlecolor;// 标题的颜色
			var pt = new BMap.Point(pintx, pinty);// 根据经纬度坐标生成点
			var myIcon, marker;

			if (typeof (avs) === 'undefined') {
				avs = "暂无数据";
			} else {
				avs += "&nbsp;dBm";
			}
			switch (color)// 根据后台传过来的color参数选择不同的点的颜色
			{
			/** 绿色* */
			case 1:
				myIcon = new BMap.Icon("/androidServer/images/greenicon.png",
						new BMap.Size(20, 27));
				marker = new BMap.Marker(pt, {
					icon : myIcon
				});
				bgcolor = "#dff0d8";
				titlecolor = "#339933";
				break;
			case 2:
				/** 蓝色* */
				myIcon = new BMap.Icon("/androidServer/images/blueicon.png",
						new BMap.Size(20, 27));
				marker = new BMap.Marker(pt, {
					icon : myIcon
				});
				bgcolor = "#D7EAEF";
				titlecolor = "#336699";
				break;
			case 3:
				/** 黄色* */
				myIcon = new BMap.Icon("/androidServer/images/yellowicon.png",
						new BMap.Size(20, 27));
				marker = new BMap.Marker(pt, {
					icon : myIcon
				});
				bgcolor = "#EAEFD7";
				titlecolor = "#999933";
				break;
			case 4:
				/** 红色* */
				myIcon = new BMap.Icon("/androidServer/images/redicon.png",
						new BMap.Size(20, 27));
				marker = new BMap.Marker(pt, {
					icon : myIcon
				});
				bgcolor = "#f2dede";
				titlecolor = "#a61e1e";
				break;
			case 5:
				/** 灰色* */
				myIcon = new BMap.Icon("/androidServer/images/grayicon.png",
						new BMap.Size(20, 27));
				marker = new BMap.Marker(pt, {
					icon : myIcon
				});
				bgcolor = "#dedede";
				break;
			default:
				/** 白色* */
				myIcon = new BMap.Icon("/androidServer/images/whiteicon.png",
						new BMap.Size(20, 27));
				marker = new BMap.Marker(pt, {
					icon : myIcon
				});
				bgcolor = "#ffffff";
			}
			mp.addOverlay(marker);// 给地图添加覆盖物
			markerOverlay.push(marker);//
			var network = getNetType(op, nt);// 获取点的网络类型
			/** 定位点最小化显示的内容* */
			var mincontent = "<table  style='border-radius:0.2em; padding:10px; width:240px; height:60px;background-color:"
					+ bgcolor
					+ "'><tbody>"
					+ "<tr><td>&nbsp;&nbsp;网络类型：</td><td>"
					+ network
					+ "</td></tr>"
					+ "<tr><td >&nbsp;&nbsp;小区编号：</td><td style='font-weight:600'>"
					+ ci + "</td></tr>" + "</tbody></table>";
			var opts = {
				width : 240, // 信息窗口宽度
				height : 100, // 信息窗口高度
				title : "<h4 style='color:" + titlecolor + "'>" + txt + "</h4>", // 信息窗口标题
				enableAutoPan : true,// 开启打开信息窗口时地图自动平移
				enableCloseOnClick : true,// 开启点击地图时自动关闭信息窗口
				maxWidth : 290, // 信息窗口最大化时的宽度
			};
			var infoWindow = new BMap.InfoWindow(mincontent, opts); // 创建信息窗口对象
			infoWindow.enableMaximize();// 开启窗口最大化功能
			marker.addEventListener("mouseover", function(e) {// 给marker添加鼠标移入事件
				mp.openInfoWindow(infoWindow, pt);// 开启信息窗口
			});
			infoWindow
					.addEventListener(
							"maximize",
							function(event) {
								ajaxUtil(
										{
											"cid" : ci,
											"network" : network,
											"startTime" : getStartTime(),
											"endTime" : getEndTime()
										},
										mainpath + "/outdoor/pointAvg",
										function(response) {
											var avgList = response.avgList;
											// 待显示在界面详细框中
											avs = avgList[0].averageStrength;
											rsrp = avgList[0].averageRsrp;
											rsrq = avgList[0].averageRsrq;
											sinr = avgList[0].averageSinr;
											dlbps = avgList[0].averageDlbps;
											ulbps = avgList[0].averageUlbps;
											warn = response.warnNum;
											var strengthTitle = '平均场强';
											if (network.indexOf("3G", 0) != -1)
												strengthTitle = '平均RSCP';
											else if (network.indexOf("4G", 0) != -1) {
												strengthTitle = '平均RSRP';
											}
											var maxcontent = "<table style='line-height:25px;width:300px; height:380px;background-color:"
													+ bgcolor
													+ "'><tbody>"
													+ "<tr><td>&nbsp;&nbsp;网络类型：</td><td>"
													+ network
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;"
													+ strengthTitle
													+ "：</td><td>"
													+ avs.toFixed(2)
													+ "&nbsp;dBm"
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;平均RSRQ：</td><td>"
													+ rsrq
													+ "&nbsp;dBm"
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;平均SINR：</td><td>"
													+ sinr
													+ "&nbsp;dBm"
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;下行传输速率：</td><td>"
													+ dlbps
													+ "&nbsp;kbps"
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;上行传输速率：</td><td>"
													+ ulbps
													+ "&nbsp;kbps"
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;告警数：</td><td style='color:red'>"
													+ warn
													+ "个</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;省份：</td><td>"
													+ prv
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;城市：</td><td>"
													+ ct
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;小区编号：</td><td>"
													+ ci
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;经度：</td><td>"
													+ pintx
													+ "</td></tr>"
													+ "<tr><td>&nbsp;&nbsp;纬度：</td><td>"
													+ pinty
													+ "</td></tr>"
													+ "</tbody></table>";

											infoWindow
													.setMaxContent(maxcontent);// 设置最大化窗口的内容

										}, function() {
											alert("建立会话失败 ciInSession");
										})
							});
			marker.addEventListener("click", function(e) {// 给marker添加点击事件
				ajaxUtil({
					"ci" : ci,
					"network" : network
				}, mainpath + "/outdoor/setCiInSession", function() {
				}, function() {
					alert("建立会话失败 ciInSession");
				})
				window.location.href = "indoor_statistic";
			});
			if (i == 0) {// 设置第一个点为中心点
				var centerPoint = new BMap.Point(pintx, pinty);
				mp.setCenter(centerPoint);// 设置地图中心点
				mp.openInfoWindow(infoWindow, centerPoint);// 自动打开中心点信息窗口
			}
		})();
	}
}
function getNetType(op, network) {
	var operator = "未知运营商";
	if (op == "00" || op == "02")
		operator = "移动";
	else if (op == "01")
		operator = "联通";
	else if (op == "03")
		operator = "电信"
	var _2 = "'EDGE', 'GPRS', 'CDMA', '1xRTT', 'IDEN'";
	var _3 = "'UMTS', 'EVDO_0', 'EVDO_A', 'HSDPA', 'HSPA', 'HSPAP', 'HSUPA','EVDO_B', 'EHRPD','UNKNOWN'";
	var _4 = "'LTE'";
	if (_2.indexOf(network.trim()) != -1) {
		operator += "2G";
	} else if (_3.indexOf(network.trim()) != -1) {
		operator += "3G";
	} else if (_4.indexOf(network.trim()) != -1) {
		operator += "4G";
	} else {
		operator += "未知网络类型";
	}
	return operator;
}
/**
 * 将服务器返回的结果(pdf)中，计算覆盖率为95%的电平值对应的颜色区间
 * 
 * @param responseList
 *            responseList的格式详见data/test.json
 */
function calculateColor(responseList) {
	var resultList = [];
	var preCi = -1;
	var preIndex = -1;
	// 填充resultList,合并相同的ci
	for (var i = 0; i < responseList.length; i++) {
		var item = responseList[i];
		if (preCi == item.ci) {
			// 仍是相同的ci
			// 利用flagNumber记录总数目
			resultList[preIndex].flagNumber += item.flagNumber;
		} else {
			// 新的ci
			preIndex++;
			preCi = item.ci;
			resultList.push(deepClone(item));
		}
	}
	console.log(JSON.stringify(resultList));
	// 计算resultList每个ci对应的颜色
	for (var i = 0; i < resultList.length; i++) {
		var item = resultList[i];
		var count = 0;// 当count>=item.flagNumber*coverageRationCache时，视为找到颜色区间
		var targetValue = Math.floor(item.flagNumber * coverageRationCache
				/ 100.0);
		console.log("targetValue " + targetValue);
		for (var j = 0; j < responseList.length; j++) {
			var oneOfItem = responseList[j];
			if (item.ci == oneOfItem.ci) {
				count += oneOfItem.flagNumber;
				console.log("ci " + JSON.stringify(oneOfItem) + "\ncount"
						+ count);
				if (count >= targetValue) {
					item.flag = oneOfItem.flag;
					item.averageStrength = oneOfItem.averageStrength;
					break;
				}
			}
		}
	}

	return resultList;
}
/**
 * 实现对象的深拷贝
 */
function deepClone(source) {
	var result = {};
	for ( var key in source) {
		result[key] = typeof source[key] === 'object' ? deepCoyp(source[key])
				: source[key];
	}
	return result;
}
