package com.example.electivaiv.ui.screens.authorCommentProfile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.data.local.services.LikeUserServiceSP
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.domain.usecase.GetCommentsByAuthorUseCase
import com.example.electivaiv.domain.usecase.GetLikesByUserUseCase
import com.example.electivaiv.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorCommentProfileViewModel @Inject constructor(
    private val getCommentsByAuthorUseCase: GetCommentsByAuthorUseCase,
    private val getLikesByUserUseCase: GetLikesByUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val likeUserServiceSP: LikeUserServiceSP,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthorCommentProfileUi())
    val uiState: StateFlow<AuthorCommentProfileUi> = _uiState.asStateFlow()

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
}