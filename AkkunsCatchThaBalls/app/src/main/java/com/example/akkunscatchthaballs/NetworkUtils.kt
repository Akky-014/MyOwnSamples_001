package com.example.akkunscatchthaballs

import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

// Kotlin通信クラス
object NetworkUtils {

    private val client = OkHttpClient()

    // 非同期で通信を行う関数
    // @JvmStaticを追加してJavaからのアクセスを簡単にする
    @JvmStatic
    fun fetchTop3(callback: (String) -> Unit) {
        // Coroutineを使って非同期で通信を実行
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = fetchTop3FromServer()
                // コールバックで結果をJavaに返す
                withContext(Dispatchers.Main) {
                    callback(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    callback("Error: ${e.message}")
                }
            }
        }
    }

    // 実際にサーバーからデータを取得するサスペンド関数
    private suspend fun fetchTop3FromServer(): String {
        val request = Request.Builder()
            .url("http://192.168.3.40:5000/top3")
            .build()

        // OkHttpを使って非同期でリクエストを実行
        val response: Response = client.newCall(request).execute()
        return response.body?.string() ?: "No Data"
    }
}