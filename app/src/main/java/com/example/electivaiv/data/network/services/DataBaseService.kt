package com.example.electivaiv.data.network.services

import android.util.Log
import com.example.electivaiv.data.network.clients.FirebaseClient
import com.example.electivaiv.domain.model.PostComment
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.example.electivaiv.common.Constants.Companion as Constants

class DataBaseService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {
    suspend fun getComments(): List<PostComment> {
        var commentsList = mutableListOf<PostComment>()
        val response =
            firebaseClient.firestore.collection(Constants.COMMENTS_COLLECTION).get().await()
        if (!response.isEmpty) {
            commentsList = createComments(response.documents)
            commentsList = findCommentatorName(commentsList)
        }

        return commentsList
    }

    private fun createComments(response: List<DocumentSnapshot>): MutableList<PostComment> {
        val productList = mutableListOf<PostComment>()
        response.forEach { document ->
            productList.add(getComment(document))
        }
        return productList
    }

    private suspend fun findCommentatorName(list: List<PostComment>): MutableList<PostComment> {
        val updatedList = mutableListOf<PostComment>()
        list.forEach { postComment ->
            val userQuery = firebaseClient.firestore.collection(Constants.USERS_COLLECTION)
                .whereEqualTo(Constants.UID, postComment.authorUid).get().await()
            val commentator = if (!userQuery.isEmpty) {
                userQuery.documents.first().getString(Constants.NAME)
                    ?: Constants.UNKNOWN_AUTHOR
            } else {
                Constants.UNKNOWN_AUTHOR
            }

            updatedList.add(
                postComment.copy(authorName = commentator)
            )
        }
        return updatedList
    }

    private fun getComment(document: DocumentSnapshot): PostComment {
        val authorUid = document.getString(Constants.AUTHOR_UID)
            ?: Constants.EMPTY_STRING
        val authorName = document.getString(Constants.AUTHOR_NAME)
            ?: Constants.EMPTY_STRING
        val images = document.get(Constants.ARRAY_IMAGES) as List<String>
        val rate = document.getDouble(Constants.RATE)
            ?: Constants.ZERO
        val restaurantName = document.getString(Constants.RESTAURANT_NAME)
            ?: Constants.EMPTY_STRING
        val text = document.getString(Constants.TEXT)
            ?: Constants.EMPTY_STRING
        return PostComment(
            authorUid,
            authorName,
            restaurantName,
            rate,
            text,
            images
        )
    }
}