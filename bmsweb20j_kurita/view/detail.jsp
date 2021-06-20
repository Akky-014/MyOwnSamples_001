<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.Book,util.MyFormat"%>

<%
Book books = (Book)request.getAttribute("book");
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
			<td width="50">[<a href="<%=request.getContextPath() %>/view/insert.jsp">書籍登録</a>]</td>
			<td width="50">[<a href="<%=request.getContextPath() %>/list">【書籍一覧】</a>]</td>
			<td width="408" align="center" ><font size="5">書籍詳細情報</font></td>
			<td width="80">&nbsp;</td>
			<td width="80">&nbsp;</td>
		</tr>
		</table>

		<hr align="center" size="2" color="black" width="1650"></hr>
		<br>

	<table align="center" width="200">
		<tr>
		<td colspan="2" align="center" >
			<form action="<%=request.getContextPath() %>/detail" method="get">
			<input type="hidden" name="isbn" value=<%=books.getIsbn()%>>
			<input type="hidden" name="cmd" value="update">
			<input type="submit" value="変更">
			</form>
		</td>


		<td colspan="2" align="center" >
			<form action="<%=request.getContextPath() %>/delete" method="get">
			<input type="hidden" name="isbn" value=<%=books.getIsbn()%>>
			<input type="submit" value="削除">
			</form>
			</td>
		</tr>
	</table>

		<br>
	<table align="center">
		<tr>
			<td width="60">ISBN</td>
			<td><%=books.getIsbn() %></td>
		</tr>
		<tr>
			<td width="60">TITLE</td>
			<td><%=books.getTitle() %></td>
		</tr>
		<tr>
			<td width="60">価格</td>
			<td><%=myf.moneyFormat(books.getPrice()) %></td>
		</tr>
	</table>

		<br><br><br><br><br><br><br><br><br><br>
		<%@include file = "/common/footer.jsp" %>
	</body>
</html>