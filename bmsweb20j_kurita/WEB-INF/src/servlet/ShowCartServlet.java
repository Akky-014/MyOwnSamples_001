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
import bean.Order;
import bean.User;
import dao.BooksDAO;

public class ShowCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException{

		//各変数を定義
		String error ="";
		String cmd ;
		User	user = new User();

		try {
			//delnoの入力パラメータを取得
			String delno = request.getParameter("delno");
			//セッションオブジェクトの作成
			HttpSession session = request.getSession();

			//セッションからuserのUserオブジェクトを取得する
			user = (User)session.getAttribute("user");

			//セッション切れの場合
			if(user==null) {
				error="セッション切れの為、カート状況は確認出来ません。";
				//エラーなら「error.jsp」へフォワード、そうでなければinsertIntoCart.jspへフォワード
				cmd="insertintocart";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}

			//セッションから"orderlist"を取得する。
			ArrayList<Order> orderlist =(ArrayList<Order>)session.getAttribute("orderlist");

			//delnoがnullでない場合は、orderlistから該当の書籍情報を削除する
			if ( delno!=null ) {
				orderlist.remove(Integer.parseInt(delno));
				session.setAttribute("orderlist", orderlist);
			}

			//Orderオブジェクト、BooksDAOオブジェクト、Bookオブジェクト作成
			Order order = new Order();
			BooksDAO BookDaoObj = new BooksDAO();
			Book book = new Book();

			//forwardに使う<Book>ArrayList配列データを定義しとく
			ArrayList<Book> list =new ArrayList<Book>();

			//関連メソッドをorderlist（カートデータ）分だけ呼び出す。
			for(int i = 0;i<orderlist.size();i++) {
				//orderメソッドへi番目のOrderオブジェクトを入れる
				order = orderlist.get(i);
				//Bookオブジェクトへi番目のOrderオブジェクトのIsbnを引数にDAOのselectByIsbnメソッドを使い、書籍データを格納
				book = BookDaoObj.selectByIsbn(order.getIsbn());
				//取得した各Bookをlistへ追加する
				list.add(book);
			}
			//リクエストスコープにbooklistの名で登録
			request.setAttribute("booklist", list);
		}catch(IllegalStateException e) {
			error="DB接続エラーの為、カート状況は確認できません。";
		}finally {
			//エラーの有無でフォワード先を呼び分ける
			if(error.equals("DB接続エラーの為、カート状況は確認できません。")) {
				cmd="insertintocart";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
			else {
				//showCart.jspへフォワード
				request.getRequestDispatcher("/view/showCart.jsp").forward(request, response);
			}
		}
	}
}