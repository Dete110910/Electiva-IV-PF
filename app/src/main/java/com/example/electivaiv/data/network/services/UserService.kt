package com.example.electivaiv.data.network.services

import android.util.Log
import com.example.electivaiv.common.Constants
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.common.Constants.Companion.USERS_COLLECTION
import com.example.electivaiv.common.Constants.Companion.USER_SUCCESSFULLY_REGISTERED_MESSAGE
import com.example.electivaiv.common.Constants.Companion.USER_UNSUCCESSFULLY_REGISTERED_MESSAGE
import com.example.electivaiv.data.network.clients.FirebaseClient
import com.example.electivaiv.domain.model.User
import com.example.electivaiv.domain.model.UserSP
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(
    private val firebaseClient: FirebaseClient,
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

   suspend fun getUserByUid(uid: String): UserSP? {
    return try {
        val documents = firebaseClient.firestore.collection(USERS_COLLECTION)
            .whereEqualTo("uid", uid)
            .get().await().documents

        if (documents.isNotEmpty()) {
            val user = documents[0]
            val path = user.reference.path
            val name = user.getString(Constants.NAME)!!
            val profilePhoto = user.getString(Constants.PROFILE_PHOTO)!!
            Log.d(TEST_MESSAGE, "User found: $uid")
            return UserSP(path, uid, name, profilePhoto)
        } else {
            Log.d(TEST_MESSAGE, "No user found with uid: $uid")
            null
        }
    } catch (e: Exception) {
        Log.d(TEST_MESSAGE, "Error fetching user: ${e.message}")
        null
    }
}
}