package com.example.akkunscatchthaballs

import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

// Kotlin通信クラス
object NetworkUtils {

    private val client = OkHttpClient()

    // 非同期で通信を行う関数
    // @JvmStaticを追加してJavaからのアクセスを簡単にする
    @JvmStatic
    fun fetchTop5(callback: (String) -> Unit) {
        // Coroutineを使って非同期で通信を実行
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = fetchTop5FromServer()
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
    private suspend fun fetchTop5FromServer(): String {
        val request = Request.Builder()
            .url("http://52.199.204.161:5000/top5")
            .build()

        // OkHttpを使って非同期でリクエストを実行
        val response: Response = client.newCall(request).execute()
        return response.body?.string() ?: "No Data"
    }

    // スコアを非同期で保存する関数
    @JvmStatic
    fun saveScore(name: String, score: String, callback: (String) -> Unit) {
        // Coroutineを使って非同期で通信を実行
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = saveScoreToServer(name, score)
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

    // 実際にスコアをサーバーに送信するサスペンド関数
    private suspend fun saveScoreToServer(name: String, score: String): String {
        val jsonInputString = """{"name": "$name", "score": $score}"""
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = jsonInputString.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("http://52.199.204.161:5000/save_score")
            .post(requestBody)
            .build()

        // OkHttpを使って非同期でリクエストを実行
        val response: Response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            "Score saved successfully"
        } else {
            "Failed to save score"
        }
    }
}