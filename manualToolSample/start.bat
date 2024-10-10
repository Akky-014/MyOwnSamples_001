@echo off
SETLOCAL EnableDelayedExpansion

REM ログファイルを設定
set LOGFILE=batch_log.txt
echo Batch process started > %LOGFILE%

REM データベース関連の環境変数を設定
set DB_NAME=django_db
set DB_USER=postgres
set DB_PASSWORD=postgres
set DB_HOST=localhost

REM Pythonのパス設定
set PYTHON_PATH=C:\workspace\manualToolSample\python\python.exe
set PYTHON_INSTALLER=C:\workspace\manualToolSample\python-installer.exe
set PYTHON_DOWNLOAD_URL=https://www.python.org/ftp/python/3.9.9/python-3.9.9-amd64.exe

REM Pythonのインストール確認
if not exist %PYTHON_PATH% (
    echo Python not found, starting download... >> %LOGFILE%
    
    REM Pythonのインストーラーをダウンロード
    if not exist %PYTHON_INSTALLER% (
        echo Downloading Python installer... >> %LOGFILE%
        powershell -Command "Invoke-WebRequest -Uri %PYTHON_DOWNLOAD_URL% -OutFile %PYTHON_INSTALLER%"
        IF ERRORLEVEL 1 (
            echo Error downloading Python installer >> %LOGFILE%
            echo Python download failed! Detailed error log: >> %LOGFILE%
            echo ----------------------------------------------------------- >> %LOGFILE%
            type %LOGFILE%  REM ログファイルの内容をコンソールに出力
            echo ----------------------------------------------------------- >> %LOGFILE%
            exit /b
        )
    )
    
    REM Pythonのインストール実行
    echo Installing Python... >> %LOGFILE%
    start /wait "" %PYTHON_INSTALLER% /quiet InstallAllUsers=0 PrependPath=1 TargetDir=C:\workspace\manualToolSample\python
    IF ERRORLEVEL 1 (
        echo Error during Python installation >> %LOGFILE%
        echo Python installation failed! Detailed error log: >> %LOGFILE%
        echo ----------------------------------------------------------- >> %LOGFILE%
        type %LOGFILE%
        echo ----------------------------------------------------------- >> %LOGFILE%
        exit /b
    )
) else (
    echo Python found, skipping installation. >> %LOGFILE%
)

REM PostgreSQLのインストール確認
set POSTGRES_PATH="C:\Program Files\PostgreSQL\17\bin\psql.exe"
if not exist %POSTGRES_PATH% (
    echo PostgreSQL not found, starting installation... >> %LOGFILE%
    
    REM PostgreSQLのインストーラーを実行
    if exist "C:\workspace\manualToolSample\postgresql-17.0-rc1-windows-x64.exe" (
        echo PostgreSQL installer found, running installation... >> %LOGFILE%
        start /wait "" "C:\workspace\manualToolSample\postgresql-17.0-rc1-windows-x64.exe" --mode unattended --superpassword postgres --servicename postgresql-17
        IF ERRORLEVEL 1 (
            echo Error during PostgreSQL installation >> %LOGFILE%
            echo PostgreSQL installation failed! Detailed error log: >> %LOGFILE%
            echo ----------------------------------------------------------- >> %LOGFILE%
            type %LOGFILE%
            echo ----------------------------------------------------------- >> %LOGFILE%
            exit /b
        )
        
        REM サービスを自動起動設定にする
        sc config postgresql-17 start= auto
        net start postgresql-17

    ) else (
        echo PostgreSQL installer not found in C:\workspace\manualToolSample\ >> %LOGFILE%
        echo Please download and place postgresql-17.0-rc1-windows-x64.exe in the correct directory.
        exit /b
    )
) else (
    echo PostgreSQL found, skipping installation. >> %LOGFILE%

    REM サービスが実行中か確認し、起動していなければ起動
    sc query postgresql-17 | findstr /I "RUNNING" >nul
    IF ERRORLEVEL 1 (
        echo Starting PostgreSQL service... >> %LOGFILE%
        net start postgresql-17
    )
)

