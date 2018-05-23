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
<html class="dj_webkit dj_chrome dj_contentbox"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
     
    <title>NUPT - SpatioTemporal Data Miner Data</title> 

    <link rel="stylesheet" href="css/claro.css" media="screen"> 
    <link rel="stylesheet" href="css/geocloud.css" media="screen"> 
<style type="text/css"></style></head> 
<body class="claro" onload="on_pageLoad()" marginwidth="0" marginheight="0"> 
    <div id="appLayout"> 
        <div id="banner" class="center"> 
            <img src="images/terrafly_logo80.jpg"> 
            <span>NUPT - SpatioTemporal Data Miner Data</span> 
        </div> 
        <div id="head"> 
            <div class="dijitMenuBar center dijitMenuPassive" data-dojo-attach-point="containerNode" role="menubar" tabindex="0" data-dojo-attach-event="onkeypress: _onKeyPress" id="menu" widgetid="menu"> 
                <!-- Menu button --> 
                <div class="dijitReset dijitInline dijitMenuItemLabel dijitMenuItem" data-dojo-attach-point="focusNode" role="menuitem" tabindex="-1" aria-labelledby="dijit_PopupMenuBarItem_0_text" id="dijit_PopupMenuBarItem_0" widgetid="dijit_PopupMenuBarItem_0" aria-haspopup="true" style="user-select: none;">
	<span data-dojo-attach-point="containerNode" id="dijit_PopupMenuBarItem_0_text">Data</span>
</div> 
                <div class="dijitReset dijitInline dijitMenuItemLabel dijitMenuItem" data-dojo-attach-point="focusNode" role="menuitem" tabindex="-1" aria-labelledby="dijit_PopupMenuBarItem_1_text" id="dijit_PopupMenuBarItem_1" widgetid="dijit_PopupMenuBarItem_1" aria-haspopup="true" style="user-select: none;">
	<span data-dojo-attach-point="containerNode" id="dijit_PopupMenuBarItem_1_text">Edit</span>
</div> 
                <div class="dijitReset dijitInline dijitMenuItemLabel dijitMenuItem" data-dojo-attach-point="focusNode" role="menuitem" tabindex="-1" aria-labelledby="dijit_PopupMenuBarItem_2_text" id="dijit_PopupMenuBarItem_2" widgetid="dijit_PopupMenuBarItem_2" aria-haspopup="true" style="user-select: none;">
	<span data-dojo-attach-point="containerNode" id="dijit_PopupMenuBarItem_2_text">Share</span>
</div> 
                <div class="dijitReset dijitInline dijitMenuItemLabel dijitMenuItem" data-dojo-attach-point="focusNode" role="menuitem" tabindex="-1" aria-labelledby="dijit_PopupMenuBarItem_3_text" id="dijit_PopupMenuBarItem_3" widgetid="dijit_PopupMenuBarItem_3" aria-haspopup="true" style="user-select: none;">
	<span data-dojo-attach-point="containerNode" id="dijit_PopupMenuBarItem_3_text">Analysis Models</span>
</div> 
                <div class="dijitReset dijitInline dijitMenuItemLabel dijitMenuItem" data-dojo-attach-point="focusNode" role="menuitem" tabindex="-1" aria-labelledby="dijit_PopupMenuBarItem_4_text" id="dijit_PopupMenuBarItem_4" widgetid="dijit_PopupMenuBarItem_4" aria-haspopup="true" style="user-select: none;">
	<span data-dojo-attach-point="containerNode" id="dijit_PopupMenuBarItem_4_text">Graph</span>
</div> 
 
                <div class="dijitReset dijitInline dijitMenuItemLabel dijitMenuItem" data-dojo-attach-point="focusNode" role="menuitem" tabindex="-1" aria-labelledby="dijit_PopupMenuBarItem_5_text" id="dijit_PopupMenuBarItem_5" widgetid="dijit_PopupMenuBarItem_5" aria-haspopup="true" style="user-select: none;">
	<span data-dojo-attach-point="containerNode" id="dijit_PopupMenuBarItem_5_text">MapQL</span>
</div> 
 
            </div> 
        </div> 
            <script type="text/javascript">
function loadIframe(iframeName, url) {
    var $iframe = $('#' + iframeName);
    if ( $iframe.length ) {
        $iframe.attr('src',url);   
        return false;
    }
    return true;
}
    </script>
        <div id="middle"> 
            <div id="mainContent" class="center"> 
                <div id="leftPanel" class="shadow"> 
                    <div id="refBar"> 
                        <span>Service</span> 
                    </div> 
                    <div id="datasets">
                    <ul id="data-list">
                        <li id="item1" ><a onclick="loadIframe('ifr','jsp/stayPoint.jsp')"><span class="datasetNames">StayPoint visualization</span></a></li>
                        <li id="item2"><a onclick="loadIframe('ifr','jsp/Cluster.jsp')"><span class="datasetNames">Cluster visualization</span></a></li>
                        <li id="item1"><a onclick="loadIframe('ifr','jsp/ClusterService.jsp')"><span class="datasetNames">ClusterService</span></a></li>
                        <li id="item1"><a onclick="loadIframe('ifr','jsp/predictrService.jsp')"><span class="datasetNames">Prediction Service</span></a></li>
                    </ul>
                    </div> 
               </div> 
                <div id="centerMap" class="shadow" > 
<iframe id="ifr" src="jsp/ClusterService.jsp" style="width: 100%; height: 99%; "> </div>
                <div id="mapFrame" class="shadow" style="display: none"> 
                </div> 
            </div> 
        </div> 
 
        <div id="tail"> 
            TerraFly GeoCloud 
        </div> 
    </div> 

 </div></body></html>