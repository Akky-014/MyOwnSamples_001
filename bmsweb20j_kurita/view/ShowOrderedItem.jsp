<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.OrderedItem"%>
<%

%>

<html>
	<head>
	<title>Cart内容</title>
	</head>
	<%@include file = "/common/header.jsp" %>
	<table >
		<tr>
			<td align="left" >[<a href="<%=request.getContextPath() %>/view/menu.jsp">メニュー</a>]</td>
			<td align="center" width="1400"><font size="5">購入状況</font></td>
		</tr>
	</table>

	<hr align="center" size="2" color="black" ></hr>

	<br>
	<br>
	<table align="center" width="1400">
		<tr>
			<td></td>
		</tr>
		<tr>
			<th bgcolor="#6666ff" >ユーザー</th>
			<th bgcolor="#6666ff" >Title</th>
			<th bgcolor="#6666ff" >注文日</th>
		</tr>

		<%int total = 0; %>
		<%
		ArrayList<OrderedItem> list =(ArrayList<OrderedItem>)request.getAttribute("orderedlist");
		if(list != null){
			for(int i=0;i<list.size();i++){
				OrderedItem item = (OrderedItem)list.get(i);
		%>
		<tr>
			<td align="center" ><%=item.getUserid()%></td>
			<td align="center" ><%=item.getTitle()%></td>
			<td align="center" ><%=item.getDate()%></td>

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

		<br><br><br><br><br><br>
	<%@include file = "/common/footer.jsp" %>
	</body>
</html>