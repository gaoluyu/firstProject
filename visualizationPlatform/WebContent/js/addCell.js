var mainpath;
$(function() {
	$("ul.main-menu li:eq(8)").addClass("active");
});
function addInit(_mainpath) {
	mainpath = _mainpath;
	getProvince("#province", "#city");
	$("#submit").click(function() {
		submit();
	});
	$('#province').change(function() {
		getCity("#province", "#city");
	});
	networkChanged();

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
		// alert(123);
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