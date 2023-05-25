package com.example.secureprogramming.core.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private const val PREFERENCE_NAME = "MyPreferences"
    private const val KEY_LOGIN_ATTEMPTS = "login_attempts"
    private const val KEY_LAST_LOGIN_TIME = "last_login_time"
    private const val KEY_SIGNUP_ATTEMPTS = "signup_attempts"
    private const val KEY_LAST_SIGNUP_TIME = "last_signup_time"
    private const val PASSWORD_HASH = "password_hash"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun incrementLoginAttempts() {
        val loginAttempts = getLoginAttempts()
        setLoginAttempts(loginAttempts + 1)
    }

    fun resetLoginAttempts() {
        setLoginAttempts(0)
    }

    fun incrementSignupAttempts() {
        val signupAttempts = getSignupAttempts()
        setSignupAttempts(signupAttempts + 1)
    }

    fun resetSignupAttempts() {
        setSignupAttempts(0)
    }

    fun getLoginAttempts(): Int {
        return sharedPreferences.getInt(KEY_LOGIN_ATTEMPTS, 0)
    }

    fun setLoginAttempts(loginAttempts: Int) {
        sharedPreferences.edit().putInt(KEY_LOGIN_ATTEMPTS, loginAttempts).apply()
    }

    fun getLastLoginTime(): Long {
        return sharedPreferences.getLong(KEY_LAST_LOGIN_TIME, 0)
    }

    fun setLastLoginTime(lastLoginTime: Long) {
        sharedPreferences.edit().putLong(KEY_LAST_LOGIN_TIME, lastLoginTime).apply()
    }

    fun getSignupAttempts(): Int {
        return sharedPreferences.getInt(KEY_SIGNUP_ATTEMPTS, 0)
    }

    fun setSignupAttempts(signupAttempts: Int) {
        sharedPreferences.edit().putInt(KEY_SIGNUP_ATTEMPTS, signupAttempts).apply()
    }

    fun getLastSignupTime(): Long {
        return sharedPreferences.getLong(KEY_LAST_SIGNUP_TIME, 0)
    }

    fun setLastSignupTime(lastSignupTime: Long) {
        sharedPreferences.edit().putLong(KEY_LAST_SIGNUP_TIME, lastSignupTime).apply()
    }


    fun setHashKey(password: String) {
        sharedPreferences.edit().putString(PASSWORD_HASH, password).apply()
    }

    fun getHashKey(): String {
        return sharedPreferences.getString(PASSWORD_HASH, "")!!
    }
}