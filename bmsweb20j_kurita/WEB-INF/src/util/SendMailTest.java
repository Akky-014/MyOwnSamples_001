package util;
import java.util.Properties;
import java.util.Date;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
public class SendMailTest {

	static String userid ;
	static String mailtext1 ;
	static String buythings ;
	static String mailtext2 ;
	static String soushin;

	//メソッドを作成
	public static String mailsetter(String userid,String mailtext1,String buythings,String mailtext2) {
		SendMailTestByGmail.userid=userid;
		SendMailTestByGmail.mailtext1=mailtext1;
		SendMailTestByGmail.buythings=mailtext2;
		SendMailTestByGmail.mailtext2=mailtext2;

		String soushin = userid+"様"+"\t"+mailtext1+"\t"+buythings+"\t"+mailtext2;
		return soushin;
	}

	//メソッドを作成
	public static String mailsender() {
		try {
			Properties props = System.getProperties();
			// SMTPサーバーのアドレスを指定
			props.put("mail.smtp.host","192.168.1.223");
			Session session=Session.getDefaultInstance(props,null);
			MimeMessage mimeMessage=new MimeMessage(session);

			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(new InternetAddress("info@kanda-it-school.com","神田IT School","iso-2022-jp"));

			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO, "americanidiot.41@gmail.com");

			// メールのタイトルを指定
			mimeMessage.setSubject("Hello World","iso-2022-jp");

			// メールの内容を指定
			mimeMessage.setText(soushin , "iso-2022-jp");

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type","text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);

			//送信成功
			System.out.println("送信に成功しました。");

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("送信に失敗しました。\n"+e);
		}
		return buythings;
	}
}