REM PostgreSQLデータベースの作成
echo Checking and creating PostgreSQL database if it does not exist...
set PGPASSWORD=postgres
"C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -lqt | findstr /I "django_db" >nul
IF ERRORLEVEL 1 (
    echo "django_db" does not exist, creating database... >> %LOGFILE%
    "C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -c "CREATE DATABASE django_db;" >> %LOGFILE% 2>&1
    IF ERRORLEVEL 1 (
        echo Error creating PostgreSQL database >> %LOGFILE%
        echo PostgreSQL database creation failed! Detailed error log: >> %LOGFILE%
        echo ----------------------------------------------------------- >> %LOGFILE%
        type %LOGFILE%
        echo ----------------------------------------------------------- >> %LOGFILE%
        exit /b
    )
) ELSE (
    echo "django_db" already exists, skipping creation. >> %LOGFILE%
)

REM Python仮想環境の作成
IF NOT EXIST "venv" (
    echo Creating virtual environment... >> %LOGFILE%
    
    REM 仮想環境作成時の詳細ログ出力
    "%PYTHON_PATH%" -m venv venv >> %LOGFILE% 2>&1
    
    REM 仮想環境作成が失敗した場合にエラー詳細を表示
    IF ERRORLEVEL 1 (
        echo Error creating virtual environment >> %LOGFILE%
        echo Virtual environment creation failed! Detailed error log: >> %LOGFILE%
        echo ----------------------------------------------------------- >> %LOGFILE%
        type %LOGFILE%
        echo ----------------------------------------------------------- >> %LOGFILE%
        exit /b
    )
)

REM 仮想環境の有効化
echo Activating virtual environment... >> %LOGFILE%
call venv\Scripts\activate
IF ERRORLEVEL 1 (
    echo Error activating virtual environment >> %LOGFILE%
    echo Virtual environment activation failed! Detailed error log: >> %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    type %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    exit /b
)

REM 依存パッケージのインストールと確認
echo Installing dependencies... >> %LOGFILE%
pip install -r requirements.txt >> %LOGFILE% 2>&1
IF ERRORLEVEL 1 (
    echo Error installing dependencies >> %LOGFILE%
    echo Dependency installation failed! Detailed error log: >> %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    type %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    exit /b
)

REM 依存パッケージがインストールされたことを確認
echo Checking installed packages... >> %LOGFILE%
pip list >> %LOGFILE% 2>&1
IF ERRORLEVEL 1 (
    echo Error checking installed packages >> %LOGFILE%
    echo Package check failed! Detailed error log: >> %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    type %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    exit /b
)

REM 仮想環境内のPythonを使用してDjangoマイグレーションを実行
echo Running migrations... >> %LOGFILE%
venv\Scripts\python.exe C:\workspace\manualToolSample\manualToolSample\manage.py makemigrations >> %LOGFILE% 2>&1
venv\Scripts\python.exe C:\workspace\manualToolSample\manualToolSample\manage.py migrate >> %LOGFILE% 2>&1
IF ERRORLEVEL 1 (
    echo Error during migrations >> %LOGFILE%
    echo Migrations failed! Detailed error log: >> %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    type %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    exit /b
)

REM insert_data.pyの実行
echo Running insert_data.py... >> %LOGFILE%
venv\Scripts\python.exe C:\workspace\manualToolSample\manualToolSample\insert_data.py >> %LOGFILE% 2>&1
IF ERRORLEVEL 1 (
    echo Error running insert_data.py >> %LOGFILE%
    echo insert_data.py failed! Detailed error log: >> %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    type %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    exit /b
)

REM スーパーユーザーの自動作成
echo Creating superuser if not exists... >> %LOGFILE%
venv\Scripts\python.exe C:\workspace\manualToolSample\manualToolSample\manage.py shell -c "from django.contrib.auth import get_user_model; User = get_user_model(); User.objects.create_superuser('admin', 'admin@example.com', 'adminpassword') if not User.objects.filter(username='admin').exists() else print('Superuser already exists')" >> %LOGFILE% 2>&1
IF ERRORLEVEL 1 (
    echo Error creating superuser >> %LOGFILE%
    echo Superuser creation failed! Detailed error log: >> %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    type %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    exit /b
)

REM Django開発サーバーの起動
echo Starting Django server... >> %LOGFILE%
start "" http://localhost:8000  REM ブラウザを自動で開く
venv\Scripts\python.exe C:\workspace\manualToolSample\manualToolSample\manage.py runserver 0.0.0.0:8000 >> %LOGFILE% 2>&1
IF ERRORLEVEL 1 (
    echo Error starting Django server >> %LOGFILE%
    echo Django server failed to start! Detailed error log: >> %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    type %LOGFILE%
    echo ----------------------------------------------------------- >> %LOGFILE%
    exit /b
)

echo Batch process completed >> %LOGFILE%