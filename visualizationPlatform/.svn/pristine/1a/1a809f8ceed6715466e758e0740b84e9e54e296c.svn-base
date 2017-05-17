var mainpath;
$(function() {
	$("ul.main-menu li:eq(8)").addClass("active");
});
function init(_mainpath) {
	mainpath = _mainpath;
	printBeaconList("#beaconList", "", "", 1, 10);

}
function printBeaconList(tableId, url, data, pageNo, pageSize) {
	ajaxUtil(
			{
				"startPage" : pageNo,
				"pageSize" : pageSize
			},
			mainpath + "/deviceAdmin/getBeaconList",
			function(response) {
				var list = response.list;
				var totalCount = response.totalCount;
				var table = $(tableId);
				var theHtml = "<thead>"
						+ "<tr><th>楼编号</th><th>楼层</th><th>MAC</th>"
						+ "<th>状态</th><th>描述</th><th>x</th>"
						+ "<th>y</th><th>编辑</th></tr>" + "</thead>";
				theHtml += "<tbody>"
				for (var i = 0; i < list.length; i++) {
					theHtml += "<tr>";
					theHtml += "<td>" + list[i].building + "</td>";
					theHtml += "<td>" + list[i].floor + "</td>";
					theHtml += "<td>" + list[i].id + "</td>";
					theHtml += "<td>" + list[i].inUse + "</td>";
					theHtml += "<td>" + list[i].description + "</td>";
					theHtml += "<td>" + list[i].x + "</td>";
					theHtml += "<td>" + list[i].y + "</td>";

					theHtml += "<td>"
							+ "<a href=\"javascript:modifyBeacon('"
							+ list[i].id
							+ "')\">"
							+ "<i class=\"glyphicon glyphicon-ban-circle\"></i> <span>修改</span></a> "
							+ "<a href=\"javascript:deleteBeacon('"
							+ list[i].id
							+ "',"
							+ list[i].building
							+ ",'"
							+ list[i].unit
							+ "')\">"
							+ "<i class=\"glyphicon glyphicon-trash\"></i> <span>删除</span></a>"
							+ "</td>";
					theHtml += "</tr>";
				}
				theHtml += "</tbody>";
				table.html(theHtml);
				var totalPage = Math.ceil(totalCount / pageSize);
				printPage(tableId, document.getElementById("paging"),
						totalPage, url, data, pageNo, pageSize,
						"printBeaconList");
				setIcRank();
			}, function(response, reason) {
				alert(reason);
			});
}

function modifyBeacon(mac) {
	window.location.href = mainpath + "/page/editBeacon?mac=" + mac;
}

function deleteBeacon(mac, builindgId, unit) {
	ajaxUtil({
		"mac" : mac
	}, mainpath + '/deviceAdmin/deleteBeacon', function(response) {
		alert("删除成功");
		window.location.href = mainpath + "/page/beacon?id=" + builindgId
				+ "&unit=" + unit;
	}, function(response, reason) {
		alert(reason);
		// window.location.href = mainpath + "/page/building";
	});
}