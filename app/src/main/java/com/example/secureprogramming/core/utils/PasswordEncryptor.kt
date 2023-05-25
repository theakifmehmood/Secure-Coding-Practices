package com.example.secureprogramming.core.utils

import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

// Define the encryption algorithm and the key
private const val ALGORITHM = "AES"
private const val KEY = "mySecretKey12345"

class PasswordEncryptor {
    // Encrypt the password
    fun encrypt(password: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val key: Key = SecretKeySpec(KEY.toByteArray(), ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedPassword = cipher.doFinal(password.toByteArray())
        return Base64.encodeToString(encryptedPassword, Base64.DEFAULT)
    }

    // Decrypt the password
    fun decrypt(encryptedPassword: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val key: Key = SecretKeySpec(KEY.toByteArray(), ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedPassword = cipher.doFinal(Base64.decode(encryptedPassword, Base64.DEFAULT))
        return String(decryptedPassword)
    }
}