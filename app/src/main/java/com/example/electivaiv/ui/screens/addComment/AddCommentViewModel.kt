package com.example.electivaiv.ui.screens.addComment

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.common.notification.ToastUtil
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.domain.usecase.GetUserProfilePhotoUseCase
import com.example.electivaiv.domain.usecase.SaveCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCommentViewModel @Inject constructor(
    private val saveCommentUseCase: SaveCommentUseCase,
    private val getUserProfilePhotoUseCase: GetUserProfilePhotoUseCase
) : ViewModel() {

    var images = mutableStateOf(mutableListOf<String>())
        private set

    fun saveComment(context: Context, comment: PostComment) {
        viewModelScope.launch {
            val result = saveCommentUseCase.invoke(comment)
            if (result) {
                ToastUtil.showToast(context, "Comentario guardado exitosamente")
            } else {
                ToastUtil.showToast(context, "Error al guardar el comentario")
            }
        }
    }

    fun saveImages(context: Context, uris: List<Uri>) {
        viewModelScope.launch {
            val result = saveCommentUseCase.saveImages(uris)
            if (result.isNotEmpty()) {
                images.value = result.toMutableList()
                ToastUtil.showToast(context, "Imágenes cargadas exitosamente")
            } else {
                ToastUtil.showToast(context, "Error al cargar las imágenes")
            }
        }
    }

    fun getProfilePhoto(): String {
        return getUserProfilePhotoUseCase.invoke()
    }
}