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
			<td width="408" align="center" ><font size="5">書籍変更</font></td>
			<td width="80">&nbsp;</td>
			<td width="80">&nbsp;</td>
		</tr>
		</table>

		<hr align="center" size="2" color="black" width="1650"></hr>
		<br>

	<form action="<%=request.getContextPath()%>/update">

		<br>
	<table align="center">
		<tr>
			<th width="60"></th>
			<th> 変更前情報 </th>
			<th> 変更後情報 </th>
		</tr>
		<tr>
			<th width="60">ISBN</th>
			<td><%=books.getIsbn() %></td>
			<td><%=books.getIsbn() %></td>

		</tr>
		<tr>
			<th width="60">TITLE</th>
			<td><%=books.getTitle() %></td>
			<td><input type=text name="title"></input></td>
		</tr>
		<tr>
			<th width="60">価格</th>
			<td><%=myf.moneyFormat(books.getPrice()) %></td>
			<td><input type=text  name="price"></input></td>
		</tr>
	</table>

	<br>

	<table align="center" width="200">
		<tr>
		<td colspan="2" align="center">

		<input type="hidden" name="isbn" value=<%=books.getIsbn() %>>

			<input type="submit" value="変更完了">
		</td>
	</tr>
	</table>
</form>

		<br><br><br><br><br><br><br><br><br><br>
		<%@include file = "/common/footer.jsp" %>
	</body>
</html>