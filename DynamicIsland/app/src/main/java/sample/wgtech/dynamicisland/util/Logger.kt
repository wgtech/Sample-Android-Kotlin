package sample.wgtech.dynamicisland.util

import android.util.Log

object Logger {

    private val TAG = "wgtech"

    fun i(msg: String) {
        Log.i(TAG, msg)
    }

    fun d(msg: String) {
        Log.d(TAG, msg)
    }
}