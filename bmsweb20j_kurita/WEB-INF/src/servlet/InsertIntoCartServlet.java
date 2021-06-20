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

public class InsertIntoCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException{

		//エラー表記を定義
		String error ="";
		String cmd ;


		//セッションオブジェクトの作成
		HttpSession session = request.getSession();



		//セッションからuserのUserオブジェクトを取得する(セッション切れの場合はエラー）。
		User	user = (User)session.getAttribute("user");


		//該当書物のisbnのパラメータ、useridを習得する。数量も設定
		String isbn = request.getParameter("isbn");

		try {
			//BookDAOをインスタンス化して、関連メソッドを呼び出す
			BooksDAO dao = new BooksDAO();
			Book book = dao.selectByIsbn(isbn);

			//取得したBookオブジェクトをリクエストスコープにBookの名で登録する
			request.setAttribute("Book", book);

			//Orderのインスタンスを生成して、各セッターメソッドを利用し、isbn,userid(ログイン者）,数量（１固定）を設定する
			Order order = new Order();
			order.setIsbn(isbn);
			order.setUserid(user.getUserid());
			order.setQuantity(1);

			//セッションから「order_list」のList配列を取得する(取得できなかった場合は、ArrayList<Order>配列を新規で作成する）
			ArrayList<Order> list = (ArrayList<Order>)session.getAttribute("orderlist");
			if(list==null) {
				list= new ArrayList<Order>();
			}
			//OrderオブジェクトをList配列に追加し、セッションスコープにorderlistの名で登録する。
			list.add(order);
			session.setAttribute("orderlist",list);

		}catch(IllegalStateException e) {
			error="データベース接続エラーの為、カートに追加できません。";
		}finally {
			//エラーの有無でフォワード先を呼び分ける
			//セッション切れの場合
			if(user==null) {
				error="セッション切れの為、カートに追加出来ません。";
				//エラーなら「error.jsp」へフォワード、そうでなければinsertIntoCart.jspへフォワード
				cmd="insertintocart";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
			else if(error.equals("データベース接続エラーの為、カートに追加できません。")) {
				cmd="insertintocart";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
			else {
				//エラーでないなら、insertIntoCart.jspへフォワード
				request.getRequestDispatcher("/view/insertIntoCart.jsp").forward(request, response);
			}
		}
	}
}
