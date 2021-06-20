<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.Book,util.MyFormat"%>

<%
Book books = (Book)request.getAttribute("book");
MyFormat myf = new MyFormat();
String error = (String)request.getAttribute("error");
String cmd = (String)request.getAttribute("cmd");

%>


<html>
	<head>
		<title>list</title>
	</head>
<body>
	<%@include file = "/common/header.jsp" %>
	<table align="center" >
		<tr>
			<th align="center" >■■エラー■■</th>
		</tr>
		<tr>
			<td ><%=error %></td>
		</tr>

		<tr>
			<%if(cmd.equals("list")){%>
			<td align="center"><a href="<%=request.getContextPath() %>/list">【一覧表示に戻る】</a></td>
			<%}else if(cmd.equals("menu")){ %>
			<td align="center"><a href="<%=request.getContextPath() %>/view/menu.jsp">【メニュー画面に戻る】</a></td>
			<%}else{ %>
			<td align="center"><a href="<%=request.getContextPath() %>/logout">【ログイン画面へ】</a>]</td>
			<%}%>
		</tr>
	</table>


		<br><br><br><br><br><br><br><br><br><br>
		<%@include file = "/common/footer.jsp" %>
	</body>
</html>