package com.example.electivaiv.data.network.clients

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor() {
    val firebaseAuth: FirebaseAuth = Firebase.auth
    val firestore = Firebase.firestore
    val store = Firebase.storage("")
}