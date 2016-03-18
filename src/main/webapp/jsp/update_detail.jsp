

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html><html class=''>
<head><meta charset='UTF-8'>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    %>
    <base href="<%=basePath %>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="js/angular.js"></script> 
<html>

<div ng-app="myApp" ng-controller="myCtrl">
名字: <input ng-model="names.title">
<h1>你输入了: {{names.title}}</h1>
</div>

<script>
var id = '<%=session.getAttribute("id")%>';
     // alert(jp);   
     var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope, $http) {
    $http.get("/dataController/getinfo.do?id=30")
    .success(function(response) {$scope.names = response.data;
// $scope.names.title =1;
   });
});


// var app = angular.module('myApp', []);
// app.controller('myCtrl', function($scope) { $scope.name = 1;
// });
</script>
</html>


