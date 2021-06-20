package servlet;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import bean.Book;
import dao.BooksDAO;

public class ListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

		//エラー表記を定義
		String error ="";
		String cmd="";
		try {
			//①BookDAOクラスのオブジェクトを生成
			BooksDAO dao = new BooksDAO();

			//③画面からの入力情報を受け取るためのエンコードを設定
			request.setCharacterEncoding("UTF-8");

			//②検索した書籍情報を格納するAllayListオブジェクトを生成
			//③BookDAOクラスに定義した、selectAll()メソッドを利用して書籍情報を取得
			ArrayList<Book> book = dao.selectAll();

			//④取得した書籍情報を「book_list」という名前でリクエストスコープに登録
			request.setAttribute("book_list", book);
		}catch(IllegalStateException e) {
			error="データベース接続エラーの為、一覧表示はできませんでした。";
		}catch(Exception e) {
			error="予期せぬエラーが発生しました。";
		}finally {
			//⑤errorの値が"予期せぬエラーが発生しました。"なら「error.jsp」へフォワード、そうでなければlist.jspへフォワード
			request.setAttribute("error", error);
			if(error.equals("予期せぬエラーが発生しました。")) {
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}else if(error.equals("データベース接続エラーの為、一覧表示はできませんでした。")) {
				cmd="menu";
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/view/list.jsp").forward(request, response);
			}
		}
	}
}