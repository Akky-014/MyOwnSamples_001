package dao;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.sql.*;

import bean.Order;
import bean.User;
public class OrderDAO {

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

	//引数の購入データを元にDBのorderinfoテーブルに新規登録処理を行うメソッド定義
	public void insert(Order order) {

		//コネクション型の変数とステイトメント型の変数を初期化
		Connection con = null;
		Statement smt = null;

		//カウント変数初期化
		int count = 0;

		try{
			//②SQL文を文字列として定義
			String sql = "INSERT INTO orderinfo VALUES (NULL,'"+order.getUserid()+"','"+order.getIsbn()+"',"+order.getQuantity()+", CURDATE())";
			System.out.println(sql);
			//③BookDAOクラスに定義した、getConnection()メソッドを利用してConnectionオブジェクトを生成
			con = OrderDAO.getConnection();

			//④ConnectionオブジェクトのcreateStatement（）メソッドを利用してStatementオブジェクトを生成
			smt = con.createStatement();

			//⑤Statementオブジェクトの、executeUpdate（）メソッドを利用して、②のSQL文を発行し結果カウントを取得
			count = smt.executeUpdate(sql);
			System.out.println(count);
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
	}
}
