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
import dao.OrderDAO;
import util.SendMailTest;
import util.SendMailTestByGmail;

public class BuyConfirmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException ,IOException{

		//エラー表記を定義
		String error ="";
		String cmd ;

		//メール本文送付準備
		String texts="";

		//セッションオブジェクトの作成
		HttpSession session = request.getSession();

		//①セッションからuserのUserオブジェクトを取得する(セッション切れの場合はエラー）。
		User	user = (User)session.getAttribute("user");

		//セッション切れの場合
		if(user==null) {
			error="セッション切れの為、購入は出来ません。";
			//エラーなら「error.jsp」へフォワード、そうでなければinsertIntoCart.jspへフォワード
			cmd="insertintocart";
			request.setAttribute("cmd", cmd);
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}

		//②セッションから「order_list」のList配列を取得する
		ArrayList<Order> orderList = (ArrayList<Order>)session.getAttribute("orderlist");
		//カートの中身が空の場合は、error.jspへフォワードする。
		if(orderList==null||orderList.size()==0) {
			error="カートの中に何も無かったので、購入は出来ません。";
			cmd="menu";
			request.setAttribute("cmd", cmd);
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}

		//BookDAOとOrderDAOをインスタンス化
		BooksDAO objBookDAO = new BooksDAO();
		OrderDAO objOrderDAO = new OrderDAO();

		//forwardに使う<Book>ArrayList配列データを定義しとく
		ArrayList<Book> list =new ArrayList<Book>();

	try {

		//③関連メソッドをorderlist（カートデータ）分だけ呼び出す。
		for(int i = 0;i<orderList.size();i++) {
			//orderメソッドへi番目のOrderオブジェクトを入れる
			Order order = orderList.get(i);
			//Bookオブジェクトへi番目のOrderオブジェクトのIsbnを引数にDAOのselectByIsbnメソッドを使い、書籍データを格納
			Book book = objBookDAO.selectByIsbn(order.getIsbn());
			//OrderDAOオブジェクトを使って、orderクラスのオブジェクトを引数にデータベースへデータをアップデートする
			objOrderDAO.insert(order);

			//メール本文を作成
			texts +=book.getIsbn()+"	"+book.getTitle()+"	"+book.getPrice()+"\t";

			//④③文中に作成した各Bookオブジェクトをlistへ追加し、リクエストスコープへ"booklist"の名で登録する
			list.add(book);
		}
		request.setAttribute("booklist", list);

		//注文が無いならメールは送らない
		if(orderList.size()!=0) {
			//⑤orderlistの注文内容を各String型に作成
			String userid= user.getUserid();
			String mailtext1 = "本のご購入ありがとうございます。以下内容でご注文を受け付けましたので、ご連絡致します。";
			String buythings = texts;
			String mailtext2 = "またのご利用よろしくお願いします。";

			String soushin=SendMailTestByGmail.mailsetter(userid,mailtext1,buythings,mailtext2) ;
			System.out.println(soushin);

			//メール送信実行
			String kekka = SendMailTestByGmail.mailsender();
			System.out.println(kekka);
			//⑥セッションのorderlist情報をクリアにする。
			session.setAttribute("orderlist", null);
		}
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
			//⑦buy.Conrirm.jspへフォワードする
			request.getRequestDispatcher("/view/buyConfirm.jsp").forward(request, response);
		}
	}
	}
}

