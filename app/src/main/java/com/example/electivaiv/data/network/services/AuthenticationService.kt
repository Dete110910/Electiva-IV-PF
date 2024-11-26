package com.example.electivaiv.data.network.services

import android.util.Log
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.common.Constants.Companion.USER_UNSUCCESSFULLY_LOGIN_MESSAGE
import com.example.electivaiv.common.Constants.Companion.USER_UNSUCCESSFULLY_REGISTERED_MESSAGE
import com.example.electivaiv.data.network.clients.FirebaseClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {
    suspend fun signUp(email: String, password: String): String? {
        return try {
            val account =
                firebaseClient.firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            return if (account.user != null) {
                account.user?.uid
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d(TEST_MESSAGE, "${USER_UNSUCCESSFULLY_REGISTERED_MESSAGE}: ${e.message}")
            null
        }
    }

    suspend fun login(email: String, password: String): String? {
        return try {
            val account =
                firebaseClient.firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return if (account.user != null) {
                account.user?.uid
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d(TEST_MESSAGE, "${USER_UNSUCCESSFULLY_LOGIN_MESSAGE}: ${e.message}")
            null
        }

    }
}