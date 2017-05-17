$(function() {
	$("ul.main-menu li:eq(5)").addClass("active");
});
function help1()//第1个图表的调用注释函数
{
	$("#myModalLabel").text("RSRP与RSRQ散点分布图：");
	$("#myModalBody").text("RSRP与RSRQ散点分布图");
	$('#myModal').modal('show')
}
function help2()//第2个图表的调用注释函数
{
	$("#myModalLabel").text("上下行速率的CDF分布曲线：");
	$("#myModalBody").text("上下行速率的CDF分布曲线");
	$('#myModal').modal('show')
}
function drawScatter(rlist) {
	// console.log(JSON.stringify(rlist));
	var data = [];
	for (var i = 0; i < rlist.length; i++) {
		var r = [];
		r.push(rlist[i].rsrp);
		r.push(rlist[i].rsrq);
		data[i] = r;
	}
	// console.log(JSON.stringify(data));
	var option = {
		chart : {
			type : 'scatter'
		},
		title : {
			useHTML:true,
			text : "RSRP与RSRQ散点分布图 &nbsp&nbsp| <a href='javascript:help1()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
		},
		credits : {
			enabled : false
		},
		xAxis : {
			title : {
				text : 'RSRP'
			}
		},
		yAxis : {
			title : {
				text : 'RSRQ'
			}
		},
		series : [ {
			name : null,
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
	};
	$('#container_1').highcharts(option);
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
				ajaxUtil(requestdata, mainpath + "/LTE/getRsrpRsrq", function(
						response) {

					// 散点图
					// console.log(response);
					var rlist = response.rlist;

					drawScatter(rlist);

				}, function(response, reason) {
				});
				ajaxUtil(requestdata, mainpath + "/LTE/getCDFData", function(
						response) {

					// CDF
					console.log(response);
					drawCDF(response.cdfLists.ulcdf, response.cdfLists.dlcdf);

				}, function(response, reason) {
				});

			});
}
function trigger() {
	if (getStartTime() != null && getEndTime() != null
			&& $("#floorSelector").val() != null
			&& $("#floorSelector").val().trim().length != 0
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
function drawPoints(response) {

}
function drawCDF(ucdf, dcdf) {
	var ucdf_array = [];
	var dcdf_array = [];
	if (ucdf != null) {
		for (var i = 0; i < ucdf.length; i++) {
			ucdf_array.push([ ucdf[i].ul_bps, ucdf[i].per * 100 ]);
		}
		// console.log(ucdf_array);
	}
	if (dcdf != null) {
		for (var i = 0; i < dcdf.length; i++) {
			dcdf_array.push([ dcdf[i].dl_bps, dcdf[i].per * 100 ]);
		}
		// console.log(dcdf_array);
	}
	var option = {
		credits : {
			enabled : false
		},
		colors : [ '#7cb5ec', '#ffe07c', '#ffa77c' ],
		title : {
			useHTML:true,
			text : "上下行速率的CDF分布曲线 &nbsp&nbsp| <a href='javascript:help2()'><i class='glyphicon glyphicon-question-sign'> </i></a>"
		},
		xAxis : {
			title : {
				text : '速率（单位：kbps）'
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
			headerFormat : '<small>速率：<b>{point.key}kbps</b></small><br/>'

		},
		series : [ {
			name : '上行速率',
			type : 'line',
			data : [],
			tooltip : {
				valueSuffix : '%'
			},
			marker : {
				enabled : false
			}
		}, {
			name : '下行速率',
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
	option.series[0].data = ucdf_array;
	option.series[1].data = dcdf_array;
	$(function() {
		$('#container_2').highcharts(option);
	});

}
