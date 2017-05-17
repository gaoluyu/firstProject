var mainpath;
$(function() {
	$("ul.main-menu li:eq(8)").addClass("active");
});
function editInit(_mainpath) {

	mainpath = _mainpath;
	getProvince("#province", "#city");
	$("#submit").click(function() {
		submit();
	});
	$('#province').change(function() {
		getCity("#province", "#city");
	});
	alert(1);
	ajaxUtil({}, mainpath + "/cellAdmin/getModifiedCell", function(response) {
		var cellInfo = response.cellInfo;
		// alert(1+" " +cellInfo.province);
		$("#province").val(cellInfo.province);

		// $("#province option[value='河南']").attr("selected", "selected");
		getCity("#province", "#city", cellInfo.city);
		$("#network").val(cellInfo.network);
		$("#region").val(cellInfo.region);
		$("#ci").val(cellInfo.wpcid);
		$("#enodeb").val(cellInfo.enodeb);
		if (cellInfo.wpcid != null)
			$('#ci').attr("disabled", "disabled");
		$("#lac").val(cellInfo.lac);
		$("#freqChannel").val(cellInfo.freqChannel);
		$("#cellNameEng").val(cellInfo.cellNameEng);
		$("#cellNameCh").val(cellInfo.cellNameCh);
		$("#cellAddress").val(cellInfo.cellAddress);
		$("#scene").val(cellInfo.scene);
		var long = cellInfo.longitudeMap;
		var lati = cellInfo.latitudeMap;
		if (long == 0 || lati == 0) {
			long = cellInfo.longitude;
			lati = cellInfo.latitude;
		}

		$("#lng").val(long);
		$("#lat").val(lati);
		$("#belonging").val(cellInfo.belonging);
		if (cellInfo.isIndoor == 1) {
			$("input[name='isIndoor'][data-par='1']")
					.attr("checked", 'checked');
		} else {
			$("input[name='isIndoor'][data-par='0']")
					.attr("checked", 'checked');
		}
		initMarker();
		networkChanged();
		disableValue();
	}, function(response, reason) {
		getProvince("#province", "#city");
	});
}
function disableValue() {
	$("#province").attr("disabled", "disabled");
	$("#city").attr("disabled", "disabled");
	$("#network").attr("disabled", "disabled");
	$("#ci").attr("disabled", "disabled");
	$("#enodeb").attr("disabled", "disabled");
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
	ajaxUtil({}, mainpath + "/cellAdmin/getProvince", function(response) {
		addSelectList($(pselect), response.list);
		getCity(pselect, cselect);
	}, function(reason, code) {
		alert("code:\n" + code);
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
function networkChanged() {
	var net = $("#network").val();
	if (net.indexOf("4G") != -1)
		$("#enodebDiv").show();
	else
		$("#enodebDiv").hide();
}
/**
 * 对字段进行合法性检查
 * 
 * @returns {Boolean}
 */
function check() {
	return true;
}

function submit() {
	if (!check()) {
		return false;
	}
	var isIndoor = $("input[name='isIndoor']:checked").val();
	// alert(isIndoor);
	var converageType = isIndoor == 1 ? '室内' : '室外';

	ajaxUtil({
		"province" : $("#province").val(),
		"city" : $("#city").val(),
		"network" : $("#network").val(),
		"region" : $("#region").val(),
		"wpcid" : $("#ci").val(),
		"ci" : getCi(),
		"enodeb" : getEnodeb(),
		"lac" : $("#lac").val(),
		"freqChannel" : $("#freqChannel").val(),
		"cellNameEng" : $("#cellNameEng").val(),
		"cellNameCh" : $("#cellNameCh").val(),
		"cellAddress" : $("#cellAddress").val(),
		"longitude" : $("#lng").val(),
		"latitude" : $("#lat").val(),
		"longitudeMap" : $("#lng").val(),
		"latitudeMap" : $("#lat").val(),
		"scene" : $("#scene").val(),
		"belonging" : $("#belonging").val(),
		"isIndoor" : isIndoor,
		"converageType" : converageType
	}, mainpath + "/cellAdmin/insertCell", function(response) {
		alert($("#lat").val());
		alert(response.msg);
		window.location.href = mainpath + "/page/gongcanluru";
	}, function(response, reason) {
		alert(reason);

	});
	return false;
}
function getEnodeb() {
	var net = $("#network").val();
	if (net.indexOf("4G") != -1) {
		var enodeb = $("#enodeb").val();
		if (enodeb.length == 0)
			enodeb = 0;
		return parseInt(enodeb);
	} else
		return 0;
}
function getCi() {
	var net = $("#network").val();
	if (net.indexOf("4G") != -1) {
		return getEnodeb() * 256 + parseInt($("#ci").val());
	} else {
		return $("#ci").val()
	}
}