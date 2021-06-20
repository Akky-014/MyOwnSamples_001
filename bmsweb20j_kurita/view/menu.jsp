<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bean.User"%>


<html>
	<head>
		<title>書籍管理メニュー画面</title>
	</head>
	<body>
		<%@include file = "/common/header.jsp" %>
		<%@include file = "/common/userInfo.jsp" %>
		<p align="center"><font size="5">MENU</font></p>
		<hr align="center" size="2" color="black" width="1650">

		<table border=0 align="center" summary="メニュー表示">
			<tr><td><a href="<%=request.getContextPath()%>/list">【書籍一覧】</a></td></tr>
			<tr><td><a href="<%=request.getContextPath() %>/view/insert.jsp">【書籍登録】</a></td></tr>
			<tr><td><a href="<%=request.getContextPath()%>/showCart">【カート状況】</a></td></tr>
			<tr><td><a href="<%=request.getContextPath()%>/insertIniData">【初期データ登録（データがない場合のみ）】</a></td></tr>
			<tr><td><a href="<%=request.getContextPath()%>/showOrderedItem">【購入状況確認】</a></td></tr>
			<tr><td><a href="<%=request.getContextPath()%>/logout" >【ログアウト】</a></td></tr>
		</table>

		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<%@include file = "/common/footer.jsp" %>
	</body>
</html>