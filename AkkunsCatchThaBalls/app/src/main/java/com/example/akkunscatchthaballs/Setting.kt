package com.example.akkunscatchthaballs

data class Setting(
    var boxSpeed: Float,
    var isakiSpeed: Float,
    var tunaSpeed: Float,
    var eelSpeed: Float,
    var bombSpeed: Float,
    var fukuroSpeed: Float,
    var kanSpeed: Float,
    var xyDefault: Float,
    var countDownTimeMax: Long,
    var stock: Int,
    )
//{
//    companion object {
//        @JvmStatic
//        fun settingMain(): Setting {
//            // speedは小さいほど速い
//            val easy = Setting(
//                60f,
//                70f,
//                55f,
//                50f,
//                60f,
//                60f,
//                60f,
//                -80.0f,
//                60000,
//                3,
//            )
//
//            // 今回はデフォルトでeasyをセット
//            val setting = easy
//
//            return setting
//        }
//    }
//}

fun settingMain() : Setting {
    // speedは小さいほど速い
    val easy = Setting(
        60f,
        70f,
        55f,
        50f,
        60f,
        60f,
        60f,
        -80.0f,
        60000,
        3,
    )

    // 今回はデフォルトでeasyをセット
    val setting = easy

    return setting
}

