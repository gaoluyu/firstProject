var mainpath;
$(function() {
	$("ul.main-menu li:eq(9)").addClass("active");
});
function init(_mainpath) {
	mainpath = _mainpath;
	getProvince("#province", "#city");
	$("#submit").click(function() {
		submit();
	});
	$('#province').change(function() {
		getCity("#province", "#city");
	});

	ajaxUtil({}, mainpath + "/deviceAdmin/getModifiedBuilding", function(
			response) {
		var buildingInfo = response.buildingInfo;
		$("#province").val(buildingInfo.province);
		
		getCity("#province", "#city", buildingInfo.city);
		$("#id").val(buildingInfo.id);
		$("#region").val(buildingInfo.region);
		$("#floorUpGround").val(buildingInfo.floorUpGround);
		$("#floorUnderGround").val(buildingInfo.floorUnderGround);
		$("#name").val(buildingInfo.name);
		$("#unit").val(buildingInfo.unit);
$("#building_name").html(buildingInfo.name);
		ajaxUtil({
			"buildingId" : buildingInfo.id
		}, mainpath + "/deviceAdmin/buildingCi", function(response) {
			var list = response.list;
			for (i = 0; i < list.length; i++) {
				addCi("#ciBox", list[i].id, list[i].ci, list[i].network);
			}
		}, function(response, reason) {
			alert("拉取小区信息失败");

		});
	}, function(response, reason) {
		// alert("拉取floor信息失败");
		getProvince("#province", "#city");
	});

	showMapList('#mapBox');
}
function addSelectList(container, nameList, seletedItemName) {
	var theHtml = "";
	var sel = typeof (seletedItemName) == "undefined" ? null : seletedItemName;
	for (var index = 0; index < nameList.length; index++) {
		var selected = (sel == nameList[index] ? "selected" : "");
		theHtml += "<option value=\"" + nameList[index] + "\" " + selected
				+ ">" + nameList[index] + "</option>";
	}

	container.html(theHtml);
}
function getProvince(pselect, cselect) {
	// alert("search " + value.val());
	ajaxUtil({}, mainpath + "/cellAdmin/getProvince", function(response) {
		addSelectList($(pselect), response.list);
		getCity(pselect, cselect);
	}, function(reason, code) {
//		alert("code:\n" + code);
	});
}

function getCity(pselect, cselect, selectedCity) {
	ajaxUtil({
		"province" : $(pselect).val()
	}, mainpath + "/cellAdmin/getCity", function(response) {
		addSelectList($(cselect), response.list, selectedCity);
	}, function(reason, code) {
		alert("code:\n" + code);
	});
}
/**
 * 对字段进行合法性检查
 * 
 * @returns {Boolean}
 */
function check() {
	// var province = $("#province").val();
	// var city = $("#city").val();
	// var id = $("#id").val();
	// var region = $("#region").val();
	// var unit = $("#unit").val();
	// var floorUpGround = $("#floorUpGround").val();
	// var floorUnderGround = $("#floorUnderGround").val();
	// var name = $("#name").val();
	// if(checkUnit()==true&&checkfloorUp()&&checkfloorUnder()){}
	return true;
}
function submit() {
	if (!check()) {
		return false;
	}

	ajaxUtil({
		"province" : $("#province").val(),
		"city" : $("#city").val(),
		"id" : $("#id").val(),
		"region" : $("#region").val(),
		"unit" : $("#unit").val(),
		"floorUpGround" : $("#floorUpGround").val(),
		"floorUnderGround" : $("#floorUnderGround").val(),
		"name" : $("#name").val()
	}, mainpath + "/deviceAdmin/updateBuilding", function(response) {
		// alert(123);
		submitBuildingMap("#mapBox", response.msg);
		window.location.href = mainpath + "/page/building";

	}, function(response, reason) {
		alert(reason);
	});
	return false;
}
function submitBuildingCi(ciBox, msg) {
	ajaxUtil({
		"updateList" : getUpdateList(ciBox),
		"insertList" : getInsertList(ciBox, $("#id").val())
	}, mainpath + "/deviceAdmin/updateBuildingCi", function(response) {
//		alert(msg);
		// window.location.href = mainpath + "/page/building";
	}, function(response, reason) {
		alert(reason);
	});
}
function getUpdateList(ciBox) {
	var list = [];
	$(ciBox + " >div").each(function(index, domEle) {
		var id = $(domEle).find('input[name=buildingCiId]').val();
		var ci = $(domEle).find('input[name=ci]').val();
		var network = $(domEle).find('input[name=network]').val();
		// alert(id);
		if (id != 0) {
			var item = {
				"id" : id,
				"network" : network,
				"ci" : ci
			};
			list.push(item);
		}
	});
	return JSON.stringify(list);
}
function getInsertList(ciBox, buildingId) {
	var list = [];
	$(ciBox + " >div").each(function(index, domEle) {
		var id = $(domEle).find('input[name=buildingCiId]').val();
		var ci = $(domEle).find('input[name=ci]').val();
		var network = $(domEle).find('input[name=network]').val();
		// alert(index);
		if (id == 0) {
			var item = {
				"buildingId" : buildingId,
				"network" : network,
				"ci" : ci
			};
			list.push(item);
		}

	});
	return JSON.stringify(list);
}
function addCi(ciBox, bci, ci, nt) {
	var uid = ("" + ((new Date()).getTime())).trim();
	var theHtml = '<div id="' + uid + '" class="controls">';
	theHtml += '<input name="buildingCiId" type="hidden" value="' + bci
			+ '" /> <input name="ci"' + 'type="text" value="' + ci
			+ '"placeholder="小区CI" class="input-xlarge">';
	theHtml += '&nbsp<input name="network" type="text" placeholder="网络类型" value="'
			+ nt
			+ '"'
			+ 'class="input-xlarge"><a href="javascript:deleteCi(\'#'
			+ uid
			+ '\');"><i class="glyphicon glyphicon-trash">&nbsp</i></a></div><br/>';
	$(ciBox).append(theHtml);
}
function deleteCi(ciBoxId) {
	var dId = $(ciBoxId).find('input[name=buildingCiId]').val();
	if (dId != 0) {
		ajaxUtil({
			"buildingId" : dId
		}, mainpath + "/deviceAdmin/deleteBuildingCi", function(response) {
			$(ciBoxId).remove();
			alert("删除成功");
		}, function(response, reason) {
			alert(reason);
		});
	} else {
		$(ciBoxId).remove();
	}
}

