from flask import Flask, request, jsonify
import pymysql

app = Flask(__name__)

# MySQLの設定
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = 'akinari14@'
app.config['MYSQL_DB'] = 'high_score_db'

# MySQL接続関数
def get_db_connection():
    return pymysql.connect(
        host=app.config['MYSQL_HOST'],
        user=app.config['MYSQL_USER'],
        password=app.config['MYSQL_PASSWORD'],
        database=app.config['MYSQL_DB'],
        cursorclass=pymysql.cursors.DictCursor  # 辞書形式で結果を返す
    )

# 上位3名を取得するAPIエンドポイント
@app.route('/top5', methods=['GET'])
def get_top_3_players():
    conn = get_db_connection()  # データベース接続を確立
    try:
        with conn.cursor() as cur:
            cur.execute("SELECT name, high_score FROM players ORDER BY high_score DESC LIMIT 5")
            top_players = cur.fetchall()

        result = []
        for player in top_players:
            result.append({
                'name': player['name'],
                'high_score': player['high_score']
            })

        return jsonify(result)
    except Exception as e:
        return str(e)
    finally:
        conn.close()  # 接続を閉じる

# スコアと名前を保存するAPIエンドポイント
@app.route('/save_score', methods=['POST'])
def save_score():
    data = request.get_json()  # JSONデータを取得
    name = data.get('name')
    score = data.get('score')

    if not name or score is None:
        return jsonify({'error': '名前とスコアが必要です。'}), 400

    conn = get_db_connection()
    try:
        with conn.cursor() as cur:
            # スコアと名前をデータベースに挿入
            cur.execute("INSERT INTO players (name, high_score) VALUES (%s, %s)", (name, score))
            conn.commit()
        return jsonify({'message': 'スコアが保存されました。'})
    except Exception as e:
        return str(e), 500
    finally:
        conn.close()  # 接続を閉じる

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)