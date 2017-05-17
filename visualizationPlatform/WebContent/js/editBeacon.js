var mainpath;
$(function() {
	$("ul.main-menu li:eq(8)").addClass("active");
});
function init(_mainpath) {
	mainpath = _mainpath;

	ajaxUtil({}, mainpath + "/deviceAdmin/getModifiedBeacon",
			function(response) {
				var beaconInfo = response.beaconInfo;
				$("#id").val(beaconInfo.id);
				$("#building").val(beaconInfo.building);
				$("#inUse").val(beaconInfo.inUse);
				if (beaconInfo.id != null)
					$('#id').attr("disabled", "disabled");
				$("#description").val(beaconInfo.description);
				$("#x").val(beaconInfo.x);
				$("#y").val(beaconInfo.y);
				$("#floor").val(beaconInfo.floor);

				if (beaconInfo.inUse == 1) {
					$("input[name='inUse'][data-par='1']").attr("checked",
							'checked');
				} else {
					$("input[name='inUse'][data-par='0']").attr("checked",
							'checked');
				}
				/**室内地图的打点**/
				var canvas = document.getElementById("canvas");
				var ctx = canvas.getContext("2d");
				ctx.fillStyle="black";
				ctx.beginPath();
				ctx.arc(beaconInfo.x, beaconInfo.y, 4, 0, 2 * Math.PI);
				ctx.fill();
                var _canvas = $("#canvas");
				/**加载室内**/
				ajaxUtil({
					"buildingId" :beaconInfo.building,
					"floor" : beaconInfo.floor
				}, mainpath + "/indoor/mapUrl", function(response) {
					if (response.list != null && response.list.length > 0) {
						var url = response.list[0].url;
						if (canvas == null)
							return false;
						_canvas.css("backgroundImage", "url('/buildingMap/" + url + "')");
						_canvas.css("backgroundSize","100% 100%");//使图片适配容器大小
						canvas.addEventListener("click", function(evt) {//给canvas绑定鼠标点击事件
							var mousePos = getMousePos(canvas, evt);//获取鼠标点击位置
							var x = mousePos.x;
							var y = mousePos.y;
							ctx.clearRect($("#x").val()-5,$("#y").val()-5,10,10);
							ctx.fillStyle = "black";
							ctx.beginPath();
							ctx.arc(x, y, 4, 0, 2 * Math.PI);
							ctx.fill();
							$("#x").val(parseInt(x));
							$("#y").val(parseInt(y));
						}, false);
					} else {
						alert("改层尚未上传室内地图");
					}
				}, function(response, reason) {
					alert(reason);
				});
			}, function(response, reason) {
				alert(reason);

			});

	$("#submit").click(function() {
		submit();
	});
}
/** 获取鼠标点击在canvas的位置* */
function getMousePos(canvas, evt) {
	var rect = canvas.getBoundingClientRect();
	return {
		x : evt.clientX - rect.left * (canvas.width / rect.width),//正确转换
		y : evt.clientY - rect.top * (canvas.height / rect.height)
	}
	/**加载室内地图**/
	function loadLocatedMap(buildingId, floorId, mapId) {
		var building = $(buildingId).val();
		var floor = $(floorId).val();
		var reg = /^\-?[1-9]+$/;
		if (!(reg.test(building) && reg.test(floor))) {
			alert("请输入正确的楼层数  \n楼编号" + building + ",楼层号 " + floor);
			return;
		}
		var url;
		ajaxUtil({
			"buildingId" : building,
			"floor" : floor
		}, mainpath + "/indoor/mapUrl", function(response) {
			// update buildingMapList
			if (response.list != null && response.list.length > 0) {
				url = response.list[0].url;
				if (canvas == null)
					return false;
				_canvas.css("backgroundImage", "url('/buildingMap/" + url + "')");
				canvas.addEventListener("click", function(evt) {//给canvas绑定鼠标点击事件
					var mousePos = getMousePos(canvas, evt);//获取鼠标点击位置
					var x = mousePos.x;
					var y = mousePos.y;
					ctx.clearRect($("#x").val()-5,$("#y").val()-5,9,10);
					ctx.fillStyle = "black";
					ctx.beginPath();
					ctx.arc(x, y, 4, 0, 2 * Math.PI);
					ctx.fill();
					$("#x").val(parseInt(x));
					$("#y").val(parseInt(y));
				}, false);
			} else {
				alert("改层尚未上传室内地图");
			}
		}, function(response, reason) {
			alert(reason);
		});
	}
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
	var inUse = $("input[name='inUse']:checked").val();

	ajaxUtil({
		"id" : $("#id").val(),
		"building" : $("#building").val(),
		"inUse" : inUse,
		"description" : $("#description").val(),
		"x" : $("#x").val(),
		"y" : $("#y").val(),
		"floor" : $("#floor").val()

	}, mainpath + "/deviceAdmin/insertBeacon", function(response) {
		// alert(123);
		alert(response.msg);
		window.location.href = mainpath + "/page/beacon";
	}, function(response, reason) {
		alert(reason);

	});
	return false;
}
