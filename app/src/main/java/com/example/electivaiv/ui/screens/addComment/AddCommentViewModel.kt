package com.example.electivaiv.ui.screens.addComment

import android.net.Uri
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
    private val saveCommentUseCase: SaveCommentUseCase,
) : ViewModel() {

    var images = mutableStateOf(mutableListOf<String>())
        private set

    fun saveComment(comment: PostComment) {
        viewModelScope.launch { saveCommentUseCase.invoke(comment) }
    }

    fun saveImages(uris: List<Uri>) {
        viewModelScope.launch { images.value = saveCommentUseCase.saveImages(uris) }
    }

}