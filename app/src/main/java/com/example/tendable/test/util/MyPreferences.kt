package com.example.tendable.test.util

import android.content.Context
import android.content.SharedPreferences
import com.example.tendable.test.R

class MyPreferences(context: Context) {

    private var editorUser: SharedPreferences.Editor? = null
    private var preferencesUser: SharedPreferences

    init {
        preferencesUser = context.applicationContext.getSharedPreferences(
            context.resources.getString(R.string.my_preference),
            Context.MODE_PRIVATE
        )
        editorUser = preferencesUser.edit()

    }

    fun clearAll() {
        editorUser?.clear()
        editorUser?.apply()
        editorUser?.commit()
    }

    fun putIsUserLoggedIn(userLoggedIn: Boolean) {
        editorUser!!.putBoolean(Constants.USER_LOGGED_IN, userLoggedIn)
        editorUser!!.apply()
    }

    fun getIsUserLoggedIn(): Boolean {
        return preferencesUser.getBoolean(Constants.USER_LOGGED_IN, false)
    }
}