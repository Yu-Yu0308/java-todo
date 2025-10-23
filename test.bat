@echo off
echo ========================================
echo Java Todo アプリケーション テスト
echo ========================================
echo.

echo 1. コンパイル中...
javac -encoding UTF-8 *.java
if %errorlevel% neq 0 (
    echo コンパイルエラーが発生しました。
    pause
    exit /b 1
)

echo 2. アプリケーションを起動します...
echo.
echo テスト手順:
echo - 既存のTodoが表示されることを確認
echo - 新しいTodoを追加してみる
echo - 既存のTodoを削除してみる
echo - 既存のTodoを編集してみる
echo - 終了機能をテスト
echo.
echo 準備ができたら何かキーを押してください...
pause

java -Dfile.encoding=UTF-8 Todo

echo.
echo テスト完了
pause
