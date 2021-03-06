
var chartBeacon;
var chartBox;
var chartDate;
function reportInit(role) {
    if(role==3){
    	$("ul.main-menu li:eq(0)").addClass("active");
    }else{
    	$("ul.main-menu li:eq(10)").addClass("active");
    }
}
/**
 * 
 * @param date
 *            'list':[{'date':'2015-10-21','num':10}]
 */
var heatmapInstance = null;
function help1()//第1个图表的调用注释函数
{
	$("#myModalLabel").text("巡检人员每天巡检到的Beacon数目的变化曲线：");
	$("#myModalBody").text("巡检人员每天巡检到的Beacon数目的变化曲线");
	$('#myModal').modal('show')
}
function help2()//第2个图表的调用注释函数
{
	$("#myModalLabel").text("每日巡检记录数目最大值、最小值、均值：");
	$("#myModalBody").text("每日巡检记录数目最大值、最小值、均值");
	$('#myModal').modal('show')
}
function help3()//第3个图表的调用注释函数
{
	$("#myModalLabel").text("巡检人员在各楼层的上传记录数目：");
	$("#myModalBody").text("巡检人员在各楼层的上传记录数目 ");
	$('#myModal').modal('show')
}
function heatMapInit() {
	$('#export').click(function() {
		exportAllCharts();
	});
	heatmapInstance = h337.create({
		container : document.getElementById('heatmapContainer'),
	});

}
function heatMapClear() {
	heatmapInstance.setData({
		"max" : 0,
		"data" : []
	});
}
function drawImsiAppearStatic(date) {
	console.log(date);
	var sumofeveryfloor = [];
	var sumoffloor = [];
	var maxfloor = 0;
	var addressoffloor = [];

	for (var i = 0; i < date.length; i++) {
		maxfloor = Math.max(maxfloor, date[i].floor);
	}
	console.log('maxfloor:' + maxfloor);
	for (var i = 0; i < maxfloor; i++) {
		sumofeveryfloor[i] = 0;
		sumoffloor[i] = 0;
		addressoffloor[i] = 0;
	}

	for (var k = 1; k <= maxfloor; k++) {
		for (var j = 0; j < date.length; j++) {
			if (date[j].floor == k) {
				sumofeveryfloor[k - 1] += date[j].num;
				sumoffloor[k - 1]++;
			}
		}
	}

	for (var i = 0; i < maxfloor; i++) {
		for (var j = 0; j <= i; j++) {
			addressoffloor[i] += sumoffloor[j];
		}
	}
	var categories1 = [];
	var series1 = [];
	for (var i = 0; i < maxfloor; i++) {
		if (sumoffloor[i] != 0) {
			var x = String(i + 1);
			categories1.push(x);
			series1.push(sumofeveryfloor[i]);
		}
	}
	console.log(series1);
	chartBeacon = new Highcharts.Chart(
	// $('#container').highcharts(
	{
		chart : {
			renderTo : 'container',
			type : 'column'
		},
		credits : {
			enabled : false
		},
		xAxis : {
			categories : categories1,
			title : {
				text : '楼层'
			}
		},
		yAxis : {
			title : {
				text : '上传记录数目（个）'
			}
		},
		title : {
			useHTML:true,
			text : "巡检人员在各楼层的上传记录数目&nbsp&nbsp| <a href='javascript:help3()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
		},
		subtitle : {
			text : '点击每一层楼查看详情'
		},
		plotOptions : {
			series : {
				cursor : 'pointer',
				point : {
					events : {
						click : function() {
							var choice = parseInt(this.category);
							var _canvas = $("#heatmapContainer");
							// _canvas.getContext("2d").fillRect(0,
							// 0, 600, 400);

							var points = [];
							var max = -1;
							var width = 600;
							var height = 400;
							var len = sumoffloor[choice - 1];
							// console.log("choice " + choice);
							// console
							// .log("addressoffloor "
							// + JSON
							// .stringify(addressoffloor));
							// console.log("date "
							// + JSON.stringify(date));
							// console
							// .log("sumoffloor "
							// + JSON
							// .stringify(sumoffloor));
							// for (var i = 0; i < len; i++) {
							// var val = date[addressoffloor[choice - 1]
							// - len + i].sum;
							// console.log("v " + val);
							// max = Math.max(max,
							// parseInt(val));
							// var point = {
							// x : date[addressoffloor[choice - 1]
							// - len + i].x,
							// y : date[addressoffloor[choice - 1]
							// - len + i].y,
							// value : val
							// };
							// points.push(point);
							// }
							for (var i = 0; i < date.length; i++) {
								if (date[i].floor == choice) {
									max = Math.max(max, parseInt(date[i].num));
									var point = {
										x : parseInt(date[i].x),
										y : parseInt(date[i].y),
										value : parseInt(date[i].num)
									};
									points.push(point);
								}
							}

							var data = {

								max : max,

								data : points

							};
							console
									.log("heat map data "
											+ JSON.stringify(data));
							heatMapClear();
							heatmapInstance.setData(data);
							/** 加载室内* */
							ajaxUtil({
								"buildingId" : $("#buildingSearchedId").val(),
								"floor" : choice
							}, mainpath + "/indoor/mapUrl", function(response) {
								console.log(response);
								if (response.list != null
										&& response.list.length > 0) {
									var url = response.list[0].url;
									_canvas.css("backgroundImage",
											"url('/buildingMap/" + url + "')");
									_canvas.css("backgroundSize", "100% 100%");// 使图片适配容器大小
								} else {
									alert("改层尚未上传室内地图");
								}
							}, function(response, reason) {
								alert(reason);
							});
						}
					}
				}
			}
		},

		series : [ {
			name : '上传记录数目',
			data : series1
		} ],
		exporting : {
			buttons : {
				contextButton : {
					enabled : true,
					menuItems : null,
					onclick : function() {
						this.exportChart();
					}
				}
			}
		}
	});
}
/**
 * 
 * @param list
 *            ['boxPlot':[{'date':'2015
 *            -10-21','maxNum':10,'minNum':10,'avgNum':10}]
 */
