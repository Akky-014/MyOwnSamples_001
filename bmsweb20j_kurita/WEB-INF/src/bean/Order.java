package bean;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
public class Order {

	private int orderno; 	//注文No
	private String userid;	//ユーザーID
	private String isbn;	//ISBN
	private int quantity; //数量
	private String date; //購入日付

	//コンストラクタの定義（初期化）
	public Order() {
		this.orderno=0;
		this.userid = null;
		this.isbn = null;
		this.quantity = 0;
		this.date = null;
	}

	//各フィールドのGetメソッド定義
	public int getOrderno() {
		return orderno;
	}
	public String getUserid() {
		return userid;
	}
	public String getIsbn() {
		return isbn;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getDate() {
		return date;
	}

	//各フィールド変数のSetメソッド定義
	public void setOrderno(int orderno) {
		this.orderno=orderno;
	}
	public void setUserid(String userid) {
		this.userid=userid;
	}
	public void setIsbn(String isbn) {
		this.isbn=isbn;
	}
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	public void setDate(String date) {
		this.date=date;
	}
}
