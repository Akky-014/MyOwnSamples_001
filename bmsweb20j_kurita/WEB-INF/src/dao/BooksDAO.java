package dao;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.sql.*;
import java.util.ArrayList;
import bean.Book;

public class BooksDAO {

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

	//selectAllメソッド
	public ArrayList<Book> selectAll(){

		Connection con = null;
		Statement smt = null;
		//①検索した書籍情報を格納するAllayListオブジェクトを生成
		ArrayList<Book> bookList = new ArrayList<Book>();

		try{
			//②SQL文を文字列として定義
			String sql = "SELECT isbn ,title,price FROM bookinfo ORDER BY isbn";

			//③BookDAOクラスに定義した、getConnection()メソッドを利用してConnectionオブジェクトを生成
			con = BooksDAO.getConnection();

			//④ConnectionオブジェクトのcreateStatement（）メソッドを利用してStatementオブジェクトを生成
			smt = con.createStatement();

			//⑤Statementオブジェクトの、executeQuery（）メソッドを利用して、②のSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			//⑥結果セットから書籍データを検索件数分全て取り出し、AllayListオブジェクトにBookオブジェクトとして格納
			while(rs.next()) {
				Book book = new Book();
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
				bookList.add(book);
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
		return bookList;
	}
	//insertメソッド
	public void insert(Book book){

		Connection con = null;
		Statement smt = null;

		//確認用変数
		int count=0;

		try{
			//①引数の情報を利用し、登録用のSQL文を文字列として定義
			String sql = "INSERT INTO bookinfo VALUES ('"+book.getIsbn()+"','"+book.getTitle()+"',"+book.getPrice()+")";

			//②BookDAOクラスに定義した、getConnection()メソッドを利用してConnectionオブジェクトを生成
			con = BooksDAO.getConnection();

			//③ConnectionオブジェクトのcreateStatement（）メソッドを利用してStatementオブジェクトを生成
			smt=con.createStatement();

			//④Statementオブジェクトの、executeUpdate（）メソッドを利用して、①のSQL文を発行し書籍データを登録
			count = smt.executeUpdate(sql);

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

	//データベースから指定された書籍データを検索しBookオブジェクトに格納するインスタンスメソッド「selectByIsbn」を定義
	public Book selectByIsbn(String isbn){

		Connection con = null;
		Statement smt = null;
		//①検索した書籍情報を格納するBookオブジェクト
		Book book = new Book();

		try{

			//②引数の情報を利用し、検索用のSQL文を文字列として定義
			String sql = "SELECT isbn,title,price FROM bookinfo WHERE isbn ='"+isbn+"'";

			//③BookDAOクラスに定義した、getConnection()メソッドを利用してConnectionオブジェクトを生成
			con = BooksDAO.getConnection();

			//④ConnectionオブジェクトのcreateStatement（）メソッドを利用してStatementオブジェクトを生成
			smt = con.createStatement();

			//⑤Statementオブジェクトの、executeQuery（）メソッドを利用して、②のSQL文を発行し結果セットを取得
			ResultSet rs = smt.executeQuery(sql);

			//⑥結果セットから書籍データを検索件数分全て取り出し、AllayListオブジェクトにBookオブジェクトとして格納
			while(rs.next()) {
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
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
		return book;
	}

	//削除メソッド
	public void delete(String isbn){

		Connection con = null;
		Statement smt = null;
		//確認用変数
		int count=0;

		try{
			//①引数の情報を利用し、削除用のSQL文を文字列として定義
			String sql = "DELETE FROM bookinfo WHERE isbn = '"+isbn+"'";

			//②BookDAOクラスに定義した、getConnection()メソッドを利用してConnectionオブジェクトを生成
			con = BooksDAO.getConnection();

			//③ConnectionオブジェクトのcreateStatement（）メソッドを利用してStatementオブジェクトを生成
			smt=con.createStatement();

			//④Statementオブジェクトの、executeUpdate（）メソッドを利用して、①のSQL文を発行し書籍データを登録
			count = smt.executeUpdate(sql);
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

	//updateメソッド
	public void update(Book book){

		Connection con = null;
		Statement smt = null;

		try{
			//sql文
			String sql= "UPDATE bookinfo Set title='"+book.getTitle()+"',price="+book.getPrice()+" WHERE isbn='"+ book.getIsbn()+"'";
			System.out.println(sql);
			//	con
			con = BooksDAO.getConnection();
			//smt
			smt= con.createStatement();
			//sql送信
			int count = smt.executeUpdate(sql);
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

	//searchメソッド
	public ArrayList<Book> search(String isbn,String title,String price){

		Connection con = null;
		Statement smt = null;

		//ArrayList配列
		ArrayList<Book> books = new ArrayList<Book>();

		try{
			//sql文
			String sql="SELECT isbn,title,price FROM bookinfo " + "WHERE isbn LIKE '%" + isbn + "%' AND title LIKE '%" +title+ "%' AND price LIKE '%" + price+ "%'";
			System.out.println(sql);
			//con(接続）
			con = BooksDAO.getConnection();
			//smt（送信準備）
			smt= con.createStatement();
			//sqlへそうしん
			ResultSet rs = smt.executeQuery(sql);

			//結果を格納
			while(rs.next()){
				Book book = new Book();
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(Integer.parseInt(rs.getString("price")));
				books.add(book);
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
		return books;
	}
}