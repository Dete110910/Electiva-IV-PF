package com.example.electivaiv.data.network.services

import android.net.Uri
import android.util.Log
import com.example.electivaiv.common.Constants
import com.example.electivaiv.common.Constants.Companion.EMAIL
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.common.Constants.Companion.UID
import com.example.electivaiv.common.Constants.Companion.USERS_COLLECTION
import com.example.electivaiv.common.Constants.Companion.USER_SUCCESSFULLY_REGISTERED_MESSAGE
import com.example.electivaiv.common.Constants.Companion.USER_UNSUCCESSFULLY_REGISTERED_MESSAGE
import com.example.electivaiv.data.network.clients.FirebaseClient
import com.example.electivaiv.domain.model.User
import com.example.electivaiv.domain.model.UserSP
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getEmailFromCloud(value: String): String = suspendCoroutine { continuation ->
        try {
            firebaseClient.firestore.collection(USERS_COLLECTION)
                .whereEqualTo(UID, value)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val email = querySnapshot.documents.firstOrNull()?.getString(EMAIL)
                            ?: Constants.UNKNOWN_FIELD
                        Log.d("TEST", "Correo es: $email")
                        continuation.resume(email)
                    } else {
                        Log.d("TEST", "No se encontraron documentos")
                        continuation.resume(Constants.UNKNOWN_FIELD)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("TEST", "Error obteniendo el correo", exception)
                    continuation.resumeWithException(exception)
                }
        } catch (e: Exception) {
            Log.e("TEST", "Error inesperado", e)
            continuation.resumeWithException(e)
        }
    }

    suspend fun saveProfilePhoto(uri: Uri): String {
        val storage = firebaseClient.store.reference
        val reference = storage.child("${System.currentTimeMillis()}.jpg")
        return suspendCoroutine { continuation ->
            reference.putFile(uri)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    reference.downloadUrl
                }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        Log.d(TEST_MESSAGE, "Register Successful image: ${downloadUri.toString()}")
                        continuation.resume(downloadUri.toString())
                    } else {
                        Log.e(TEST_MESSAGE, "Failed to upload image", task.exception)
                        continuation.resumeWithException(
                            task.exception ?: Exception("Unknown error")
                        )
                    }
                }
        }
    }

    fun setProfilePhoto(uid: String, url: String) {
        if (uid != "") {
            firebaseClient.firestore.collection(USERS_COLLECTION).whereEqualTo("uid", uid).get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        for (document in querySnapshot.documents) {
                            document.reference.update("profilePhoto", url).addOnSuccessListener {
                                Log.d("TEST", "Foto de perfil actualizada correctamente")
                            }.addOnSuccessListener {
                                Log.d("TEST", "Error al actualizar la foto de perfil")
                            }
                        }
                    } else {
                        Log.d("TEST", "No se encontró usuarios con este uid")
                    }
                }.addOnFailureListener { e ->
                    Log.d("TEST", "Error al recuperar el usuario", e)
                }
        } else {
            Log.d("TEST", "Hubo un error en su registro. Inicie sesión nuevamente.")
        }
    }


    suspend fun getUsersByUids(uids: List<String>): List<User> {
        val users = mutableListOf<User>()
        for (uid in uids) {
            val user = getUserByUidLike(uid)
            if (user != null) {
                users.add(user)
            }
        }
        return users
    }

    suspend fun getUserByUidLike(uid: String): User? {
        return try {
            val documents = firebaseClient.firestore.collection(USERS_COLLECTION)
                .whereEqualTo("uid", uid)
                .get().await().documents

            if (documents.isNotEmpty()) {
                val user = documents[0]
                val name = user.getString("name")!!
                val lastName = user.getString("lastName")!!
                val email = user.getString("email")!!
                val profilePhoto = user.getString("profilePhoto")!!
                Log.d(TEST_MESSAGE, "User found: $uid")
                return User(name, lastName, email, "", profilePhoto, uid)
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