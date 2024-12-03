package com.example.electivaiv.data.network.services

import android.net.Uri
import android.util.Log
import com.example.electivaiv.common.Constants.Companion.DATA_REGISTERED_UNSUCCESSFULLY
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
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
        val authorProfilePhoto = document.getString(Constants.AUTHOR_PROFILE_PHOTO)
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
            authorProfilePhoto,
            restaurantName,
            rate.toDouble(),
            text,
            images
        )
    }

    suspend fun saveComment(comment: PostComment): Boolean {
        return try {
            val commentData = hashMapOf(
                "authorName" to comment.authorName,
                "authorUid" to comment.authorUid,
                "authorProfilePhoto" to comment.authorProfilePhoto,
                "images" to comment.images,
                "rate" to comment.rate,
                "restaurantName" to comment.restaurantName,
                "text" to comment.text
            )
            firebaseClient.firestore.collection(Constants.COMMENTS_COLLECTION).add(commentData)
                .await()
            true
        } catch (e: Exception) {
            Log.d(TEST_MESSAGE, "$DATA_REGISTERED_UNSUCCESSFULLY: ${e.message} + ${e.cause}")
            false
        }
    }

    suspend fun saveImages(uris: List<Uri>): List<String> {
        val storage = firebaseClient.store.reference
        return uris.mapNotNull { uri ->
            try {
                val reference = storage.child("${System.currentTimeMillis()}.jpg")
                // Subir el archivo
                reference.putFile(uri).await()
                // Obtener la URL de descarga
                reference.downloadUrl.await().toString()
            } catch (e: Exception) {
                Log.e(TEST_MESSAGE, "Error al registrar la imagen: ${e.message}", e)
                null // Ignorar esta URI si hubo un error
            }
        }
    }

    suspend fun getCommentsByAuthor(uid: String): MutableList<PostComment> {
        var commentsList = mutableListOf<PostComment>()
        val response =
            firebaseClient.firestore.collection(Constants.COMMENTS_COLLECTION)
                .whereEqualTo(Constants.AUTHOR_UID, uid).get().await()
        if (!response.isEmpty) {
            commentsList = createComments(response.documents)
            commentsList = findCommentatorName(commentsList)
        }
        return commentsList
    }
}