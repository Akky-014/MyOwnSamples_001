<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,bean.User,util.MyFormat"%>
<%
String User="";	//ユーザーを管理する変数
String Authority="";	//権限を管理する変数


//セッションからユーザー情報を取得
User user = (User)session.getAttribute("user");

//セッション切れならerror.jspへとフォワード
if(user==null){
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd","logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request,response);
	return;
}

	//ユーザー情報を取得
	User = user.getUserid();

	//クッキーからパスワード情報の取得
	if(user.getAuthority().equals("1")){
		Authority = "一般ユーザー";
	}else{
		Authority = "管理者";
	}

%>

<html>
	<head>
		<title>クッキー画面</title>
	</head>
	<body>
	<table align = "right">
		<tr>
			<td>名前：<%=User %></td>
		</tr>
		<tr>
			<td>権限：<%=Authority %></td>
		</tr>
	</table>
	</body>
</html>