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

public class LogoutServlet extends HttpServlet {

	//メソッド
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{


		//セッション情報をクリアにする
		HttpSession session = request.getSession();
		session.invalidate();

			//menu.jspへフォワード
			request.getRequestDispatcher("/view/login.jsp").forward(request, response);

		}
	}
