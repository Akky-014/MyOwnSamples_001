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
import util.FileIn;

public class InsertIniDataServlet extends HttpServlet {
	//データ分割用定数
	private final static String STR_COMMA = ",";
	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

		String cmd ;
		String error="";
		String path="" ;
		ArrayList<Book> list  = new ArrayList<Book>();


		//提供ライブラリのオブジェクト (インスタンス化)
		FileIn objFileIn = new FileIn();

		try{

			//①BookDAOクラスのオブジェクトを生成し、関連メソッドを呼び出す。②また戻り値として、listを取得
			BooksDAO dao = new BooksDAO();
			list = dao.selectAll();



			//③listに一件でも書籍データがあれば、error.jspへフォワード
			if(list.size() !=0) {
				error = "DBにはデータが存在するので、初期データは登録できません。";
				System.out.println("era-ga");
				cmd="list";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			//④初期データ用CSVファイルよりデータを取得する
			path = getServletContext().getRealPath("file\\initial_data.csv");
			System.out.println(path);

			//String型strLineを定義
			String[] strLine;

			//String型の変数strを定義
			String str;

			//FileInクラスを利用して書籍データファイルをオープンする。
			if (objFileIn.open( path ) == false ) {
				error = "初期データファイルが無い為、登録は行えません。";
				cmd="menu";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			//読み込み可能なデータが無くなるまで繰り返す
			while( ( str = objFileIn.readLine() ) != null ) {

				//④splitメソッドを利用して、読み込んだ書籍データを,(カンマ)で分割した配列データを受け取る。
				strLine = str.split ( STR_COMMA );

				//⑤分割した配列データがヘッダーデータだった場合には処理をスキップし、ヘッダーデータではない場合には各ArrayListオブジェクトに格納する。
				Book book = new Book();
				book.setIsbn( strLine[0]);
				book.setTitle ( strLine[1]);
				book.setPrice ( Integer.parseInt ( strLine[2] ) );
				//取得した各bookをListへ追加
				list.add(book);
				//各Bookオブジェクトを引数にして、BookDAOオブジェクトを生成し、関連メソッドを呼ぶ
				dao.insert(book);
			}
		}catch( NumberFormatException e) {
			error="初期データファイルに不備がある為、登録は行えません。";
		}catch(IllegalStateException e) {
			error="DB接続エラーの為、ログインは出来ません。";
		}finally {
			if(error.equals("初期データファイルに不備がある為、登録は行えません。")) {
				cmd="menu";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error",error );
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
			//errorの値が"データベース接続エラーなら"「error.jsp」へフォワード
			else if(error.equals("DB接続エラーの為、ログインは出来ません。")) {
				cmd="logout";
				request.setAttribute("cmd", cmd);
				request.setAttribute("error",error );
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}else if(error.equals("")){
				//リクエストスコープへ登録
				request.setAttribute("booklist", list);

				//insertIniData.jspへフォワード
				request.getRequestDispatcher("/view/insertIniData.jsp").forward(request, response);

				//⑦FileInクラスを利用して書籍データファイルをクローズする。
				objFileIn.close();
			}
		}
	}
}
