package util;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.text.DecimalFormat;

public class MyFormat {

	//【MyFormatクラス】
	public String moneyFormat ( int price ) {

		//DecimalFormatクラスでインスタンス化する際に利用するフォーマットを引数で指定する（\で３カンマ式に価格を変換？）
		DecimalFormat df = new DecimalFormat("\u00A5###,##0.00");
		return df.format ( price ) ;
	}
}
