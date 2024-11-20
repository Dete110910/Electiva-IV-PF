package com.example.electivaiv.data.network.services

import android.util.Log
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
            return if (account.user != null) { account.user?.uid } else { null }
        } catch (e: Exception) {
            Log.d("TEST--", "Sign Up Error: ${e.message}")
            null
        }
    }
}