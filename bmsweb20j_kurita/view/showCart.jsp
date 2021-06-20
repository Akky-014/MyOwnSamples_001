<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.Book,util.MyFormat"%>
<%
MyFormat myf = new MyFormat();
%>

<html>
	<head>
	<title>Cart内容</title>
	</head>
	<%@include file = "/common/header.jsp" %>
	<table >
		<tr>
			<td align="left" >[<a href="<%=request.getContextPath() %>/view/menu.jsp">メニュー</a>]</td>
			<td align="left" >[<a href="<%=request.getContextPath() %>/list">書籍一覧</a>]</td>
			<td align="center" width="1400"><font size="5">カート内容</font></td>
		</tr>
	</table>

	<hr align="center" size="2" color="black" ></hr>

	<br>
	<form action="<%=request.getContextPath()%>/buyConfirm">
	<table align="center" width="1400">
		<tr>
			<th bgcolor="#6666ff" >isbn</th>
			<th bgcolor="#6666ff" >title</th>
			<th bgcolor="#6666ff" >価格</th>
			<th bgcolor="#6666ff" colspan="3">削除</th>
		</tr>

		<%int total = 0; %>
		<%
		ArrayList<Book> list =(ArrayList<Book>)request.getAttribute("booklist");
		if(list != null){
			for(int i=0;i<list.size();i++){
				Book books = (Book)list.get(i);
				total += books.getPrice();
		%>
		<tr>
			<td align="center" ><a href="<%=request.getContextPath() %>/detail?isbn=<%=books.getIsbn()%>&cmd=detail"><%=books.getIsbn()%></a></td>
			<td align="center" ><%=books.getTitle()%></td>
			<td align="center" ><%=myf.moneyFormat(books.getPrice())%>円</td>
				<td align="center" >
					<a href="<%=request.getContextPath()%>/showCart?delno=<%=i%>">削除</a>
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

			<br>

			<hr align="center" size="2" color="black"></hr>
			<br><br>
		<table align="center" >
		<tr>
			<td  bgcolor="#6666ff" >合計</td>
			<td><%=myf.moneyFormat(total) %>円</td>
		</tr>
		</table>

		<br>

		<table align="center">
		<tr>
			<td colspan="2" align="center" >
			<input type="submit" value="購入"></td>
		</tr>
		</table>

	</form>
		<br><br><br><br><br><br>
	<%@include file = "/common/footer.jsp" %>
	</body>
</html>