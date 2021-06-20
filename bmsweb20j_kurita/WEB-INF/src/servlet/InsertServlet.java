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

public class InsertServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

		String cmd ;
		String error="";
		int trueprice;

		//①BookDAOクラスのオブジェクトを生成
		BooksDAO dao = new BooksDAO();

		//②登録する書籍情報を格納するBookオブジェクトを生成
		Book book = new Book();

		//③画面からの入力情報を受け取るためのエンコードを設定
		request.setCharacterEncoding("UTF-8");
try {

		//④画面からの入力情報を受け取り、各変数に格納
		String isbn =(request.getParameter("isbn"));
		String title = (request.getParameter("title"));

		String price = request.getParameter("price");
		if(price.equals("")) {
			error = "価格が未入力の為、書籍登録処理は行えませんでした";
			//⑤⑥「ListServlet」へフォワード
			cmd="list";
			request.setAttribute("cmd", cmd);
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}

		//価格の変換チェック
		trueprice = (Integer.parseInt(price));


		//値のエラー確認
		if( isbn.equals("") ){
			error = "ISBNが未入力の為、書籍登録処理は行えませんでした。";
			//⑤⑥「ListServlet」へフォワード
			System.out.println("era-ga");
			cmd="list";
			request.setAttribute("cmd", cmd);
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}
		//ISBN重複確認
		book =dao.selectByIsbn(isbn);
		if( book.getIsbn()!=null){
			System.out.println(book.getIsbn());
			error = "入力ISBNは既に登録済みのため、書籍登録処理は行えませんでした。";
			//⑤⑥「ListServlet」へフォワード
			System.out.println("era-ga");
			cmd="list";
			request.setAttribute("cmd", cmd);
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}
		//タイトル未入力確認
		if( title.equals("") ){
			error = "タイトルが未入力の為、書籍登録処理は行えませんでした。";
			//⑤⑥「ListServlet」へフォワード
			cmd="list";
			request.setAttribute("cmd", cmd);
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}
		//各値をクラスオブジェクトbookへ格納
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setPrice(trueprice);

		//insert
		dao.insert(book);

	}catch(NumberFormatException e) {
		error="価格の値が不正のため、書籍登録処理は行えませんでした。";
		//⑤⑥「ListServlet」へフォワード
		cmd="list";
		request.setAttribute("cmd", cmd);
		request.setAttribute("error", error);
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
			request.setAttribute("book_list", book);
			request.getRequestDispatcher("/list").forward(request, response);
		}
	}
	}
}
