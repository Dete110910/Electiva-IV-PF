package com.example.electivaiv.data.network.services

import android.util.Log
import com.example.electivaiv.common.Constants
import com.example.electivaiv.data.network.clients.FirebaseClient
import com.example.electivaiv.domain.model.likeUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LikeUserService @Inject constructor(
    private val firebaseClient: FirebaseClient,
) {

    private fun createLikes(response: List<DocumentSnapshot>): MutableList<String> {
        val uidList = mutableListOf<String>()
        response.forEach { document ->
            val listUi =
                (document.get("uidFavComments") as? List<*>)?.filterIsInstance<String>()
                    ?: emptyList()
            uidList.addAll(listUi)
        }
        return uidList
    }

    suspend fun getUidsByUserId(userId: String): List<String> {
        var uidList = mutableListOf<String>()
        val response = firebaseClient.firestore.collection(Constants.USER_lIKES_COLLECTION)
            .whereEqualTo(Constants.USER_UID, userId).get().await()
        if (!response.isEmpty) {
            uidList = createLikes(response.documents)
        }
        Log.d("TEST", uidList.toString())
        return uidList
    }

    suspend fun addLike(userId: String, likeUid: String) {
        val document =
            firebaseClient.firestore.collection(Constants.USER_lIKES_COLLECTION).document(userId)
        document.update("uidFavComments", FieldValue.arrayUnion(likeUid)).await()
    }

    suspend fun removeLike(userId: String, likeUid: String) {
        val document =
            firebaseClient.firestore.collection(Constants.USER_lIKES_COLLECTION).document(userId)
        document.update("uidFavComments", FieldValue.arrayRemove(likeUid)).await()
    }
}
