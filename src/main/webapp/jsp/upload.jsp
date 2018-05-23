<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html><html class=''>
<head><meta charset='UTF-8'>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    %>
    <base href="<%=basePath %>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>上传图片</title> 
    </head>
    <body>
        <form action="upload/picUpload" method="POST" enctype="multipart/form-data"> 
         <input  name="type" style="display:none;" value="test"/>
<!--             yourfile: <input type="file" name="myfiles"/><br/>  -->
            服务器已经启动成功.........
            请上传您需要识别的图片......
            yourfile: <input type="file" name="myfiles"/><br/> 
            <input type="submit" value="上传图片"/> 
        </form> 
    </body>
</html>