package com.example.akkunscatchthaballs.data

import com.google.gson.annotations.SerializedName

data class HighScore(
    @SerializedName("high_score")
    var highScore: Int = 0,
    var name: String = ""
)
