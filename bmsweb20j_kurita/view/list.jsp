<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.Book,util.MyFormat"%>
<%
MyFormat myf = new MyFormat();
%>

<html>
	<head>
	<title>list</title>
	</head>
	<%@include file = "/common/header.jsp" %>
	<table align="center" width="1550">
		<tr>
			<td width="80">[<a href="<%=request.getContextPath() %>/view/menu.jsp">メニュー</a>]</td>
			<td width="80">[<a href="<%=request.getContextPath() %>/view/insert.jsp">書籍登録</a>]</td>
			<td width="508" align="center" ><font size="5">書籍一覧</font></td>
			<td width="80">&nbsp;</td>
			<td width="80">&nbsp;</td>
		</tr>
	</table>

	<hr align="center" size="2" color="black" ></hr>

	<table align="center">
		<tr>
			<td>
				<form action="<%=request.getContextPath()%>/search">
					isbn：<input type=text size="30" name="isbn"></input>
					title：<input type=text size="30" name="title"></input>
					価格：<input type=text size="30" name="price"></input>
					<input type="submit" name="search" value="検索"></input>
				</form>
			</td>
			<td>
			<form action="<%=request.getContextPath()%>/list">
			<input type="submit" name="searchall" value="全件表示"></input>
			</form>
			</td>
		</tr>
	</table>

	<table align="center" width="1500">
		<tr>
			<th bgcolor="#6666ff" >isbn</th>
			<th bgcolor="#6666ff" >title</th>
			<th bgcolor="#6666ff" >価格</th>
			<th bgcolor="#6666ff" colspan="3">変更/削除/カートに入れる</th>
		</tr>
		<%
		ArrayList<Book> list =(ArrayList<Book>)request.getAttribute("book_list");
		if(list != null){
			for(int i=0;i<list.size();i++){
				Book books = (Book)list.get(i);
		%>
		<tr>
			<td align="center" ><a href="<%=request.getContextPath() %>/detail?isbn=<%=books.getIsbn()%>&cmd=detail"><%=books.getIsbn()%></a></td>
			<td align="center" ><%=books.getTitle()%></td>
			<td align="center" ><%=myf.moneyFormat(books.getPrice())%></td>
			<td align="center" >
				<a href="<%=request.getContextPath() %>/detail?isbn=<%=books.getIsbn()%>&cmd=update">変更</a>
			</td>
				<td align="center" >
					<a href="<%=request.getContextPath()%>/delete?isbn=<%=books.getIsbn()%>">削除</a>
				</td>
				<td align="center" >
					<a href="<%=request.getContextPath()%>/insertIntoCart?isbn=<%=books.getIsbn()%>">カートに入れる</a>
				</td>
			</tr>
			<%
				}
			}else{
			%>
			<tr>
				<td align="center" width="200">&nbsp;</td>
				<td align="center" width="200">&nbsp;</td>
				<td align="center" width="200">&nbsp;</td>
				<td align="center" width="250" colspan="2">&nbsp;</td>
			</tr>
			<%
			}
			%>
		</table>
		<br><br><br><br><br><br><br><br><br><br>
	<%@include file = "/common/footer.jsp" %>
	</body>
</html>