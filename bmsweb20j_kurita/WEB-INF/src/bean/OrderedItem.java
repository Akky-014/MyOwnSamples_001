package bean;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
public class OrderedItem {
	private String userid;	//ユーザーID
	private String title;	//書籍のタイトル
	private String date; //購入日付

	//コンストラクタの定義（初期化）
	public OrderedItem() {
		this.userid = null;
		this.title = null;
		this.date = null;
	}

	//各フィールドのGetメソッド定義
	public String getUserid() {
		return userid;
	}
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}

	//各フィールド変数のSetメソッド定義
	public void setUserid(String userid) {
		this.userid=userid;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public void setDate(String date) {
		this.date=date;
	}
}