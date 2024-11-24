package com.example.electivaiv.data.network.services

import android.util.Log
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.common.Constants.Companion.USERS_COLLECTION
import com.example.electivaiv.common.Constants.Companion.USER_SUCCESSFULLY_REGISTERED_MESSAGE
import com.example.electivaiv.common.Constants.Companion.USER_UNSUCCESSFULLY_REGISTERED_MESSAGE
import com.example.electivaiv.data.network.clients.FirebaseClient
import com.example.electivaiv.domain.model.User
import javax.inject.Inject

class UserService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    fun saveUserData(user: User) {
        val userData = hashMapOf(
            "email" to user.email,
            "lastName" to user.lastName,
            "name" to user.name,
            "profilePhoto" to user.profilePhoto,
            "uid" to user.uid
        )
        firebaseClient.firestore.collection(USERS_COLLECTION).add(userData).addOnSuccessListener {
            Log.d(TEST_MESSAGE, USER_SUCCESSFULLY_REGISTERED_MESSAGE)
        }.addOnFailureListener {
            Log.d(TEST_MESSAGE, USER_UNSUCCESSFULLY_REGISTERED_MESSAGE)

        }
    }
}