<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.Book,util.MyFormat"%>

<%
Book book = (Book)request.getAttribute("Book");
MyFormat myf = new MyFormat();
%>


	<html>
	<head>
		<title>list</title>
	</head>
	<body>
		<%@include file = "/common/header.jsp" %>
		<table align="center" width="1600">
		<tr>
			<td width="50">[<a href="<%=request.getContextPath() %>/view/menu.jsp">メニュー</a>]</td>
			<td width="50">[<a href="<%=request.getContextPath() %>/list">書籍一覧</a>]</td>
			<td width="700" align="center" ><font size="5">カート追加</font></td>
		<td width="80">&nbsp;</td>

		</tr>
		</table>

		<hr align="center" size="2" color="black" width="1650"></hr>

		<br>
		<h2 align="center"><font size="4">下記の書籍をカートに追加しました。</font></h2>
		<br>

	<form action="<%=request.getContextPath() %>/showCart" method="get">
		<table align="center">
			<tr>
				<td width="50">ISBN</td>
				<td><%=book.getIsbn() %></td>
			</tr>
			<tr>
				<td width="50">TITLE</td>
				<td><%=book.getTitle() %></td>
			</tr>
			<tr>
				<td width="50">価格</td>
				<td><%=book.getPrice() %></td>
			</tr>
		</table>

		<br>

		<table align="center">
		<tr>
		<td colspan="2" align="center" >
			<input type="submit" value="カート確認">
			</td>
		</tr>
		</table>
	</form>
		<br><br><br><br><br><br><br><br><br><br>
		<%@include file = "/common/footer.jsp" %>
	</body>
</html>