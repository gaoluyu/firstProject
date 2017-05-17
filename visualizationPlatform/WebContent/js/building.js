var mainpath;
var cellType;
$(function() {
	$("ul.main-menu li:eq(9)").addClass("active");
});
function init(_mainpath) {
	mainpath = _mainpath;
	printBuildingList("#buildingList", "", {
		"id" : -1
	}, 1, 10);
}
/** 实时自动填充* */
function initBuildingSearch(_onBuildingSearch) {
	$("#buildingIdSearch").autocomplete({
		autoFocus : true,
		delay : 900,
		minLength : 1,
		max : 10,
		source : function(request, response) {
			ajaxUtil({
				"idOrCName" : request.term + "%"
			}, mainpath + "/deviceAdmin/getIdOrBNameLike", function(_response) {
				response(_response.list);
			}, function(_response, reason) {
			});
		},
		select : function(event, ui) {
			var id = ui.item.value;
			ui.item.value = ui.item.label;
			_onBuildingSearch(id);
		}
	});
}
function onBuildingSearch(id) {
	printBuildingList("#buildingList", "", {
		"id" : id
	}, 1, 10);
}
function ciDetail(id) {
	ajaxUtil({
		"buildingId" : id
	}, mainpath + '/deviceAdmin/buildingCi', function(response) {
		var list = response.list;
		/** 弹出的该楼的所有小区CI的表格内容* */
		var str = "<table style='font-size:10px'>";
		for (var i = 0; i < list.length; i++) {
			str += "<tr><td>" + list[i].ci + ":\n\n</td><td>" + list[i].network
					+ "</td></tr>"
		}
		str += "</table>";
		cellType = str;
		$("#" + "allType" + id).attr("data-content", cellType);// 加载弹出框的内容data-content
		$("#" + "allType" + id).popover('toggle');// 弹出或隐藏
	}, function(response, reason) {
		alert(reason);
	});
}
function printBuildingList(tableId, url, data, pageNo, pageSize) {
	ajaxUtil(
			{
				"startPage" : pageNo,
				"pageSize" : pageSize,
				"idMayDefault" : data.id
			},
			mainpath + "/deviceAdmin/loadAllBuilding",
			function(response) {
				var list = response.list;
				var totalCount = response.totalCount;

				var table = $(tableId);
				var theHtml = "<thead><tr class='active'>"
						+ "<th>楼编号</th><th>小区CI</th><th>省份</th><th>市地</th>"
						+ "<th>区域</th><th>单元</th><th>地上层数</th>"
						+ "<th>地下层数</th><th>命名</th><th>编辑</th></tr>"
						+ "</thead>";
				theHtml += "<tbody>"
				for (var i = 0; i < list.length; i++) {
					console.log(cellType);
					theHtml += "<tr>";
					theHtml += "<td><a href=\"javascript:beaconDetail("
							+ list[i].id + "," + list[i].unit + ")\">"
							+ list[i].id + "</a></td>";
					theHtml += "<td>"
							+ list[i].ci
							+ ""
							+ list[i].network
							+ "<br/><a role='button' tabindex='0' data-html='true' id='allType"
							+ list[i].id
							+ "' onclick='javascript:ciDetail("
							+ list[i].id
							+ ")' class='' data-container='body' data-toggle='popover' data-trigger='focus' data-placement='right' data-content='"
							+ cellType + "' >点击查看全部</a></td>";
					theHtml += "<td>" + list[i].province + "</td>";
					theHtml += "<td>" + list[i].city + "</td>";
					theHtml += "<td>" + list[i].region + "</td>";
					theHtml += "<td>" + list[i].unit + "</td>";
					theHtml += "<td>" + list[i].floorUpGround + "</td>";
					theHtml += "<td>" + list[i].floorUnderGround + "</td>";
					theHtml += "<td>" + list[i].name + "</td>";

					theHtml += "<td><a href='javascript: modifyBuilding("
							+ list[i].id
							+ ","
							+ list[i].unit
							+ ")'> <i class=\"glyphicon glyphicon-ban-circle\"></i>"
							+ "<span>修改</span></a>"
							+ " <a href='javascript: deleteBuilding("
							+ list[i].id
							+ ","
							+ list[i].unit
							+ ")'> <i class=\"glyphicon glyphicon-trash\"></i> <span>删除</span>"
							+ "</a></td>";
					theHtml += "</tr>";
				}
				theHtml += "</tbody>";
				table.html(theHtml);
				var totalPage = Math.ceil(totalCount / pageSize);
				printPage(tableId, document.getElementById("paging"),
						totalPage, url, data, pageNo, pageSize,
						"printBuildingList");
				setIcRank();
			}, function(response, reason) {
				alert(reason);
			});
}

function modifyBuilding(id, unit) {
	window.location.href = mainpath + "/page/editBuilding?id=" + id;
}
function beaconDetail(id, unit) {
	window.location.href = mainpath + "/page/beacon?id=" + id;
}

function deleteBuilding(id, unit) {
	ajaxUtil({
		"id" : id,
		"unit" : unit
	}, mainpath + '/deviceAdmin/deleteBuilding', function(response) {
		alert("删除成功");
		window.location.href = mainpath + "/page/building";
	}, function(response, reason) {
		alert(reason);
		window.location.href = mainpath + "/page/building";
	});
}