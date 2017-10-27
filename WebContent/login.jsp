<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备管理系统-用户登录</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/loginStyle.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/modernizr-1.6.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/loginPlaceholder.js"></script>
<script type="text/javascript">
	function checkForm() {
		var userName = document.getElementById("userName").value;
		var password = document.getElementById("password").value;
		if (userName == null || userName == "") {
			alert("用户名不能为空！");
			return false;
		}
		if (password == null || password == "") {
			alert("密码不能为空！");
			return false;
		}
		return true;
	}
</script>

	<%
	if(request.getAttribute("errorMsg") != null){
		String errorMsg = request.getAttribute("errorMsg").toString();
	%>
	<script language="javascript">
		alert("<%=errorMsg%>");
	</script>
	<%
	}
	%>

</head>
<body>
<form id="slick-login" action="${pageContext.request.contextPath }/user/login.do" method="post" onsubmit="return checkForm()">
  <label for="username">username</label><input type="text" id="userName" name="userName" class="placeholder" placeholder="username"">
  <label for="password">password</label><input type="password" id="password" name="password" class="placeholder" placeholder="password"">
  <input type="submit" value="Log In">
</form>
</body>
</html>