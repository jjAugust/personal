<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html><html class=''>
<head><meta charset='UTF-8'>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    %>
    <base href="<%=basePath %>" />
    <script src="js/jquery.min.js"></script>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#allmap {height:500px; width: 100%;}
		#control{width:100%;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=Kq0VzqPxRvId3jYAjuTVLcbluYige9s4"></script>
	<script type="text/javascript" src="js/aftercluster2.0.js"></script>
	<!--
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<script type="text/javascript">
	 var userAK = 'Kq0VzqPxRvId3jYAjuTVLcbluYige9s4';
	 var userTableID = 162150;
</script> -->

	<title>TITLE?</title>
</head>
<body>
	
	<div id="control">
		<input type="text" id="input1" name="fname" onkeypress="getKey();"/>
	</div>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	
	/*
	var point = new BMap.Point(116.403694, 39.927552); // 创建点坐标
	var options = {
		renderOptions: {
			map: map
		},
		onSearchComplete: function(results) {
			//alert('Search Completed');
			//可添加自定义回调函数
		}
	};
	var localSearch = new BMap.LocalSearch(map, options);
	map.addEventListener("load", function() {
		var circle = new BMap.Circle(point, 500000000, {
			fillColor: "blue",
			strokeWeight: 1,
			fillOpacity: 0.3,
			strokeOpacity: 0.3
		});		
		//map.addOverlay(circle);
		localSearch.searchNearby('Cluster', point, 500000000, {
			customData: {
				geotableId: 162150
			}
		});
	});

	map.enableScrollWheelZoom();
	map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件
	*/
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 10);

	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
	//缩放控件type有四种类型:	BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CON按钮；BMAP_NAVIGAT
	//添加控件和比例尺
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);     
	map.addControl(top_right_navigation);
		
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 10);
	map.enableScrollWheelZoom();

	function getKey()  
	{  
		if(event.keyCode==13){  
			
			var map = new BMap.Map("allmap");
			map.centerAndZoom(new BMap.Point(116.404, 39.915), 10);

			var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
			var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
			var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
			//缩放控件type有四种类型:	BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CON按钮；BMAP_NAVIGAT
			//添加控件和比例尺
			map.addControl(top_left_control);        
			map.addControl(top_left_navigation);     
			map.addControl(top_right_navigation);
				
			map.centerAndZoom(new BMap.Point(116.404, 39.915), 10);
			map.enableScrollWheelZoom();
	
			var a = document.getElementById('input1').value;
			var result=a.split(",");
			var res_length=result.length;
			var i=0;
			//alert(Arr[0][2]);
			for(;i<res_length-1;i++){//第i个输入点
				a0 = result[i]-1;//Arr数组起始num为1，非0
				a1 = result[i+1]-1;
				
				var marker = new BMap.Marker(new BMap.Point(Arr[a0][2], Arr[a0][1])); // 创建点
				map.addOverlay(marker);            //增加点
				var label = new BMap.Label("Cluster"+Arr[a0][0]+"#",{offset:new BMap.Size(20,-10)});
				marker.setLabel(label);
				
				var marker = new BMap.Marker(new BMap.Point(Arr[a1][2], Arr[a1][1])); // 创建点
				map.addOverlay(marker);            //增加点
				var label = new BMap.Label("Cluster"+Arr[a1][0]+"#",{offset:new BMap.Size(20,-10)});
				marker.setLabel(label);
	
				var polyline = new BMap.Polyline([
					new BMap.Point(Arr[a0][2], Arr[a0][1]),
					new BMap.Point(Arr[a1][2], Arr[a1][1])
				], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});   //创建折线
				map.addOverlay(polyline);   //增加折线
			}
			b0 = result[res_length-2]-1;
			b1 = result[res_length-1]-1;
			//var marker = new BMap.Marker(new BMap.Point(Arr[b1][2], Arr[b1][1])); // 创建点
				//map.addOverlay(marker);            //增加点
			var polyline = new BMap.Polyline([
				new BMap.Point(Arr[b0][2], Arr[b0][1]),
				new BMap.Point(Arr[b1][2], Arr[b1][1])
			], {strokeColor:"red", strokeWeight:2, strokeOpacity:0.5});   //创建折线
			map.addOverlay(polyline);   //增加折线
		}     
	}  
	
	
	
	
	

</script>