function showMapList(mapBoxId, buildingId) {
	ajaxUtil({
		"buildingId" : $("#id").val(),
		"floor":0
	}, mainpath + "/indoor/mapUrl", function(response) {
		var list = response.list;
		$(mapBoxId).html("");
		for (var i = 0; i < list.length; i++)
			addMap(mapBoxId, list[i].floor, list[i].url);
	}, function(response, reason) {
		alert(reason);
	});
}

function addMap(mapBox, floor, url) {
	var uid = ("" + ((new Date()).getTime())).trim();
	var picName = url.substring(0, url.indexOf("."));
	var theHtml = '<td><div id="' + uid + '" class="controls" >';
	theHtml += '楼层：<input name="mapFloor"'
			+ 'type="text" value="'
			+ floor
			+ '"placeholder="" class="input-xlarge" style="width:20px; margin:10px 20px 5px 10px"><br/>';

	theHtml += '<a href="javascript:deleteMap(\'#' + uid + '\')">删除</a>';
	theHtml += "<div class=\"pt_con\" id=\"pic_" + picName + "\">";
	theHtml += "<input name='picUrl' type='hidden' value=\"" + url + "\" />";
	if (url.trim().length == 0)
		theHtml += "	<p class=\"pt\"><img src=\"\" alt=\"\" /></p>";
	else
		theHtml += "	<p class=\"pt\"><img src=\"/buildingMap/" + url
				+ "\" alt=\"\" /></p>";
	theHtml += "	<p class=\"wz\"><input name='file' id='fileId_"
			+ picName
			+ "' type=\"file\" onchange=\"uploadMap('fileId_"
			+ picName
			+ "', '#pic_"
			+ picName
			+ "')\" class=\"compile_file\" /><span class=\"compile_btn\">编辑</span></p>";
	theHtml += "</div>";
	theHtml += "</div></td>";
	$(mapBox).append(theHtml);
}
function deleteMap(uid) {
	var mapDiv = $(uid);
	var buildingId = $("#id").val();
	var floor = mapDiv.find('input[name=mapFloor]').val();
	var picUrl = mapDiv.find('input[name=picUrl]').val();

	if (!isNaN(floor) && picUrl.trim().length != 0) {
		var item = {
			"floor" : floor,
			"buildingId" : buildingId
		};
		ajaxUtil({
			"floor" : floor,
			"buildingId" : buildingId
		}, mainpath + "/deviceAdmin/deleteBuildingMap", function(response) {
			mapDiv.remove();
		}, function(response, reason) {
			alert(reason);
		});
	}else{
		mapDiv.remove();
	}

}
function uploadMap(fileId, picId) {
	ajaxUploadUtil(
			mainpath + "/upload.do",
			fileId,
			function(response) {
				var url = response.url;
				var holderDiv = $(picId);
				var picName = url.substring(0, url.indexOf("."));
				holderDiv.attr('id', 'pic_' + picName);
				var theHtml = "";
				theHtml += "<input name='picUrl' type='hidden' value=\"" + url
						+ "\" />";
				theHtml += "	<p class=\"pt\"><img src=\"/buildingMap/" + url
						+ "\" alt=\"\" /></p>";
				theHtml += "	<p class=\"wz\"><input name='img' id='fileId_"
						+ picName
						+ "' type=\"file\" onchange=\"uploadMap('fileId_"
						+ picName
						+ "', '#pic_"
						+ picName
						+ "')\" class=\"compile_file\" /><span class=\"compile_btn\">编辑</span></p>";
				holderDiv.html(theHtml);
			}, function(response, reason) {
				alert(reason);
			});
}

function getMapCommitString(mapBox, buildingId) {
	var list = [];
	$(mapBox + " >td").each(function(index, domEle) {
		var floor = $(domEle).find('input[name=mapFloor]').val();
		var picUrl = $(domEle).find('input[name=picUrl]').val();
//		alert(index);
		if (!isNaN(floor) && picUrl.trim().length != 0) {
			var item = {
				"floor" : floor,
				"url" : picUrl,
				"buildingId" : buildingId
			};
			list.push(item);
		}
	});
	return JSON.stringify(list);
}
function submitBuildingMap(mapBox, msg) {
	ajaxUtil({
		"updateList" : getMapCommitString(mapBox, $("#id").val())
	}, mainpath + "/deviceAdmin/updateBuildingMap", function(response) {
		submitBuildingCi("#ciBox", msg);
	}, function(response, reason) {
		alert(reason);
	});
}