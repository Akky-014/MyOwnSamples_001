package dao;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.sql.*;
import java.util.ArrayList;
import bean.Book;
import bean.OrderedItem;

public class OrderedItemDAO {

	//データベース接続情報
	private static String RDB_DRIVE ="com.mysql.jdbc.Driver";
	private static String URL ="jdbc:mysql://localhost/mybookdb";
	private static String USER ="root";
	private static String PASS ="root123";

	private static Connection getConnection(){
		try{
			Class.forName(RDB_DRIVE);
			Connection con = DriverManager.getConnection(URL,USER,PASS);
			return con;
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
	}

	//DBのorderinfoとbookinfoテーブルを結合して、購入情報を取得するselectAllメソッド
	public ArrayList<OrderedItem> selectAll(){

		Connection con = null;
		Statement smt = null;
		//①検索した書籍情報を格納するAllayListオブジェクトを生成
		ArrayList<OrderedItem> List = new ArrayList<OrderedItem>();

		try{
			//②SQL文を文字列として定義
			String sql = "SELECT o.user,b.title,o.date from bookinfo b inner join orderinfo o on b.isbn=o.isbn";

			//③BookDAOクラスに定義した、getConnection()メソッドを利用してConnectionオブジェクトを生成
			con = OrderedItemDAO.getConnection();

			//④ConnectionオブジェクトのcreateStatement（）メソッドを利用してStatementオブジェクトを生成
			smt = con.createStatement();

			//⑤Statementオブジェクトの、executeQuery（）メソッドを利用して、②のSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			//⑥結果セットから書籍データを検索件数分全て取り出し、AllayListオブジェクトにBookオブジェクトとして格納
			while(rs.next()) {
				OrderedItem item = new OrderedItem();
				item.setUserid(rs.getString("user"));
				item.setTitle(rs.getString("title"));
				item.setDate(rs.getString("date"));
				List.add(item);
			}


		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			if( smt != null ){
				try{smt.close();}catch(SQLException ignore){}
			}
			if( con != null ){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return List;
	}
}
