package servlet;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.userDAO;
import bean.User;

public class LoginServlet extends HttpServlet {

	//doPostメソッド
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{

		//各変数を定義および初期化
		String error="";
		String cmd="";
		User user=new User();
		String userid="";
		String password="";

		try {
			//userid,passwordの入力パラメータを取得する
			userid = request.getParameter("id");
			password = request.getParameter("password");


			//userDAOをインスタンス化して、関連メソッドを呼び出す
			userDAO userdao = new userDAO();
			user = userdao.selectByUser(userid, password);
		}catch(IllegalStateException e) {
			error="DB接続エラーの為、ログインは出来ません。";
		}finally {
			//errorの値が"データベース接続エラーなら"「error.jsp」へフォワード
			if(error.equals("DB接続エラーの為、ログインは出来ません。")) {
				cmd="menu";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error",error );
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				}
			//ユーザー情報がない場合
			if ( user.getUserid()==null||user.getPassword()==null) {
				error = "※入力データが間違っています！";
				cmd = "miss";
				request.setAttribute("message",error );
				request.setAttribute("cmd", cmd);
				//login.jspへフォワード
				request.getRequestDispatcher("/view/login.jsp").forward(request, response);

			}else {
				//ユーザー情報がある場合
				//セッションオブジェクトの作成
				HttpSession session = request.getSession();

				//取得したUserオブジェクトをセッションスコープにuserという名前で登録する。
				session.setAttribute("user", user);

				//クッキーに入力情報のuseridとpasswordを登録する
				//ユーザーid用
				Cookie userCookie = new Cookie("userid",userid);
				userCookie.setMaxAge(60*60*24*5);
				response.addCookie(userCookie);
				//パスワード用
				Cookie passwordCookie = new Cookie("password",password);
				passwordCookie.setMaxAge(60*60*24*5);
				response.addCookie(passwordCookie);

				//menu.jspへフォワード
				request.getRequestDispatcher("/view/menu.jsp").forward(request, response);
			}
		}
	}
}
