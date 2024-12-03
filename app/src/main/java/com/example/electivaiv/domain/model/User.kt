package com.example.electivaiv.domain.model

data class User(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val profilePhoto: String = "https://firebasestorage.googleapis.com/v0/b/electiva-iv-593f3.firebasestorage.app/o/img_profile_photo.png?alt=media&token=5ca66a03-b215-4aca-8889-d65499a030db",
    var uid: String = ""
) {
}