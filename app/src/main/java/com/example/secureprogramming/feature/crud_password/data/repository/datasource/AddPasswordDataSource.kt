package com.example.secureprogramming.feature.crud_password.data.repository.datasource

import android.util.Log
import com.example.secureprogramming.core.utils.MySharedPreferences
import com.example.secureprogramming.core.utils.helper.encryptionKey
import com.example.secureprogramming.feature.crud_password.data.model.PasswordDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddPasswordDataSource @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val cryptoSource: CryptoSource
) : AddPasswordSource {

    override suspend fun storePassword(data: PasswordDataModel): Result<PasswordDataModel> {
        val passwordCollectionRef = fireStore.collection("passwords")
        return try {
            val documentReference = passwordCollectionRef.document()
            val userId = firebaseAuth.currentUser?.uid ?: ""
            val encryptionKey = encryptionKey()
            val passwordData = data.copy(userId = userId, id=documentReference.id,
                password = cryptoSource.encryptData(data.password, encryptionKey = encryptionKey),
                userName = cryptoSource.encryptData(data.userName, encryptionKey = encryptionKey)
            )
            documentReference.set(passwordData).await()
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun retrievePassword(): Result<List<PasswordDataModel>> {
        val userId = firebaseAuth.currentUser?.uid ?: ""
        val passwordCollectionRef = fireStore.collection("passwords").whereEqualTo("userId", userId)
        return try {
            val querySnapshot = passwordCollectionRef.get().await()
            val passwordList = mutableListOf<PasswordDataModel>()
            for (document in querySnapshot.documents) {
                val passwordData = document.toObject(PasswordDataModel::class.java)
                val encryptionKey = encryptionKey()
                if (passwordData != null) {
                    val passwordDataCopy = passwordData.copy(
                        password = cryptoSource.decryptData(passwordData.password,encryptionKey),
                        userName = cryptoSource.decryptData(passwordData.userName,encryptionKey)


                    )
                    passwordList.add(passwordDataCopy)
                }
            }
            Result.success(passwordList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePassword(data: PasswordDataModel): Result<PasswordDataModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePassword(): Result<Pair<String, Boolean>> {
        TODO("Not yet implemented")
    }

    fun resetPassword(newPassword: String): Result<Pair<String, Boolean>> {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("passwords")
        val batch = db.batch()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        collectionRef.whereEqualTo("userId", currentUserId).get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val docRef = collectionRef.document(document.id)
                val oldPasswordData = cryptoSource.decryptData(document.getString("password") ?: "", encryptionKey())
                MySharedPreferences.setHashKey(newPassword)
                val newPasswordData = cryptoSource.encryptData(oldPasswordData, encryptionKey())
                batch.update(docRef, "password", newPasswordData)
            }
            batch.commit().addOnSuccessListener {
                Log.d("UPDATED", "All documents updated successfully.")
            }
        }.addOnFailureListener { e ->
            Log.e("UPDATED", "Failed to fetch documents.", e)
        }
        return Result.success(Pair("Password reset successful", true))
    }

     }