package servlet;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import bean.Book;
import dao.BooksDAO;

public class DeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{
		//エラー表記を定義
		String error ="";
		String cmd="";
		String isbn="";
		//①BookDAOクラスのオブジェクトを生成
		BooksDAO dao = new BooksDAO();

		//②表示する書籍情報を格納するBookオブジェクトを生成
		Book book = new Book();

		//②画面から送信されるISBN情報を受け取るためのエンコードを設定します。
		request.setCharacterEncoding("UTF-8");

		try {
			//③画面から送信されるISBN情報を受け取ります。
			isbn = request.getParameter("isbn");
			cmd = request.getParameter("cmd");
			//⑤BookDAOクラスに定義したselectByIsbn（）メソッドを利用して書籍情報を取得
			book = dao.selectByIsbn(isbn);

			//対象の書籍が存在しないとき
			if(book.getIsbn()==null){
				error = "表示対象の書籍が存在しない為、詳細情報は表示できませんでした。";
				//⑤⑥「ListServlet」へフォワード
				cmd="list";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}catch(IllegalStateException e) {
			error="データベース接続エラーの為、一覧表示はできませんでした。";

		}catch(Exception e) {
			System.out.println("よきせぬ");
			error="予期せぬエラーが発生しました。";

		}finally {
			//⑤errorの値が"予期せぬエラーが発生しました。"か"データベース接続エラーの為、一覧表示はできませんでした。"なら「error.jsp」へフォワード、そうでなければlist.jspへフォワード

			request.setAttribute("error", error);
			if(error.equals("予期せぬエラーが発生しました。")) {
				cmd="list";
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);

			}else if(error.equals("データベース接続エラーの為、一覧表示はできませんでした。")) {
				cmd="menu";
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);

			}else {

				//④BookDAOクラスに定義したdelete（）メソッドを利用して書籍情報を削除
				dao.delete(isbn);

				//⑤「ListServlet」へフォワード
				request.getRequestDispatcher("/list").forward(request, response);
			}
		}
	}
}