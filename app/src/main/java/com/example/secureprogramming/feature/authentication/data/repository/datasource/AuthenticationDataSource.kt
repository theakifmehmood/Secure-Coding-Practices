package com.example.secureprogramming.feature.authentication.data.repository.datasource

import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import com.example.secureprogramming.feature.authentication.data.model.UserDataModel
import com.example.secureprogramming.feature.authentication.data.model.UserSignInRequestDataModel
import com.example.secureprogramming.feature.authentication.data.model.UserSignUpRequestDataModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.KeyStore
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val ANDROID_KEYSTORE_PROVIDER = "AndroidKeyStore"
const val TRANSFORMATION = "AES/CBC/PKCS7Padding"
const val PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1"

class AuthenticationDataSource(
) :  AuthenticationSource {

    override suspend fun signIn(data: UserSignInRequestDataModel): Result<UserDataModel> = suspendCoroutine { continuation ->
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userData = UserDataModel(
                        id = auth.currentUser!!.uid,
                        name = auth.currentUser!!.displayName?:"",
                        email = auth.currentUser!!.email!!
                    )
                    continuation.resume(Result.success(userData))
                } else {
                    continuation.resume(Result.failure(task.exception ?: Exception("Unknown exception")))
                }
            }
    }

    override suspend fun signUp(data: UserSignUpRequestDataModel): Result<UserDataModel> = suspendCoroutine { continuation ->
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userData = UserDataModel(
                        id = auth.currentUser!!.uid,
                        name = auth.currentUser!!.displayName?:"",
                        email = auth.currentUser!!.email!!
                    )
                    continuation.resume(Result.success(userData))
                } else {
                    continuation.resume(Result.failure(task.exception ?: Exception("Unknown exception")))
                }
            }
    }

    override suspend fun logout() {
        FirebaseAuth.getInstance().signOut()
        withContext(Dispatchers.IO) {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            keyStore.deleteEntry("MyEncryptionKey")
        }
        // Delete the old key if it exists

    }

    override fun generateEncryptionKey(password: String) {
        val salt = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10)
        // Use PBKDF2 with the user's password and the salt to derive an encryption key
        val iterationCount = 1000 // The number of iterations to use for the PBKDF2 function
        val keyLength = 256 // The length of the key to generate, in bits
        val keySpec = PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength)
        val keyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM)
        val rawKey = keyFactory.generateSecret(keySpec).encoded

        // Convert the raw key to a SecretKey object
        val secretKey = SecretKeySpec(rawKey, "AES")

        // Store the SecretKey object in the Android Keystore
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE_PROVIDER)
        keyStore.load(null)
        keyStore.setEntry(
            "MyEncryptionKey",
            KeyStore.SecretKeyEntry(secretKey),
            KeyProtection.Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(false)
                .build()
        )

    }



}