function drawRecordNumBoxPlot(list) {
	// boxPlot_container
	// xList : array

	var average = 0;
	var xList = [];
	var yItemList = [];
	for (var i = 0; i < list.length; i++) {
		xList.push(list[i].date);
		average += list[i].avgNum;
		var array = new Array(list[i].minNum, list[i].avgNum, list[i].avgNum,
				list[i].avgNum, list[i].maxNum);
		yItemList.push(array);
	}
	console.log(yItemList);
	if (list.length != 0)
		average = average / list.length;
	$(function() {
		chartBox = new Highcharts.Chart(
		// $('#boxPlot_container').highcharts(
		{
			chart : {
				renderTo : 'boxPlot_container',
				type : 'boxplot'
			},

			title : {
				useHTML:true,
				text : "每日巡检记录数目最大值、最小值、均值 &nbsp&nbsp| <a href='javascript:help2()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
			},

			legend : {
				enabled : false
			},

			xAxis : {
				categories : xList,
				title : {
					text : '日期'
				}
			},

			yAxis : {
				title : {
					text : "记录数目"
				},
				plotLines : [ {
					value : average,
					color : 'red',
					width : 1,
					label : {
						text : '该段时间内每日记录平均约' + average.toFixed(0) + '个',
						align : 'center',
						style : {
							color : 'gray'
						}
					}
				} ]
			},
			series : [ {
				name : '上传记录数目',
				data : yItemList,
				tooltip : {
					headerFormat : '<em>{point.key}</em><br/>',
					useHTML : true,

				}
			} ],
			exporting : {
				buttons : {
					contextButton : {
						enabled : true,
						menuItems : null,
						onclick : function() {
							this.exportChart();
						}
					}
				}
			}

		});
	});
}
/** 巡检人员每天的巡检到的Beacon个数的曲线图* */
function drawBeaconCurve(list) {
	var data = [];
	var categories = [];
	for (var i = 0; i < list.length; i++) {
		categories.push(list[i].date);
		data.push(list[i].num);
	}
	console.log(categories);
	console.log(data);
	chartDate = new Highcharts.Chart(
	// $('#date_container').highcharts(
	{
		chart : {
			renderTo : 'date_container',
			type : 'line'
		},
		title : {
			useHTML:true,
			text : "巡检人员每天巡检到的Beacon数目的变化曲线 &nbsp&nbsp| <a href='javascript:help1()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
		},
		xAxis : {
			categories : categories
		},
		yAxis : {
			title : {
				text : 'Beacon数目（个）'
			}
		},
		tooltip : {
			enabled : false,
			formatter : function() {
				return '<b>' + this.series.name + '</b><br/>' + this.x + ': '
						+ this.y + '个';
			}
		},
		plotOptions : {
			line : {
				dataLabels : {
					enabled : true
				},
				enableMouseTracking : false
			}
		},
		series : [ {
			name : 'Beacon数目',
			data : data
		} ],
		exporting : {
			buttons : {
				contextButton : {
					enabled : true,
					menuItems : null,
					onclick : function() {
						this.exportChart();
					}
				}
			}
		}
	});
}
function onSearch() {
	$("#search").click(
			function() {
				var requestdata = {
					"startTime" : getStartTime(),
					"endTime" : getEndTime(),
					"building" : $("#buildingSearchedId").val(),
					"imsi" : $("#imsi").val()
				};
				ajaxUtil(requestdata, mainpath
						+ "/inspectAdmin/ImsiAppearStatic", function(response) {
					drawImsiAppearStatic(response.imsiStatic);
				}, function(response, reason) {
				});
				ajaxUtil(requestdata, mainpath + "/inspectAdmin/positionCurve",
						function(response) {
							drawBeaconCurve(response.list);
							drawRecordNumBoxPlot(response.boxPlot);
						}, function(response, reason) {
						});
			});
}

function trigger() {
	if (getStartTime() != null && getEndTime() != null
			&& $("#floorSelector").val() != null
			&& $("#floorSelector").val().trim().length != 0
			&& $("#buildingSearchedId").val() != null
			&& $("#buildingSearchedId").val().trim().length != 0
			&& $("#imsi").val() != null && $("#imsi").val().trim().length != 0) {
		// alert("click");
		$("#search").trigger('click');
	}
}
function exportAllCharts() {
	var type = "image/jpeg";
	if (chartBeacon != null) {
		// alert(1);
		chartBeacon.exportChartLocal({
			"type" : type
		});
	}
	if (chartBox != null) {
		// alert(2);
		// chartBeacon.exportChart();
		chartBox.exportChartLocal({
			"type" : type
		});
	}
	if (chartDate != null) {
		// alert(3);
		chartBox.exportChartLocal({
			"type" : type
		});
	}
}
