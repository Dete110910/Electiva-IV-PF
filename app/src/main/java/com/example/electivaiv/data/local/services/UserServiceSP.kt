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

    fun saveCurrentUserUid(userSP: UserSP) {
        sharedPreferences.editor.putString(fieldCurrentUserUid, userSP.uid)
        sharedPreferences.editor.putString(fieldCurrentUserPath, userSP.documentPath)
        sharedPreferences.editor.apply()
    }
    fun getCurrentUserUid(): String? =
        sharedPreferences.sharedPreferences.getString(fieldCurrentUserUid, null)
    fun getCurrentUserPath(): String? =
        sharedPreferences.sharedPreferences.getString(fieldCurrentUserPath, null)

}