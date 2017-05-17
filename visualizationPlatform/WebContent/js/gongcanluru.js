var mainpath;
$(function() {
	$("ul.main-menu li:eq(8)").addClass("active");
});
/** 实时自动填充* */
function initCiSearch(onCiSearch) {
	$("#CiSearch").autocomplete({
		autoFocus : true,
		delay : 900,
		minLength : 1,
		max : 10,
		source : function(request, response) {
			ajaxUtil({
				"CiorCName" : request.term + "%",
				"network" : "all_network"
			}, mainpath + "/outdoor/getCiOrCNameLike", function(_response) {
				response(_response.list);
			}, function(_response, reason) {
			});
		},
		select : function(event, ui) {
			var ci = ui.item.value;
			ui.item.value = ui.item.label;
			onCiSearch(ci);
		}
	});
}
function uploadInit(mainPath) {
	mainpath = mainPath;
	$("#upload").click(
			function() {
				// alert("up!");
				ajaxUploadUtil(mainpath + '/excelUpload.do', 'cellUpload',
						function(response) {
							alert(JSON.stringify(response));
							var filePath = response.filePath;
							ajaxUtil({
								"filePath" : filePath
							}, mainpath + '/cellAdmin/importWP', function(
									response) {
								alert("导入成功");
							}, function(response, reason) {
								alert(reason);
							});
						}, function(response, reason) {
							alert(reason);
						});
			});
	printWPList("#workparameterTable", "", {
		"ci" : -1
	}, 1, 10);
}
function onCiSearch(ci) {
	printWPList("#workparameterTable", "", {
		"ci" : ci
	}, 1, 10);
}
function printWPList(tableId, url, data, pageNo, pageSize) {
	ajaxUtil(
			{
				"startPage" : pageNo,
				"pageSize" : pageSize,
				"ciMayDefault" : data.ci
			},
			mainpath + "/cellAdmin/loadAllWP",
			function(response) {
				var list = response.list;
				var totalCount = response.totalCount;
				var table = $(tableId);
				var theHtml = "<thead>"
						+ "<tr class='active'><th><br>省份</br></th>"
						+ "<th><br>市地</br></th><th><br>地区</br></th><th>网络 <br>制式</br></th>"
						+ "<th><br>小区ID</br></th><th><br>LAC</br></th><th><br>eNodeB</br></th>"
						+ "<th><br>频段</br></th><th>英文<br>名称</br></th><th>中文<br>名称</br></th>"
						+ "<th>小区<br>地址</br></th><th>经度</th><th>纬度</th>"
						+ "<th>归属<br>区域</br></th><th>覆盖<br>场景</br></th><th>覆盖<br>类型</br></th><th><br>设置</br></th></tr>";
				theHtml += "<tbody>"
				for (var i = 0; i < list.length; i++) {
					theHtml += "<tr>";
					// theHtml += "<th scope='row'>" + (i + 1) + "</th>";
					theHtml += "<td>" + list[i].province + "</td>";
					theHtml += "<td>" + list[i].city + "</td>";
					theHtml += "<td>" + list[i].region + "</td>";
					theHtml += "<td>" + list[i].network + "</td>";
					theHtml += "<td>" + list[i].wpcid + "</td>";
					theHtml += "<td>" + list[i].lac + "</td>";
					theHtml += "<td>" + list[i].enodeb + "</td>";
					theHtml += "<td>" + list[i].freqChannel + "</td>";
					theHtml += "<td>" + list[i].cellNameEng + "</td>";
					theHtml += "<td>" + list[i].cellNameCh + "</td>";
					theHtml += "<td>" + list[i].cellAddress + "</td>";
					theHtml += "<td>" + list[i].longitude + "</td>";
					theHtml += "<td>" + list[i].latitude + "</td>";
					theHtml += "<td>" + list[i].belonging + "</td>";
					theHtml += "<td>" + list[i].scene + "</td>";
					theHtml += "<td>" + list[i].converageType + "</td>";
					theHtml += "<td><a href='javascript: modifyCell("
							+ list[i].ci
							+ ",\""
							+ list[i].network
							+ "\")'> <i class=\"glyphicon glyphicon-ban-circle\"></i>"
							+ "&nbsp;<span>修改</span></a>"
							+ " <br><a href='javascript: deleteCell("
							+ list[i].ci
							+ ",\""
							+ list[i].network
							+ "\")'> <i class=\"glyphicon glyphicon-trash\"></i> <span>删除</span>"
							+ "</a></br></td>";
					theHtml += "</tr>";
				}
				theHtml += "</tbody>";
				table.html(theHtml);
				var totalPage = Math.ceil(totalCount / pageSize);
				printPage(tableId, document.getElementById("paging"),
						totalPage, url, data, pageNo, pageSize, "printWPList");
				setIcRank();
			}, function(response, reason) {
				alert(reason);
			});
}

function modifyCell(ci, network) {
	alert(ci+" "+network)
	ajaxUtil({
		"ci" : ci,
		"network" : network
	}, mainpath + '/indoor/dataInSession', function(response) {
		window.location.href = mainpath + "/page/editCell";
	}, function(response, reason) {
		alert(reason);
	});

}
function deleteCell(ci, network) {

	ajaxUtil({
		"ci" : ci,
		"network" : network
	}, mainpath + '/cellAdmin/deleteCell', function(response) {
		alert("删除成功");
		window.location.href = mainpath + "/page/gongcanluru";
	}, function(response, reason) {
		alert(reason);
		window.location.href = mainpath + "/page/gongcanluru";
	});

}
