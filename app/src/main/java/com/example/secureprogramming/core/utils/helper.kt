package com.example.secureprogramming.core.utils

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import com.example.secureprogramming.R
import com.example.secureprogramming.feature.authentication.data.repository.datasource.ANDROID_KEYSTORE_PROVIDER
import com.example.secureprogramming.feature.authentication.data.repository.datasource.PBKDF2_ALGORITHM
import java.security.KeyStore
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object helper {

    public fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun encryptionKey(): SecretKeySpec {
        val salt = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10)
        // Use PBKDF2 with the user's password and the salt to derive an encryption key
        val iterationCount = 1000 // The number of iterations to use for the PBKDF2 function
        val keyLength = 256 // The length of the key to generate, in bits
        val keySpec = PBEKeySpec(MySharedPreferences.getHashKey().toCharArray(), salt, iterationCount, keyLength)
        val keyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM)
        val rawKey = keyFactory.generateSecret(keySpec).encoded
        return SecretKeySpec(rawKey, "AES")
    }
}