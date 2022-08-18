package com.ahmed.shortener.utils

import android.content.Context
import android.content.SharedPreferences

class Preferencer(context: Context) {
    companion object {
        private const val PREFERENCE_FILE_NAME = "MAIN_PREFERENCE_FILE"
    }

    private val sharedPreference: SharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        sharedPreference.edit().run {
            putString(key, value)
        }.apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreference.getString(key, defaultValue)!!
    }

}