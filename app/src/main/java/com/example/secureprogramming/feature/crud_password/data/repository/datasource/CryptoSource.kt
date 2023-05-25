package com.example.secureprogramming.feature.crud_password.data.repository.datasource

import javax.crypto.spec.SecretKeySpec

interface CryptoSource {

    fun encryptData(data: String, encryptionKey: SecretKeySpec): String

    fun decryptData(data: String, encryptionKey: SecretKeySpec): String

    fun encryptionKey(): SecretKeySpec
}