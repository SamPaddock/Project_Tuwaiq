package com.saraha.paws.Util

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

enum class SharedConst(var string: String){
    PrefsName("cats"),
    PrefsFactDate("fact_time")
}

class AppSharedPreference {

    companion object {
        private var prefs: SharedPreferences? = null

        fun init(context: Context) {
            if (prefs == null){
                prefs = context.getSharedPreferences(SharedConst.PrefsName.string, Context.MODE_PRIVATE)
            }
        }
    }

    fun read(key: String, value: String): String? {
        return prefs?.getString(key, value)
    }

    fun read(key: String, value: Long): Long? {
        return prefs?.getLong(key, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs?.edit()!!
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    fun write(key: String, value: Long) {
        val prefsEditor: SharedPreferences.Editor = prefs?.edit()!!
        with(prefsEditor) {
            putLong(key, value)
            commit()
        }
    }

}