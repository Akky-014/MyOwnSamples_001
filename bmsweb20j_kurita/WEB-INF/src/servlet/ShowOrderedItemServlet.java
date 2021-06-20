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
import bean.OrderedItem;
import bean.User;
import dao.BooksDAO;
import dao.OrderedItemDAO;

public class ShowOrderedItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException{

		//エラー表記を定義
		String error ="";
		String cmd ;

		//①OrderedItemDAOをインスタンス化
		OrderedItemDAO oid = new OrderedItemDAO();

	try {
			//②戻り値として、OrderedItemオブジェクトのリスト（List）を取得する
			ArrayList<OrderedItem> list = oid.selectAll();

			//③リクエストスコープにorderedlistの名で登録
			request.setAttribute("orderedlist", list);

		}catch(IllegalStateException e) {
			error="DB接続エラーの為、購入状況確認は出来ません。";
		}finally {
			//エラーの有無でフォワード先を呼び分ける
			if(error.equals("DB接続エラーの為、購入状況確認は出来ません。")) {
				cmd="insertintocart";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}else {
				//④ShowOrderedItem.jspへフォワード
				request.getRequestDispatcher("/view/ShowOrderedItem.jsp").forward(request, response);
			}
		}
	}
}
