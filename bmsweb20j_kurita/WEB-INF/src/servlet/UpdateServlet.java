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

public class UpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{

		String error ="";
		String cmd ;

		//①BookDAOクラスのオブジェクトを生成
		BooksDAO dao = new BooksDAO();

		//②更新後の書籍情報を格納するBookオブジェクトを生成
		Book book = new Book();

		//③画面からの入力情報を受け取るためのエンコードを設定
		request.setCharacterEncoding("UTF-8");
	try {
		//④画面からの入力情報を受け取り、格納
		String isbn =request.getParameter("isbn");
		String title =request.getParameter("title");
		String price =request.getParameter("price");

		//タイトル未入力エラー
		if(title.equals("")) {

			error = "タイトルが未入力のため、書籍更新処理はできませんでした。";
			//⑤⑥「ListServlet」へフォワード
			cmd="list";
			request.setAttribute("cmd", cmd);
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}

		if(price.equals("")) {
			error = "価格が未入力の為、書籍登録処理は行えませんでした";
			//⑤⑥「ListServlet」へフォワード
			cmd="list";
			request.setAttribute("cmd", cmd);
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}

		//bookへ格納
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setPrice(Integer.parseInt(price));

		//⑤BookDAOクラスに定義したupdate（）メソッドを利用して、Bookオブジェクトに格納された書籍データでデータベースを更新
		dao.update(book);
	}catch(NumberFormatException e) {
		error="価格の値が不正のため、書籍登録処理は行えませんでした。";
		//⑤⑥「ListServlet」へフォワード
		cmd="list";
		request.setAttribute("cmd", cmd);
		request.setAttribute("error", error);
		request.getRequestDispatcher("/view/error.jsp").forward(request, response);

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
			//⑥「ListServlet」へフォワード
			request.setAttribute("book_list", book);
			request.getRequestDispatcher("/list").forward(request, response);
		}
	}
	}
}
