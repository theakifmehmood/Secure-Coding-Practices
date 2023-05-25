package com.example.secureprogramming.feature.crud_password.data.repository.datasource

import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.util.Log
import com.example.secureprogramming.feature.authentication.data.repository.datasource.ANDROID_KEYSTORE_PROVIDER
import com.example.secureprogramming.feature.authentication.data.repository.datasource.PBKDF2_ALGORITHM
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.util.Base64
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class CryptoDataSource: CryptoSource {
    // Encrypt a string using the encryption key
    override fun encryptData(data: String, encryptionKey: SecretKeySpec): String {
        val fixedIV = byteArrayOf(
            0x54, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x46,
            0x69, 0x78, 0x65, 0x64, 0x49, 0x56, 0x46, 0x09
        )
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivSpec = IvParameterSpec(fixedIV)
        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, ivSpec)
        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encryptedData)
    }

    // Decrypt a string using the encryption key
    override fun decryptData(data: String, encryptionKey: SecretKeySpec): String {
        val fixedIV = byteArrayOf(
            0x54, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x46,
            0x69, 0x78, 0x65, 0x64, 0x49, 0x56, 0x46, 0x09
        )
        val encryptedData = Base64.getDecoder().decode(data)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivSpec = IvParameterSpec(fixedIV)
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey, ivSpec)
        val decryptedData = cipher.doFinal(encryptedData)
        return String(decryptedData, Charsets.UTF_8)
    }


    override fun encryptionKey(): SecretKeySpec {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        return keyStore.getKey("MyEncryptionKey", null) as SecretKeySpec
    }


}