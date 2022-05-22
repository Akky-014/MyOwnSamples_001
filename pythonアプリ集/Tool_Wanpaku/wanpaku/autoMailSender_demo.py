import datetime
import smtplib, ssl
from email.mime.text import MIMEText

import sys, codecs
sys.stdout = codecs.getwriter('utf_8')(sys.stdout)

# メールアドレスの設定
gmail_account = 'kurichandemokankyou@gmail.com'
gmail_password = 'purplehaze14'
mail_to = 'americanidiot.41@gmail.com'
name = ''

# 開催日時
held_date = '2022年5月9日(日)'

# 件名
subject = 'Sample_第４６回渋谷区わんぱく相撲ご参加のお礼'.format(name)
body = '{0}にて開催された第４６回渋谷区わんぱく相撲にご参加いただき、誠にありがとうございます。'.format(held_date)

msg = MIMEText(body, 'html')
msg['Subject'] = subject
msg['To'] = mail_to
msg['From'] = gmail_account

server = smtplib.SMTP_SSL('smtp.gmail.com', 465, context=ssl.create_default_context())
server.login(gmail_account, gmail_password)
server.send_message(msg)
print('送信完了')




