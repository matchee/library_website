<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%=basePath%>">

<title>regist</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page"> 
<script src="js/jQuery.js"></script> 
<script type="text/javascript">
function checkEmail(){
var emailifo=$("#email").val();
         $.ajax({
             type: "GET",
             url: "userJSONAction_checkUserEmail.action",
             data: {email:emailifo},
             dataType: "json",
             success: function(data){
             var obj=$.parseJSON(data);
             $("#emailMes").html("");
             $("#emailMes").text(obj.warnMsg);
             }
             });
}
function checkUserName(){
var nameInfo=$("#Name").val();
         $.ajax({
             type: "GET",
             url: "userJSONAction_checkUserName.action",
             data: {userName:nameInfo},
             dataType: "json",
             success: function(data){
             var obj=$.parseJSON(data);
             $("#nameMes").html("");
             $("#nameMes").text(obj.warnMsg);
             }
             });
}
</script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
	注册
	<br>
	<form action="regist.action" method="post">
		邮箱:<input type="text" id="email" name="email" onblur="checkEmail()"><p id="emailMes"></p><br /> 
		用户名:<input type="text" id="userName" name="userName" onblur="checkUserName()"><p id="nameMes"></p> <br /> 
		密码:<input type="text" name="password" value=""><br /> 
		<input type="submit" name="submit" value="注册">
	</form>
</body>
</html>
