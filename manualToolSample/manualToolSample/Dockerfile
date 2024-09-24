# ベースイメージとしてPython 3.7を使用
FROM python:3.7

# 作業ディレクトリを設定
WORKDIR /usr/src/app

# 依存関係をコピーしてインストール
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# プロジェクト全体をコピー
COPY . .

# ポートを開放
EXPOSE 8000

# コンテナ起動時のデフォルトコマンドを設定
CMD ["bash", "-c", "./entrypoint.sh"]