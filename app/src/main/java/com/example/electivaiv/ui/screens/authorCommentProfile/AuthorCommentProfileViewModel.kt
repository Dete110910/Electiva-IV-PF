package com.example.electivaiv.ui.screens.authorCommentProfile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.domain.usecase.GetCommentsByAuthorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorCommentProfileViewModel @Inject constructor(
    private val getCommentsByAuthorUseCase: GetCommentsByAuthorUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthorCommentProfileUi())
    val uiState: StateFlow<AuthorCommentProfileUi> = _uiState.asStateFlow()

    fun getCommentsByUser(userUid: String) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(otherComments = getCommentsByAuthorUseCase.invoke(userUid))
        }
    }

    fun getAverageRate(comments: List<PostComment>) : Double {
        var sum = 0
        comments.forEach{ comment ->
            sum += comment.rate.toInt()
        }
        return (sum / comments.size).toDouble()
    }


}