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

public class DetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException{
		String cmd = null ;
		String error="";
		int trueprice;
		//①BookDAOクラスのオブジェクトを生成
		BooksDAO dao = new BooksDAO();

		//②表示する書籍情報を格納するBookオブジェクトを生成
		Book book = new Book();

		//③画面から送信されるISBNとcmd情報を受け取るためのエンコードを設定
		request.setCharacterEncoding("UTF-8");

		try {
			//④画面から送信されるISBNとcmd情報を受け取る
			String isbn = request.getParameter("isbn");
			 cmd = request.getParameter("cmd");

			//⑤BookDAOクラスに定義したselectByIsbn（）メソッドを利用して書籍情報を取得

			book = dao.selectByIsbn(isbn);

			//
			if( book.getIsbn()==null){
				error = "表示対象の書籍が存在しない為、詳細情報は表示できませんでした。";
				//⑤⑥「ListServlet」へフォワード

				cmd="list";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}

				//⑥取得した書籍情報を「book」という名前でリクエストスコープに登録
				request.setAttribute("book", book);

			}catch(IllegalStateException e) {
				error="データベース接続エラーの為、一覧表示はできませんでした。";

			}catch(Exception e) {

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

				//⑦cmd情報の値を判定し、「detail」の場合は「detail.jsp」へフォワード
			}else if (cmd.equals ("detail") ) {
				request.getRequestDispatcher("/view/detail.jsp").forward(request, response);
			}else if(cmd.equals("update")) {
				request.getRequestDispatcher("/view/update.jsp").forward(request, response);
			}
		}
	}
}