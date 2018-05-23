<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html><html class=''>
<head><meta charset='UTF-8'>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    %>
    <base href="<%=basePath %>" />
    <script src="js/jquery.min.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=M2fCcQ8stMC84gOtahIUI644"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<title>用户聚类</title>
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
#allmap{
	position: absolute;
	top: 0px;
}
</style>
</head>
<body>
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">

// 百度地图API功能
var mp = new BMap.Map("allmap");            // 创建Map实例
var point = new BMap.Point(116.404, 39.915);    // 创建点坐标
mp.centerAndZoom(point,15);                     // 初始化地图,设置中心点坐标和地图级别。
mp.enableScrollWheelZoom();                            //启用滚轮放大缩小

	var cp = mp.getCenter();
		$.ajax({
      url: 'YDataController/getclusterdata',
			type: 'get',
			success: function(data){
					mp.clearOverlays();
					for (var i=0;i<200; i++)
					{
					//console.log(data[i])
						//最简单的用法，生成一个marker数组，然后调用markerClusterer类即可。
							var point = new BMap.Point(data.data[i].longtitude,data.data[i].latitude);	
								
							var txt = "Cluster", mouseoverTxt = txt + " " +data.data[i].user_id+ "#" ;       
							var myCompOverlay = new ComplexCustomOverlay(point, txt,mouseoverTxt);
							mp.addOverlay(myCompOverlay);
			
						
					}
			},
			error: function(err){
				console.log("err")	
			}
		});

	
// 复杂的自定义覆盖物
    function ComplexCustomOverlay(point, text, mouseoverText){
      this._point = point;
      this._text = text;
      this._overText = mouseoverText;
    }
    ComplexCustomOverlay.prototype = new BMap.Overlay();
    ComplexCustomOverlay.prototype.initialize = function(map){
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
	  

      mp.getPanes().labelPane.appendChild(div);
      
      return div;
    }
    ComplexCustomOverlay.prototype.draw = function(){
      var map = this._map;
      var pixel = map.pointToOverlayPixel(this._point);
      this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
      this._div.style.top  = pixel.y - 30 + "px";
    }

</script>
