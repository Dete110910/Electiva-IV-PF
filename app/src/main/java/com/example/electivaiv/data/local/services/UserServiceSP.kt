package com.example.electivaiv.data.local.services


import com.example.electivaiv.domain.model.UserSP
import com.example.electivaiv.data.local.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserServiceSP @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {
    private val fieldCurrentUserUid = "user_uid"
    private val fieldCurrentUserPath = "user_path"
    private val fieldCurrentUserName = "user_name"
    private val fieldCurrentUserProfilePhoto = "user_profilePhoto"

    fun saveCurrentUserUid(userSP: UserSP) {
        sharedPreferences.editor.putString(fieldCurrentUserUid, userSP.uid)
        sharedPreferences.editor.putString(fieldCurrentUserPath, userSP.documentPath)
        sharedPreferences.editor.putString(fieldCurrentUserName, userSP.name)
        sharedPreferences.editor.putString(fieldCurrentUserProfilePhoto, userSP.profilePhoto)
        sharedPreferences.editor.apply()
    }
    fun getCurrentUserUid(): String? =
        sharedPreferences.sharedPreferences.getString(fieldCurrentUserUid, null)
    fun getCurrentUserPath(): String? =
        sharedPreferences.sharedPreferences.getString(fieldCurrentUserPath, null)
    fun getCurrentUserName(): String? =
        sharedPreferences.sharedPreferences.getString(fieldCurrentUserName, null)
    fun getCurrentUserProfilePhoto(): String? =
        sharedPreferences.sharedPreferences.getString(fieldCurrentUserProfilePhoto, null)

}