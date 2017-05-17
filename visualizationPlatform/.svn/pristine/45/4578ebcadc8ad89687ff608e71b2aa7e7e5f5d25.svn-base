<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=bQsQswt4w4diFb2BXoODAZco"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<script type="text/javascript" src="/androidServer/js/utils.js"></script>
<script src="/androidServer/js/jquery-1.11.2.min.js"></script>
<title>热力图功能示例</title>
<style type="text/css">
ul, li {
	list-style: none;
	margin: 0;
	padding: 0;
	float: left;
}

html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px;
	font-family: "微软雅黑";
}

#container {
	height: 90%;
	width: 100%;
}

#r-result {
	width: 100%;
}
</style>
<script type="text/javascript">
	function submit(action) {
		alert("exe");
		window.location.href = "./heat/" + action;
	}
	//submit("generate");
</script>
<style type="text/css">
#btn_style {
	float: left;
	position: absolute;
	margin: 5px 0 5px 10px;
}

#info_style {
	float: left;
	position: absolute;
	margin: 5px 0 5px 30%;
}

#ul_h {
	list-style: none;
	float: left;
	margin: 0px;
}

#ul_h li {
	float: left;
}

#ul_v li {
	float: none;
}

table {
	border-collapse: collapse;
	border: none;
}

td {
	border: solid #000 1px;
}
</style>
</head>
<body>
	<div id="container"></div>
	<div id="control_panel">
		<div id="btn_style"></div>
		<div id="info_style">
			<ul id="ul_h">
				<li>
					<ul id="ul_v">

					</ul>
				</li>
				<li>
					<!-- 					<table>
						<tr>
							<td>信号强度</td>
							<td>&ge;${gradient0}dbm</td>
							<td>&ge;${gradient1}dbm</td>
							<td>&ge;${gradient2}dbm</td>
							<td>&ge;${gradient3}dbm</td>
							<td>&ge;${gradient4}dbm</td>
						</tr>
						<tr>
							<td>颜色</td>
							<td style="background-color: rgb(0, 0, 255)">&nbsp;</td>
							<td style="background-color: rgb(0, 255, 255)">&nbsp;</td>
							<td style="background-color: rgb(0, 255, 0)">&nbsp;</td>
							<td style="background-color: yellow">&nbsp;</td>
							<td style="background-color: rgb(255, 0, 0)">&nbsp;</td>
						</tr>
					</table> -->

				</li>
				<li>current operator:${requestScope.currentOperator }</li>
			</ul>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
    var map = new BMap.Map("container",{minZoom:5,maxZoom:18});
    var getPosition=true;
    var setLng=-1;
    var setLat=-1;
    if(setLng==-1){
    	 setLng=116.365459;
    	 getPosition=false;
    }    
    if(setLat==-1){
    	setLat=39.968429;
		getPosition=false;
    }
    var point = new BMap.Point(setLng, setLat);
    if(getPosition){
    	var marker = new BMap.Marker(point);        // 创建标注    
    	map.addOverlay(marker); 
    }
    map.centerAndZoom(point, 10);             // 初始化地图，设置中心点坐标和地图级别
    map.enableScrollWheelZoom(); // 允许滚轮缩放
	var radiusInZoom18=20;
    var points =[{"lng":116.365459 ,"lat":39.968429,"count":100}
              ];
    
    /*
    map.addEventListener("click", function(e){    
    	alert(e.point.lng + ", " + e.point.lat);    
    	//writeFile("d:/points.txt",'{"lng":'+e.point.lng+',"lat":'+e.point.lat+',"count":30},')
    });*/
    if(!isSupportCanvas()){
    	alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
    }
	//详细的参数,可以查看heatmap.js的文档 https://github.com/pa7/heatmap.js/blob/master/README.md
	//参数说明如下:
	/* visible 热力图是否显示,默认为true
     * opacity 热力的透明度,1-100
     * radius 势力图的每个点的半径大小   
     * gradient  {JSON} 热力图的渐变区间 . gradient如下所示
     *	{
			.2:'rgb(0, 255, 255)',
			.5:'rgb(0, 110, 255)',
			.8:'rgb(100, 0, 255)'
		}
		其中 key 表示插值的位置, 0~1. 
		    value 为颜色值. 
     */
	heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":radiusInZoom18});
	map.addOverlay(heatmapOverlay);
	heatmapOverlay.setDataSet({data:points,max:100});
	//是否显示热力图
    function openHeatmap(){
        heatmapOverlay.show();
    }
	function closeHeatmap(){
        heatmapOverlay.hide();
    }
	openHeatmap();
	map.centerAndZoom(point, 18);	
	//5-InitialRadius*2+5
	var pixel1=new BMap.Pixel(5,5);
	var pixel2=new BMap.Pixel(radiusInZoom18+5,5);
	var point1=map.pixelToPoint(pixel1);
	var point2=map.pixelToPoint(pixel2);
	
	map.addEventListener("zoomend",function(){
		var zoomedLevel=this.getZoom();

		var _p1=map.pointToPixel(point1);
		var _p2=map.pointToPixel(point2);
		var newRadius=_p2.x-_p1.x;
		/*
		if(zoomedLevel==18||zoomedLevel==100||zoomedLevel==100){
			newRadius=radiusInZoom18;
			setGradient(1);
		}else if(zoomedLevel>14){
			newRadius=newRadius+6;
			setGradient(1);
		}else if(zoomedLevel<=14){
			newRadius=2;
		} */

		if(newRadius<=0)
			newRadius=1;
	//	alert(newRadius+"zoomedLevel "+zoomedLevel);
		//alert("zoom="+zoomedLevel+",radius="+newRadius);
		//alert("坐标差："+(point2.lng-point1.lng)+"\nzoomLevel:"+zoomedLevel+"\n新半径:"+newRadius);
		//newRadius=radiusInZoom18+(zoomedLevel-18)*5;
		heatmapOverlay.setOptions({"radius":newRadius});
		
	});
    function setGradient(type){
     	/*格式如下所示:
		{
	  		0:'rgb(102, 255, 0)',
	 	 	.5:'rgb(255, 170, 0)',
		  	1:'rgb(255, 0, 0)'
		}*/
		var gradient;
		if(type==1){
			gradient= {
				//0.45: "rgb(0,0,255)", 0.55: "rgb(0,255,255)", 0.65: "rgb(0,255,0)", 0.95: "yellow", 1.0: "rgb(255,0,0)"
					//0.45: "rgb(0,0,255)", 0.55: "rgb(0,255,255)", 0.65: "rgb(0,255,0)",0.85: "yellow", 0.93: "rgb(255,0,0)"
					0.45: "rgb(0,0,255)", 0.55: "rgb(0,255,255)", 0.65: "rgb(0,255,0)",0.93: "yellow", 0.97: "rgb(255,0,0)"
		};
		}else if(type==2){
			gradient = {
				0.3: "rgb(0,0,255)", 0.4: "rgb(0,255,255)", 0.5: "rgb(0,255,0)", 0.5: "yellow", 0.55: "rgb(255,0,0)"
		};
		}else if(type==3){
			gradient = {
					0.45: "rgb(0,0,255)", 0.55: "rgb(0,255,255)", 0.65: "rgb(0,255,0)",0.85: "yellow", 0.92: "rgb(255,0,0)"
			};
		}
        heatmapOverlay.setOptions({"gradient":gradient});
    }
	setGradient(3);
	map.centerAndZoom(point, 17);	
	//判断浏览区是否支持canvas
    function isSupportCanvas(){
        var elem = document.createElement('canvas');
        return !!(elem.getContext && elem.getContext('2d'));
    }
	
	ajaxUtil({},"/androidServer/outdoor/strengthHeat", function(response){
		var p=[];
		var list=response.list;
		for(var i=0;i<list.length;i++){
			var item={"lng":list[i].lng,"lat":list[i].lat,"count":list[i].value};
			p.push(item);
		}	 
		heatmapOverlay.setDataSet({data:p,max:100});


	}, function(response,reason){});
</script>
<script type="text/javascript">

</script>