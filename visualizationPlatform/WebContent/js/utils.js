function ajaxUtil(requestdata, ajaxurl, succFunction, failFunction) {
	if (("startTime" in requestdata) && requestdata.startTime == null) {
		alert("请选择开始时间");
		return;
	}
	if (("endTime" in requestdata) && requestdata.endTime == null) {
		alert("请选择结束时间");
		return;
	}
	$.ajax({
		url : ajaxurl,
		type : "POST",
		dataType : "json",
		cache : false,
		data : requestdata,
		async : false,
		success : function(response) {
			if (response.status >= 0) {
				if (succFunction != undefined && succFunction != null)
					succFunction(response);
			} else {
				// alert("ajax request error");
				if (response.status == -3) {

				}
				if (response.status == -101) {

				}
				if (failFunction != undefined && failFunction != null)
					failFunction(response, response.reason);
			}
		},
		error : function(x, e) {
			// alert("error", x);
		},
		complete : function(x) {
		}
	});
	return false;
}

function ajaxUploadUtil(ajaxurl, elementId, succFunction, failFunction) {
	$.ajaxFileUpload({
		url : ajaxurl, // submit to UploadFileServlet
		secureuri : false,
		fileElementId : elementId,// 文件选择框的id属性
		dataType : 'text/html', // or json xml whatever you like~
		success : function(response, status) {
			var reg = /<pre.+?>(.+)<\/pre>/g;
			var result = response.match(reg);
			var dataObj = eval("(" + RegExp.$1 + ")");
			// alert(dataObj);
			if (dataObj.status > 0) {
				if (succFunction != undefined && succFunction != null)
					succFunction(dataObj);
			} else {
				alert("error code :" + dataObj.status);

				if (failFunction != undefined && failFunction != null)
					failFunction(dataObj, dataObj.reason);
			}
		},
		error : function(x, e) {
			alert("error fileupload", x);
		}
	});
}
/**
 * 
 * @param panel
 *            用于显示列表的容器
 * @param container
 *            用于显示分页按钮的容器
 * @param count
 *            共有多少分页
 * @param pageNo
 *            当前第几页
 * @param pageSize
 *            每页显示多少条
 * @param funcName
 *            点击分页后的函数
 */
function printPage(panel, container, count, url, data, pageNo, pageSize,
		funcName) {
	var container = container;
	var pageindex = pageNo;
	var pageLink = "javascript:" + funcName + "('" + panel + "','" + url
			+ "','" + data + "', #pageNo, " + pageSize + ")";
	var a = [];
	// 总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
	if (pageindex == 1) {
		a[a.length] = "<a href=\"#\" class=\"prev unclick\">前一页</a>";
	} else {
		a[a.length] = "<a href=\"" + pageLink.replace("#pageNo", pageNo - 1)
				+ "\" class=\"prev\">前一页</a>";
	}
	function setPageList() {
		if (pageindex == i) {
			a[a.length] = "<a href=\"" + pageLink.replace("#pageNo", i)
					+ "\" class=\"current\">" + i + "</a>";
		} else {
			a[a.length] = "<a href=\"" + pageLink.replace("#pageNo", i) + "\">"
					+ i + "</a>";
		}
	}
	// 总页数小于10
	if (count <= 10) {
		for (var i = 1; i <= count; i++) {
			setPageList();
		}
	}
	// 总页数大于10页
	else {
		if (pageindex <= 4) {
			for (var i = 1; i <= 5; i++) {
				setPageList();
			}
			a[a.length] = "...<a href=\"" + pageLink.replace("#pageNo", count)
					+ "\">" + count + "</a>";
		} else if (pageindex >= count - 3) {
			a[a.length] = "<a href=\"" + pageLink.replace("#pageNo", 1)
					+ "\">1</a>...";
			for (var i = count - 4; i <= count; i++) {
				setPageList();
			}
		} else { // 当前页在中间部分
			a[a.length] = "<a href=\"" + pageLink.replace("#pageNo", 1)
					+ "\">1</a>...";
			for (var i = pageindex - 2; i <= pageindex + 2; i++) {
				setPageList();
			}
			a[a.length] = "...<a href=\"" + pageLink.replace("#pageNo", count)
					+ "\">" + count + "</a>";
		}
	}
	if (pageindex == count) {
		a[a.length] = "<a href=\"#\" class=\"next unclick\">后一页</a>";
	} else {
		a[a.length] = "<a href=\""
				+ pageLink.replace("#pageNo", parseInt(pageNo) + 1)
				+ "\" class=\"next\">后一页</a>";
	}
	// alert(a.join(""));
	container.innerHTML = a.join("");
	// 事件点击
}
function setIcRank() {
	$(".ic_rank").click(function() {
		if ($(this).attr("class") == "ic_rank") {
			$(this).parents("tr").find(".ic_rank").attr("class", "ic_rank");
			$(this).addClass("rank_t");
		} else if ($(this).attr("class") == "ic_rank rank_t") {
			$(this).addClass("rank_d");
		} else if ($(this).attr("class") == "ic_rank rank_t rank_d") {
			$(this).removeClass("rank_d");
		}
	});
}
