package sample.wgtech.dynamicisland.database

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {
    private val fileName = "wgtech_dynamic_island.prefs"

    private fun getSharedPreferences(applicationContext: Context): SharedPreferences {
        return applicationContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun setBoolean(applicationContext: Context, key: String, value: Boolean) {
        getSharedPreferences(applicationContext).edit().putBoolean(key, value).apply()
    }

    fun getBoolean(applicationContext: Context, key: String) : Boolean {
        return getSharedPreferences(applicationContext).getBoolean(key, false)
    }
}