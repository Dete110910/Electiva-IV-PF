package com.example.electivaiv.ui.screens.usersWithLikes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.data.local.services.LikeUserServiceSP
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.domain.model.likeUser
import com.example.electivaiv.domain.usecase.GetCommentsByAuthorUseCase
import com.example.electivaiv.domain.usecase.GetLikesByUserUseCase
import com.example.electivaiv.domain.usecase.GetListUserByLike
import com.example.electivaiv.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersWithLikesViewModel @Inject constructor(
    private val likeUserServiceSP: LikeUserServiceSP,
    private val getListUserByLike: GetListUserByLike,
    private val loginUseCase: LoginUseCase,
    private val getLikesByUserUseCase: GetLikesByUserUseCase,
    private val getCommentsByAuthorUseCase: GetCommentsByAuthorUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersWithLikesUiState())
    val uiState: StateFlow<UsersWithLikesUiState> = _uiState.asStateFlow()

    fun getCommentsByUser(userUid: String) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(otherComments = getCommentsByAuthorUseCase.invoke(userUid))
        }
    }

    fun getAverageRate(comments: List<PostComment>): Double {
        var sum = 0
        comments.forEach { comment ->
            sum += comment.rate.toInt()
        }
        return (sum / comments.size).toDouble()
    }

    fun fetchUsersWithLikes() {
        viewModelScope.launch {
            Log.d("UsersWithLikesViewModel", "ejecuto el metodo si!")
            val likeUsers = likeUserServiceSP.getAllUidsFromList()
            Log.d("UsersWithLikesViewModel", "UIDs from SharedPreferences: $likeUsers")

            val users = getListUserByLike.getUsersByUids(likeUsers)
            Log.d("UsersWithLikesViewModel", "Users from database: $users")

            _uiState.value = _uiState.value.copy(usersWithLikes = users)
        }
    }

    fun isAuthorLiked(authorUid: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val userUid = loginUseCase.getSessionActive()
            Log.d(TEST_MESSAGE, "Input author UID: $authorUid")
            if (userUid != null) {
                val likeUser = likeUserServiceSP.getLikeUser()
                val isLiked = likeUser.uidFavComments.contains(authorUid)
                Log.d(TEST_MESSAGE, "Is author UID in SharedPreferences: $isLiked")
                onResult(isLiked)
            } else {
                Log.d(TEST_MESSAGE, "User UID is null")
                onResult(false)
            }
        }
    }

    fun addAuthorToFavorites(authorUid: String) {
        viewModelScope.launch {
            val userUid = loginUseCase.getSessionActive()
            if (userUid != null) {
                val likeUser = likeUserServiceSP.getLikeUser()
                val updatedLikes = likeUser.uidFavComments.toMutableList().apply {
                    add(authorUid)
                }
                val updatedLikeUser = likeUser(userUid, updatedLikes)
                likeUserServiceSP.saveLikeUser(updatedLikeUser)
                getLikesByUserUseCase.addLike(userUid, authorUid)
                Log.d(TEST_MESSAGE, "authorUid guardado en SharedPreferences: $authorUid")
            }
        }
    }

    fun removeAuthorFromFavorites(authorUid: String) {
        viewModelScope.launch {
            val userUid = loginUseCase.getSessionActive()
            if (userUid != null) {
                val likeUser = likeUserServiceSP.getLikeUser()
                val updatedLikes = likeUser.uidFavComments.toMutableList().apply {
                    remove(authorUid)
                }
                val updatedLikeUser = likeUser(userUid, updatedLikes)
                likeUserServiceSP.saveLikeUser(updatedLikeUser)
                getLikesByUserUseCase.removeLike(userUid, authorUid) // Remover de Firebase
                Log.d(
                    TEST_MESSAGE,
                    "authorUid removed from SharedPreferences and Firebase: $authorUid"
                )
            }
        }
    }

    fun removeUserFromList(userUid: String) {
        _uiState.value = _uiState.value.copy(
            usersWithLikes = _uiState.value.usersWithLikes.filter { it.uid != userUid }
        )
    }
}