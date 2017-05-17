var mainpath;

function init(_mainpath) {
	mainpath = _mainpath;
	getProvince("#province", "#city");
	$("#submit").click(function() {
		submit();
	});
	$('#province').change(function() {
		getCity("#province", "#city");
		console.log("huoqu");
	});

}
function addSelectList(container, nameList) {
	var theHtml = "";
	for (var index = 0; index < nameList.length; index++)
		theHtml += "<option value=\"" + nameList[index] + "\">"
				+ nameList[index] + "</option>";
	container.html(theHtml);
}
function getProvince(pselect, cselect) {
	// alert("search " + value.val());
	ajaxUtil({}, mainpath + "/cellAdmin/getProvince", function(response) {
		addSelectList($(pselect), response.list);
		getCity(pselect, cselect);
	}, function(reason, code) {
		alert("code:\n" + code);
	});
}
function getCity(pselect, cselect) {
	// alert("search " + value.val());
	ajaxUtil({
		"province" : $(pselect).val()
	}, mainpath + "/cellAdmin/getCity", function(response) {
		addSelectList($(cselect), response.list);
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
	
	var province = $("#province").val();
	var city = $("#city").val();
	var id = $("#id").val();
	var region = $("#region").val();
	var unit = $("#unit").val();
	var floorUpGround = $("#floorUpGround").val();
	var floorUnderGround = $("#floorUnderGround").val();
	var name = $("#name").val();
	if (province == null || city == null || id == null || region == null
			|| unit == null || floorUpGround == null
			|| floorUnderGround == null || name == null) {
    alert("您有未填选项，请填写~");
    return false;
	}
	return true;
}
function submit() {
	if (!check()) {
		return false;
	}
	ajaxUtil({
		"province" : $("#province").val(),
		"city" : $("#city").val(),
		"ci" : $("#ci").val(),
		"region" : $("#region").val(),
		"unit" : $("#unit").val(),
		"floorUpGround" : $("#floorUpGround").val(),
		"floorUnderGround" : $("#floorUnderGround").val(),
		"name" : $("#name").val()
	}, mainpath + "/deviceAdmin/insertBuilding", function(response) {
		alert(response.msg);
		window.location.href = mainpath + "/page/building";
	}, function(response, reason) {
		alert(reason);
	});
	return false;
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
			"buildingCiId" : dId
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
function addMap(mapBox, floor, url) {
	var uid = ("" + ((new Date()).getTime())).trim();
	var picName = url.substring(0, url.indexOf("."));
	var theHtml = '<td><div id="' + uid + '" class="controls" >';
	theHtml += '楼层：<input name="mapFloor"'
			+ 'type="text" value="'
			+ floor
			+ '"placeholder="" class="input-xlarge" style="width:20px; margin:10px 20px 5px 10px"><br/>';

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
