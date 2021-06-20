package util;
/**書籍管理システムVer2.0
 * 書籍管理システムVer1.0に機能を追加し、書籍の追加や削除、一覧機能の他にログイン・ログアウトおよびセッション機能、書籍の購入システムや
 *初期データ登録機能などができるようになったシステム
 *作成者：栗田晃成 作成日付：2019/03/27
 */
import java.util.Properties;
import java.util.Date;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

public class SendMailTestByGmail {


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

		soushin = userid+"様"+"\t"+mailtext1+"\t"+buythings+"\t"+mailtext2;
		return soushin;
	}

	//メソッドを作成
	public static String mailsender() {
		try {
			Properties props = System.getProperties();

			// SMTPサーバのアドレスを指定（今回はGmailのSMTPサーバを利用）
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("homework2common@gmail.com", "kanda2chiyoda3");
						}
					}
					);

			MimeMessage mimeMessage = new MimeMessage(session);


			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(new InternetAddress("info@kanda-it-school.com", "神田IT School", "iso-2022-jp"));

			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO, "americanidiot.41@gmail.com");

			// メールのタイトルを指定
			mimeMessage.setSubject("ご購入書籍", "iso-2022-jp");

			// メールの内容を指定
			mimeMessage.setText(soushin , "iso-2022-jp");

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);
			System.out.println("9");

			// 送信成功
			System.out.println("送信に成功しました。");

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("送信に失敗しました。\n" + e);
		}
		return "送信成功";
	}


	public static void main(String[] args) {

	}
}

