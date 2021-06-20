<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.Book,util.MyFormat"%>
<%
String error = (String)request.getAttribute("message");
String cmd = (String)request.getAttribute("cmd");
%>

<html>
	<head>
		<title>書籍管理ログイン画面</title>
	</head>
	<body>

		<%@include file = "/common/header.jsp" %>
		<br>
		<br>
		<form action="<%=request.getContextPath()%>/login" method="post">
		<table align="center" >
			<tr>
				<td width="90">ユーザー</td>
				<td><input type=text size="30" name="id"></input></td>
			</tr>
			<tr>
				<td width="90">パスワード</td>
				<td><input type=password size="30" name="password"></input></td>
			</tr>
		</table>

		<br>

		<table align="center">
			<tr>
				<td colspan="2" align="center">
				<input type="submit" value="ログイン">
				</td>
			</tr>
		</table>
		</form>
		<table align="center">
			<tr>
			<%if(cmd!=null){%>
			<td align="center"><%=error %></td>
			<%}else{ %>
			<td align="center"></td>
			<%}%>
			</tr>
		</table>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<%@include file = "/common/footer.jsp" %>
    </body>
</html>