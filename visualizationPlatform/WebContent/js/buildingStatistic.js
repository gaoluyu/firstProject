/**
 * 
 */
$(function() {
	$("ul.main-menu li:eq(4)").addClass("active");
});
var cdfList_2G;// CDF signalStrength:per
var cdfList_3G;
var cdfList_4G;
function help1()//第1个图表的调用注释函数
{
	$("#myModalLabel").text("上传记录数目在2G、3G、4G中的比例情况：");
	$("#myModalBody").text("上传记录数目在2G、3G、4G中的比例情况");
	$('#myModal').modal('show')
}
function help2()//第2个图表的调用注释函数
{
	$("#myModalLabel").text("在2G、3G、4G各制式的上传记录中室分系统的覆盖比例：");
	$("#myModalBody").text("在2G、3G、4G各制式的上传记录中室分系统的覆盖比例");
	$('#myModal').modal('show')
}
function help3()//第3个图表的调用注释函数
{
	$("#myModalLabel").text("2G、3G、4G中告警数目与正常数目的比例情况：");
	$("#myModalBody").text("2G、3G、4G中告警数目与正常数目的比例情况 ");
	$('#myModal').modal('show')
}
function help4()//第4个图表的调用注释函数
{
	$("#myModalLabel").text("在2G、3G、4G中场强的CDF分布曲线：");
	$("#myModalBody").text("在2G、3G、4G中场强的CDF分布曲线");
	$('#myModal').modal('show')
}
function onSearch() {
	$("#search").click(
			function() {
				var requestdata = {
					"startTime" : getStartTime(),
					"endTime" : getEndTime(),
					"network" : $("#networktype").val(),
					"buildingId" : $("#buildingSearchedId").val(),
					"floor" : $("#floorSelector").val()
				};
				ajaxUtil(requestdata, mainpath
						+ "/buildingStatistic/dataforPie", function(response) {
					drawPie(response.tList);
				}, function(response, reason) {
				});
				ajaxUtil(requestdata, mainpath
						+ "/buildingStatistic/dataforBar_1",
						function(response) {
							drawBar(response.tList, response.fList,
									response.cList);

						}, function(response, reason) {
						});

				ajaxUtil(requestdata, mainpath
						+ "/buildingStatistic/dataforBar_2",
						function(response) {
							// console.log(JSON.stringify(response));
							drawBarpart(response.fList, response.wList);

						}, function(response, reason) {
						});

				ajaxUtil(requestdata, mainpath
						+ "/buildingStatistic/dataforCDF", function(response) {
					console.log(response);
					drawCDF(response.cdfList_2G, response.cdfList_3G,
							response.cdfList_4G);
				}, function(response, reason) {
				});
			});
}
function trigger() {
	if (getStartTime() != null && getEndTime() != null
			&& $("#networktype").val() != null
			&& $("#networktype").val().trim().length != 0
			&& $("#buildingSearchedId").val() != null
			&& $("#buildingSearchedId").val().trim().length != 0
			&& $("#floorSelector").val() != null
			&& $("#floorSelector").val().trim().length != 0) {
		// alert("click");
		$("#search").trigger('click');
	}
}

