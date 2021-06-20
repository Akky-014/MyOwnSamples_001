package dao;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.sql.*;

import bean.Book;
import bean.User;

public class userDAO {

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

	//DBのuserinfoテーブルから指定ユーザーとパスワードの条件に合致する情報を取得するメソッド定義
	public User selectByUser(String userid,String password) {

		//コネクション型の変数とステイトメント型の変数を初期化
		Connection con = null;
		Statement smt = null;

		//戻り値用データであるクラスUser型のオブジェクト、userを作成
		User user = new User();

		try{
			//②SQL文を文字列として定義
			String sql = "SELECT * FROM userinfo WHERE user='"+userid+"' and password='"+password+"' ";

			//③BookDAOクラスに定義した、getConnection()メソッドを利用してConnectionオブジェクトを生成
			con = userDAO.getConnection();

			//④ConnectionオブジェクトのcreateStatement（）メソッドを利用してStatementオブジェクトを生成
			smt = con.createStatement();

			//⑤Statementオブジェクトの、executeQuery（）メソッドを利用して、②のSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			//⑥結果セットから書籍データを検索件数分全て取り出し、AllayListオブジェクトにBookオブジェクトとして格納
			while(rs.next()) {
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));

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
		return user;
	}
}
