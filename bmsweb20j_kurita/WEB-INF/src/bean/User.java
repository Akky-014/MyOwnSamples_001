package bean;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
public class User {

	private String userid; //ユーザー名
	private String password; //パスワード
	private String email;	//メールアドレス
	private String authority; //権限（１：一般ユーザ、２：管理者）

	//コンストラクタの定義
	public User() {
		this.userid = null;
		this.password = null;
		this.email = null;
		this.authority = null;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid=userid;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
	}

	public String getEmail() {
		return email;
	}
	public void  setEmail(String email) {
		this.email= email;
	}

	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority=authority;
	}

}