function drawCDF(cdfList_2G, cdfList_3G, cdfList_4G) {
	var cdfList_2G_array = [];
	var cdfList_3G_array = [];
	var cdfList_4G_array = [];
	if (cdfList_2G != null) {
		for (var i = 0; i < cdfList_2G.length; i++) {
			cdfList_2G_array.push([ cdfList_2G[i].signalStrength,
					cdfList_2G[i].per * 100 ]);
		}
		// console.log(cdfList_2G_array);
	}
	if (cdfList_3G != null) {
		for (var i = 0; i < cdfList_3G.length; i++) {
			cdfList_3G_array.push([ cdfList_3G[i].signalStrength,
					cdfList_3G[i].per * 100 ]);
		}
		// console.log(cdfList_4G_array);
	}
	if (cdfList_4G != null) {
		for (var i = 0; i < cdfList_4G.length; i++) {
			cdfList_4G_array.push([ cdfList_4G[i].signalStrength,
					cdfList_4G[i].per * 100 ]);
		}
		console.log(cdfList_4G_array);
	}
	var option = {
		credits : {
			enabled : false
		},
		colors : [ '#7cb5ec', '#ffe07c', '#ffa77c' ],
		title : {
			useHTML:true,
			text : "2G/3G/4G网络覆盖CDF分布 &nbsp&nbsp| <a href='javascript:help4()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
		},
		xAxis : {
			title : {
				text : '场强（单位：dBm）'
			},
		},
		yAxis : [ {
			labels : {
				format : '{value}%',
			},
			max : '100',
			title : {
				enabled : false
			}
		} ],
		tooltip : {
			valueDecimals : 2,
			useHtml : true,
			headerFormat : '<small>场强：<b>{point.key}dBm</b></small><br/>'

		},
		series : [ {
			name : '2G',
			type : 'line',
			data : [],
			tooltip : {
				valueSuffix : '%'
			},
			marker : {
				enabled : false
			}
		}, {
			name : '3G',
			type : 'line',
			data : [],
			tooltip : {
				valueSuffix : '%'
			},
			marker : {
				enabled : false
			}
		}, {
			name : '4G',
			type : 'line',
			data : [],
			tooltip : {
				valueSuffix : '%'
			},
			marker : {
				enabled : false
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

	};
	option.series[0].data = cdfList_2G_array;
	option.series[1].data = cdfList_3G_array;
	option.series[2].data = cdfList_4G_array;
	$(function() {
		$('#container_4').highcharts(option);
	});
}

function drawBarpart(fList, wList) {
	var warning_2G;// 告警条数
	var warning_3G;
	var warning_4G;
	var valid_2G;// 是该处CID
	var valid_3G;
	var valid_4G;
	for (var i = 0; i < wList.length; i++) {
		if (wList[i].network == "2G") {
			warning_2G = wList[i].number;
		} else if (wList[i].network == "3G") {
			warning_3G = wList[i].number;
		} else if (wList[i].network == "4G") {
			warning_4G = wList[i].number;
		}
	}

	for (var i = 0; i < fList.length; i++) {
		if (fList[i].network == "2G") {
			valid_2G = fList[i].number;
		} else if (fList[i].network == "3G") {
			valid_3G = fList[i].number;
		} else if (fList[i].network == "4G") {
			valid_4G = fList[i].number;
		}
	}

	var normal2G;
	var normal3G;
	var normal4G;
	var normal3G;
	var normal4G;
	var normal2G;

	if (valid_2G != 0) {
		normal2G = parseFloat(((valid_2G - warning_2G) * 100 / valid_2G)
				.toFixed(2));
		warn2G = parseFloat(((warning_2G * 100) / valid_2G).toFixed(2));
	} else {
		normal2G = 0;
		warn2G = 0;
	}

	if (valid_3G != 0) {
		normal3G = parseFloat(((valid_3G - warning_3G) * 100 / valid_3G)
				.toFixed(2));
		warn3G = parseFloat(((warning_3G * 100) / valid_3G).toFixed(2));
	} else {
		normal3G = 0;
		warn3G = 0;
	}

	if (valid_4G != 0) {
		normal4G = parseFloat(((valid_4G - warning_4G) * 100 / valid_4G)
				.toFixed(2));
		warn4G = parseFloat(((warning_4G * 100) / valid_4G).toFixed(2));
	} else {
		normal4G = 0;
		warn4G = 0;
	}

	var normalData = [ normal2G, normal3G, normal4G ];
	var warnData = [ warn2G, warn3G, warn4G ];

	// console.log(normalData);
	// console.log(warnData);
	$(function() {
		$('#container_3')
				.highcharts(
						{
							credits : {
								enabled : false
							},
							colors : [ "#f15c80", "#90ed7d" ],
							chart : {
								type : 'column'
							},
							title : {
								useHTML:true,
								text : "2G/3G/4G网络覆盖告警占比  &nbsp&nbsp| <a href='javascript:help3()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
							},
							xAxis : {
								categories : [ '2G', '3G', '4G' ]
							},
							yAxis : {
								min : 0,
								title : {
									text : '记录占比'
								}
							// stackLabels : {
							// enabled : true,
							// style : {
							// fontWeight : 'bold',
							// color : (Highcharts.theme &&
							// Highcharts.theme.textColor)
							// || 'green'
							// }
							// }
							},
							legend : {
								align : 'right',
								x : -70,
								verticalAlign : 'top',
								y : 20,
								floating : true,
								backgroundColor : (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid)
										|| 'white',
								borderColor : '#CCC',
								borderWidth : 1,
								shadow : false
							},
							tooltip : {
								formatter : function() {
									return '<b>' + this.x + '</b><br/>'
											+ this.series.name + ': '
											+ this.y.toFixed(2) + '%' + '<br/>'
											+ '总共: '
											+ this.point.stackTotal.toFixed(2)
											+ '%';
								}
							},
							plotOptions : {
								column : {
									stacking : 'normal',
									dataLabels : {
										enabled : true,
										format : '{y} %',
										color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
												|| 'black'
									}
								}
							},
							series : [ {
								name : '告警',
								data : warnData
							}, {
								name : '正常',
								data : normalData
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

function drawPie(tList) {
	var valid_2G;// 是该处CID
	var valid_3G;
	var valid_4G;
	for (var i = 0; i < tList.length; i++) {
		if (tList[i].network == "2G") {
			valid_2G = tList[i].number;
		} else if (tList[i].network == "3G") {
			valid_3G = tList[i].number;
		} else if (tList[i].network == "4G") {
			valid_4G = tList[i].number;
		}
	}

	$(function() {
		/** 渐变颜色* */
		Highcharts.getOptions().colors = Highcharts.map([ '#7cb5ec', '#ffe07c',
				'#ffa77c' ], function(color) {
			return {
				radialGradient : {
					cx : 0.5,
					cy : 0.3,
					r : 0.7
				},
				stops : [ [ 0, color ],
						[ 1, Highcharts.Color(color).brighten(0.1).get('rgb') ] // darken
				]
			};
		});
		$('#container_1')
				.highcharts(
						{
							chart : {
								options3d : {
									enabled : true,
									alpha : 45,
									beta : 0
								}
							},
							credits : {
								enabled : false
							},
							title : {
								useHTML:true,
								text : "室分系统2G/3G/4G网络覆盖比例  &nbsp&nbsp| <a href='javascript:help1()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
							},
							tooltip : {
								pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
							},
							plotOptions : {
								pie : {
									allowPointSelect : true,
									cursor : 'pointer',
									depth : 35,
									dataLabels : {
										enabled : true,
										color : '#000000',
										connectorColor : '#000000',
										formatter : function() {
											return '<b>'
													+ this.point.name
													+ '</b>'
													+ this.percentage
															.toFixed(1) + ' %';
										}
									}
								}
							},
							series : [ {
								type : 'pie',
								name : '占比',
								data : [ {
									y : valid_2G,
									name : '2G ：'
								}, {
									name : '3G ：',
									y : valid_3G,
									sliced : true,
									selected : true,
								}, {
									name : '4G ：',
									y : valid_4G,
								} ]
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

function drawBar(tList, fList, cList) {
	var valid_2G;// 是该处CID
	var valid_3G;
	var valid_4G;
	var all_2G;// 所有检测到的CID
	var all_3G;
	var all_4G;
	for (var i = 0; i < tList.length; i++) {
		if (tList[i].network == "2G") {
			valid_2G = tList[i].number;
		} else if (tList[i].network == "3G") {
			valid_3G = tList[i].number;
		} else if (tList[i].network == "4G") {
			valid_4G = tList[i].number;
		}
	}
	for (var i = 0; i < fList.length; i++) {
		if (fList[i].network == "2G") {
			all_2G = fList[i].number;
		} else if (fList[i].network == "3G") {
			all_3G = fList[i].number;
		} else if (fList[i].network == "4G") {
			all_4G = fList[i].number;
		}
	}

	var CoverageRatio_2G;
	var CoverageRatio_3G;
	var CoverageRatio_4G;

	if (all_2G != 0) {
		CoverageRatio_2G = 100 * valid_2G / all_2G;
	} else {
		CoverageRatio_2G = 0;
	}
	if (all_3G != 0) {
		CoverageRatio_3G = 100 * valid_3G / all_3G;
	} else {
		CoverageRatio_3G = 0;
	}
	if (all_4G != 0) {
		CoverageRatio_4G = 100 * valid_4G / all_4G;
	} else {
		CoverageRatio_4G = 0;
	}
	var detail2G_data = [];
	var detail3G_data = [];
	var detail4G_data = [];
	var detail2G_cate = [];
	var detail3G_cate = [];
	var detail4G_cate = [];

	var _2g = 0;
	var _3g = 0;
	var _4g = 0;

	for (var i = 0; i < cList.length; i++) {
		if (cList[i]._networkType == "2G") {
			_2g++;
			if (_2g <= 5) {
				detail2G_data.push(cList[i].number * 100 / all_2G);
				detail2G_cate.push(cList[i].cid.toString());
			}
		} else if (cList[i]._networkType == "3G") {
			_3g++;
			if (_3g <= 5) {
				detail3G_data.push(cList[i].number * 100 / all_3G);
				detail3G_cate.push(cList[i].cid.toString());
			}
		} else if (cList[i]._networkType == "4G") {
			_4g++;
			if (_4g <= 5) {
				detail4G_data.push(cList[i].number * 100 / all_4G);
				detail4G_cate.push(cList[i].cid.toString());
			}
		}
	}

	// high chart
	var colors = [ '#7cb5ec', '#ffe07c', '#ffa77c' ], categories = [ '2G',
			'3G', '4G' ], name = '网络制式', data = [ {
		y : CoverageRatio_2G,
		color : colors[0],
		drilldown : {
			name : '在2G制式下各CID记录数目的比例情况',
			categories : detail2G_cate,
			data : detail2G_data,
			color : '#9ECBF6'
		}
	}, {
		y : CoverageRatio_3G,
		color : colors[1],
		drilldown : {
			name : '在3G制式下各CID记录数目的比例情况',
			categories : detail3G_cate,
			data : detail3G_data,
			color : '#FFE89D'
		}
	}, {
		y : CoverageRatio_4G,
		color : colors[2],
		drilldown : {
			name : '在4G制式下各CID记录数目的比例情况',
			categories : detail4G_cate,
			data : detail4G_data,
			color : '#FFbd9D'
		}
	} ];

	function setChart(name, categories, data, color) {
		chart.xAxis[0].setCategories(categories, false);
		chart.series[0].remove(false);
		chart.addSeries({
			name : name,
			data : data,
			color : color || 'white'
		}, false);
		chart.redraw();
	}

	var chart = $('#container_2')
			.highcharts(
					{
						credits : {
							enabled : false
						},
						chart : {
							type : 'column'
						},
						title : {
							useHTML:true,
							text : "2G/3G/4G各网络室分覆盖占比  &nbsp&nbsp| <a href='javascript:help2()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
						},
						subtitle : {
							text : '点击查看详情'
						},
						xAxis : {
							categories : categories
						},
						yAxis : {
							title : {
								text : '覆盖比例 ( % )'
							}
						},
						plotOptions : {
							column : {

								cursor : 'pointer',
								point : {
									events : {
										click : function() {
											var drilldown = this.drilldown;
											if (drilldown) { // drill down
												setChart(drilldown.name,
														drilldown.categories,
														drilldown.data,
														drilldown.color);
											} else { // restore
												setChart(name, categories, data);
											}
										}
									}
								},
								dataLabels : {
									enabled : true,
									color : 'black',
									style : {
										fontWeight : 'bold'
									},
									formatter : function() {
										var point = this.point;
										if (point.drilldown) {
											return this.y.toFixed(2) + '%';
										} else {
											return this.y.toFixed(2) + '%'
										}
									}
								}
							}
						},
						tooltip : {
							formatter : function() {
								var point = this.point, s = this.y;
								if (point.drilldown) {
									s = this.x + '制式覆盖比例: ' + this.y.toFixed(2)
											+ '% <br/>点击查看Cid分布 ';
								} else {
									s = 'cid=' + this.x + '<br/>记录占比:'
											+ this.y.toFixed(2) + '% <br/>'
											+ '<br/>点击回到原图';
								}
								return s;
							}
						},
						series : [ {
							name : name,
							data : data,
							color : 'white'
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
					}).highcharts(); // return chart

}
