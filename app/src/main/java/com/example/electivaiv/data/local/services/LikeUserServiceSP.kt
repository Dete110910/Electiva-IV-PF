package com.example.electivaiv.data.local.services

import com.example.electivaiv.data.local.SharedPreferences
import com.example.electivaiv.domain.model.likeUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LikeUserServiceSP @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val fieldUserUid = "user:uid"
    private val fieldUserLikeListUid = "user_list_uid"

    fun saveLikeUser(likeUser: likeUser) {
        sharedPreferences.editor.putString(fieldUserUid, likeUser.userUId)
        sharedPreferences.editor.putStringSet(fieldUserLikeListUid, likeUser.uidFavComments.toSet())
        sharedPreferences.editor.apply()
    }

    fun getLikeUser(): likeUser {
        val uid = sharedPreferences.sharedPreferences.getString(fieldUserUid, "") ?: ""
        val listUi = sharedPreferences.sharedPreferences.getStringSet(fieldUserLikeListUid, emptySet())?.toList() ?: emptyList()
        return likeUser(uid, listUi)
    }

    fun addUidToList(uid: String) {
        val currentList = sharedPreferences.sharedPreferences.getStringSet(fieldUserLikeListUid, emptySet())?.toMutableSet() ?: mutableSetOf()
        currentList.add(uid)
        sharedPreferences.editor.putStringSet(fieldUserLikeListUid, currentList)
        sharedPreferences.editor.apply()
    }

    fun removeUidFromList(uid: String) {
        val currentList = sharedPreferences.sharedPreferences.getStringSet(fieldUserLikeListUid, emptySet())?.toMutableSet() ?: mutableSetOf()
        currentList.remove(uid)
        sharedPreferences.editor.putStringSet(fieldUserLikeListUid, currentList)
        sharedPreferences.editor.apply()
    }

    fun getAllUidsFromList(): List<String> {
        return sharedPreferences.sharedPreferences.getStringSet(fieldUserLikeListUid, emptySet())?.toList() ?: emptyList()
    }
}