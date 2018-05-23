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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=M2fCcQ8stMC84gOtahIUI644"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<title>服务推荐</title>
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
select{
	height: 35px;
	width: 85px;
	padding-left: 8px;
	font-size: 14px;
}

.submit{
	height: 30px;
	margin-left: -3px;
	width: 60px;
}

#allmap{
	position: absolute;
	top: 0px;
}
</style>
</head>
<body>
<form method="POST" action="" style="position: absolute;z-index: 100;margin-left: 15px;" onsubmit="return keyshow()">  
        <select name="key" id="selectValue">   
        <option value="餐馆">餐馆</option>  
		<option value="商场">商场</option>  
		<option value="停车场">停车场</option> 	
		<option value="超市">超市</option> 		
        <option value="宾馆">宾馆</option>   
		</select>
        <input class="submit" type="submit" value="确认" >  
</form>  
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
$("#selectValue").val(parent.window.location.hash.substr(1) || "餐馆")
function keyshow(){
	var selected = $("#selectValue").val();
	parent.window.location.hash = selected;
}


// 百度地图API功能
var map = new BMap.Map("allmap");                // 创建Map实例
var point = new BMap.Point(116.404, 39.915);    // 创建点坐标
map.centerAndZoom(point,15);                     // 初始化地图,设置中心点坐标和地图级别。
map.enableScrollWheelZoom();                     //启用滚轮放大缩小
var marker = new Array();
var markerpoint = new Array();
var cp = map.getCenter();
initPoint();
		function initPoint(){
		$.ajax({
			url: 'YDataController/getclusterdata',
			type: 'get',
			success: function(data){
					for (var i=0;i<200; i++)
					{
					console.log(data.data[i].longtitude)
						//最简单的用法，生成一个marker数组，然后调用markerClusterer类即可。
						var point = new BMap.Point(data.data[i].longtitude,data.data[i].latitude);	
					    marker[i] = new BMap.Marker(point);  // 创建标注
						
						var label = new BMap.Label("Cluster"+data.data[i].user_id+"#",{offset:new BMap.Size(20,-10)});
						marker[i].setLabel(label);
						markerpoint[i] = point;
						
						map.addOverlay(marker[i]);              // 将标注添加到地图中
                        marker[i].addEventListener("click",showSer);
	            
						
					}
			},
			error: function(err){
				console.log("err")	
			}
		});
		}
        function showSer(e)
		{
      var options=$("#selectValue option:selected"); 
			map.clearOverlays();
			initPoint();
			var circle = new BMap.Circle(e.point,1000,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
			 map.addOverlay(circle);
			 var local =  new BMap.LocalSearch(map, {renderOptions: {map: map, autoViewport: false}});  
			 var bounds = getSquareBounds(circle.getCenter(),circle.getRadius());
			 local.searchInBounds(options.val(),bounds); 
		 
					
							 
		}

	
	
	
	 function getSquareBounds(centerPoi,r){
        var a = Math.sqrt(2) * r; //正方形边长
      
        mPoi = getMecator(centerPoi);
        var x0 = mPoi.x, y0 = mPoi.y;
     
        var x1 = x0 + a / 2 , y1 = y0 + a / 2;//东北点
        var x2 = x0 - a / 2 , y2 = y0 - a / 2;//西南点
        
        var ne = getPoi(new BMap.Pixel(x1, y1)), sw = getPoi(new BMap.Pixel(x2, y2));
        return new BMap.Bounds(sw, ne);        
        
    }
    //根据球面坐标获得平面坐标。
    function getMecator(poi){
        return map.getMapType().getProjection().lngLatToPoint(poi);
    }
    //根据平面坐标获得球面坐标。
    function getPoi(mecator){
        return map.getMapType().getProjection().pointToLngLat(mecator);
    }
	

   
   
   
    /**
     * 得到圆的内接正方形bounds
     * @param {Point} centerPoi 圆形范围的圆心
     * @param {Number} r 圆形范围的半径
     * @return 无返回值   
     */ 
    function getSquareBounds(centerPoi,r){
        var a = Math.sqrt(2) * r; //正方形边长
      
        mapoi = getMecator(centerPoi);
        var x0 = mapoi.x, y0 = mapoi.y;
     
        var x1 = x0 + a / 2 , y1 = y0 + a / 2;//东北点
        var x2 = x0 - a / 2 , y2 = y0 - a / 2;//西南点
        
        var ne = getPoi(new BMap.Pixel(x1, y1)), sw = getPoi(new BMap.Pixel(x2, y2));
        return new BMap.Bounds(sw, ne);        
        
    }
    //根据球面坐标获得平面坐标。
    function getMecator(poi){
        return map.getMapType().getProjection().lngLatToPoint(poi);
    }
    //根据平面坐标获得球面坐标。
    function getPoi(mecator){
        return map.getMapType().getProjection().pointToLngLat(mecator);
    }
	
// 复杂的自定义覆盖物
    function ComaplexCustomOverlay(point, text, mouseoverText){
      this._point = point;
      this._text = text;
      this._overText = mouseoverText;
    }
    ComaplexCustomOverlay.prototype = new BMap.Overlay();
    ComaplexCustomOverlay.prototype.initialize = function(map){
      this._map = map;
      var div = this._div = document.createElement("div");
      div.style.position = "absolute";
      div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
      div.style.backgroundColor = "#EE5D5A";
      div.style.border = "1px solid #BC3B3A";
      div.style.color = "white";
      div.style.height = "18px";
      div.style.padding = "2px";
      div.style.lineHeight = "25px";
      div.style.whiteSpace = "nowrap";
      div.style.MozUserSelect = "none";
      div.style.fontSize = "13px"
      var span = this._span = document.createElement("span");
      div.appendChild(span);
      span.appendChild(document.createTextNode(this._text));      
      var that = this;

      var arrow = this._arrow = document.createElement("div");
      arrow.style.background = "url(http://map.baidu.com/fwmap/upload/r/map/fwmap/static/house/images/label.png) no-repeat";
      arrow.style.position = "absolute";
      arrow.style.width = "11px";
      arrow.style.height = "10px";
      arrow.style.top = "22px";
      arrow.style.left = "10px";
      arrow.style.overflow = "hidden";
      div.appendChild(arrow);
     
        div.onmouseover = function(){
        this.style.backgroundColor = "#6BADCA";
        this.style.borderColor = "#0000ff";
        this.getElementsByTagName("span")[0].innerHTML = that._overText;
        arrow.style.backgroundPosition = "0px -20px";
      }

        div.onmouseout = function(){
        this.style.backgroundColor = "#EE5D5B";
        this.style.borderColor = "#BC3B3A";
        this.getElementsByTagName("span")[0].innerHTML = that._text;
        arrow.style.backgroundPosition = "0px 0px";
      }
	  

      map.getPanes().labelPane.appendChild(div);
      
      return div;
    }
    ComaplexCustomOverlay.prototype.draw = function(){
      var map = this._map;
      var pixel = map.pointToOverlayPixel(this._point);
      this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
      this._div.style.top  = pixel.y - 30 + "px";
    }

</script>
