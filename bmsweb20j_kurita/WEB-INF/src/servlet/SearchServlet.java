package servlet;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import bean.Book;
import dao.BooksDAO;

public class SearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{

		//エラー表記を定義
		String error ="";
		String cmd ;

		try {
			//ArrayList配列
			ArrayList<Book> books = new ArrayList<Book>();

			//BookDaoオブジェクト格納
			BooksDAO dao = new BooksDAO();

			//エンコードを日本語にして送る
			request.setCharacterEncoding("UTF-8");

			//受け取った値を格納する
			String isbn=request.getParameter("isbn");
			String title=request.getParameter("title");
			String price=request.getParameter("price");

			//books配列にdaoのsearchメソッドから受け取った値を戻す
			books = dao.search(isbn,title,price);

			//取得した書籍情報を「booklist」という名前でリクエストスコープに登録
			request.setAttribute("book_list", books);

		}catch(IllegalStateException e) {
				error="データベース接続エラーの為、一覧表示はできませんでした。";
		}finally {
			//⑤データベース接続エラーの為、一覧表示はできませんでした。"なら「menu.jsp」へフォワード、そうでなければlist.jspへフォワード

			request.setAttribute("error", error);

		     if(error.equals("データベース接続エラーの為、一覧表示はできませんでした。")) {
				cmd="menu";
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/menu.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/view/list.jsp").forward(request, response);
			}
		}
	}
}
