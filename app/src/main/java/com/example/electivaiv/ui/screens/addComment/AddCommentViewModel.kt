package com.example.electivaiv.ui.screens.addComment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.domain.usecase.SaveCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCommentViewModel @Inject constructor(
    private val saveCommentUseCase: SaveCommentUseCase
) : ViewModel() {

    var uiState = mutableStateOf(false)
        private set

    fun saveComment(comment: PostComment){
        viewModelScope.launch {
            uiState.value = saveCommentUseCase.invoke(comment)
        }
    }
}