package com.example.electivaiv.data.local.services

import com.example.electivaiv.data.local.SharedPreferences
import com.example.electivaiv.domain.model.likeUser
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
import javax.inject.Singleton

@Singleton
class LikeUserService @Constructor constructor(

    private val sharedPreferences: SharedPreferences,
) {
    private val fielUserUid ="user:uid"
    private val fielUserLikeListUid= "user_list_ui"

    fun saveLikeUser(likeUser: likeUser){
        sharedPreferences.editor.putString(fielUserUid, likeUser.uid)
        sharedPreferences.editor.putStringSet(fielUserLikeListUid, likeUser.ListUi.toSet())
        sharedPreferences.editor.apply()
    }

    fun getLikeUser(): likeUser {
        val uid = sharedPreferences.sharedPreferences.getString(fielUserUid, "") ?: ""
        val listUi = sharedPreferences.sharedPreferences.getStringSet(fielUserLikeListUid, emptySet())?.toList() ?: emptyList()
        return likeUser(uid, listUi)
    }
    fun addUidToList(uid: String) {
        val currentList = sharedPreferences.sharedPreferences.getStringSet(fielUserLikeListUid, emptySet())?.toMutableSet() ?: mutableSetOf()
        currentList.add(uid)
        sharedPreferences.editor.putStringSet(fielUserLikeListUid, currentList)
        sharedPreferences.editor.apply()
    }

    fun removeUidFromList(uid: String) {
        val currentList = sharedPreferences.sharedPreferences.getStringSet(fielUserLikeListUid, emptySet())?.toMutableSet() ?: mutableSetOf()
        currentList.remove(uid)
        sharedPreferences.editor.putStringSet(fielUserLikeListUid, currentList)
        sharedPreferences.editor.apply()
    }

}