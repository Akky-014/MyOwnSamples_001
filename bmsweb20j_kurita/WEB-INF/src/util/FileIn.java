package util;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FileIn {
	private Scanner sin = null; // Scanner??ｽN??ｽ??ｽ??ｽX


	public boolean open(String fname) {
		boolean sts = true;
		try {
			sin = new Scanner(new File(fname));
		} catch (FileNotFoundException e) {
			sts = false;
		}
		return sts;
	}


	public String readLine() {
		String buff;


		if (sin.hasNextLine()) {
			buff = sin.nextLine();
		} else {
			buff = null;
		}
		return buff;
	}


	public boolean close() {
		boolean sts = true;
		try {
			sin.close();
		} catch (Exception e) {
			sts = false;
		}
		return sts;
	}
}
