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
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
.form{
	position: absolute;
	top: 62px;
	left: 19px;
	z-index: 100;
}
.form input{
	display: inline;
	width:auto;
}
.btn .yz{
	display:inline;
}

#allmap{
	position: absolute;
	top: 0px;
}
</style>
<script type="text/javascript">

 </script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=M2fCcQ8stMC84gOtahIUI644"></script>
<title>StayPoint</title>
</head>
<body>
	<form method="POST"  action="" class="form" style="left: 0;top:19px;" onsubmit="return keyshow()">  
	<input id="inputValue" type="text" name="user" class="form-control" placeholder="user ID" style="height: 30px;padding-left: 5px;">
		<input style="height: 30px;margin-left: -3px;width: 60px;" class="btn btn-primary yz" type="submit" value="确认" >  
	</form>  
	<div id="allmap"></div>
	
</body>
</html>
<script type="text/javascript">
$("#inputValue").val(parent.window.location.hash.substr(1) || "").on("focus", function(){
	$(this).val("");
})
function keyshow(){
	var selected = $("#inputValue").val();
	parent.window.location.hash = selected;
}


// 百度地图API功能
	var mp = new BMap.Map("allmap");
	mp.centerAndZoom(new BMap.Point(116.3964,39.9093), 18);
	mp.enableScrollWheelZoom();
	mp.addEventListener("dragend", function showInfo(){
	var cp = mp.getCenter();
		$.ajax({
			url: 'YDataController/getdata',
			type: 'get',
			data:{userid: $("#inputValue").val(),lng:cp.lng,lat:cp.lat},
			success: function(data){
					mp.clearOverlays();
					for (var i=0;i<200; i++)
					{
					console.log(data.data[i])
						//最简单的用法，生成一个marker数组，然后调用markerClusterer类即可。
							var point = new BMap.Point(data.data[i].longtitude,data.data[i].latitude);  
              var txt = "User", mouseoverTxt = txt + " " +data.data[i].user_id+ "#" ;       
              var myCompOverlay = new ComplexCustomOverlay(point, txt,mouseoverTxt);
              mp.addOverlay(myCompOverlay);
					}
			},
			error: function(err){
				console.log("err")	
			}
		});

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
	 
    



 
	
 //   var txt = "银=海岸城", mouseoverTxt = txt + " " + parseInt(Math.random() * 1000,10) + "套" ;       
 //   var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(116.407845,39.914101), txt,mouseoverTxt);
 //   mp.addOverlay(myCompOverlay);
	
	

</script>
