<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.Book,util.MyFormat"%>
<html>
	<head>
		<title>書籍管理メニュー画面</title>
	</head>
	<body>

		<%@include file = "/common/header.jsp" %>
		<br>
	<table align="center" width="1500">
		<tr>
				<td width="50"><a href="../list">【書籍一覧】</a></td>
				<td width="50"><a href="./insert.jsp">【書籍登録】</a></td>
				<th width="500" align="center" ><font size="5">書籍登録</font></th>
				<td width="80">&nbsp;</td>
		</tr>
	</table>

		<hr align="center" size="2" color="black" width="1650">
		<br>
		<form action="<%=request.getContextPath()%>/insert">
		<table align="center">
			<tr>
				<td width="60">ISBN</td>
				<td><input type=text size="30" name="isbn"></input></td>
			</tr>
			<tr>
				<td width="60">TITLE</td>
				<td><input type=text size="30" name="title"></input></td>
			</tr>
			<tr>
				<td width="60">価格</td>
				<td><input type=text size="30" name="price"></input></td>
			</tr>
		</table>

		<br>

		<table align="center">
			<tr>
				<td colspan="2" align="center">
				<input type="submit" value="登録">
				</td>
			</tr>
		</table>
		</form>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<%@include file = "/common/footer.jsp" %>
    </body>
</html>