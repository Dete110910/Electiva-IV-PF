package com.example.electivaiv.ui.screens.main


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.data.local.SharedPreferences
import com.example.electivaiv.data.local.services.LikeUserServiceSP
import com.example.electivaiv.domain.model.likeUser
import com.example.electivaiv.domain.usecase.GetCommentsUseCase
import com.example.electivaiv.domain.usecase.GetLikesByUserUseCase
import com.example.electivaiv.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val sharedPreferences: SharedPreferences,
    private val loginUseCase: LoginUseCase,
    private val likeUserServiceSP: LikeUserServiceSP,
    private val getLikesByUserUseCase: GetLikesByUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val isLikesLoaded = MutableLiveData<Boolean>()

    init {
        listDataBaseComments()
        validateAndLoadLikes()
    }

    fun listDataBaseComments() {
        viewModelScope.launch {
            val comments = getCommentsUseCase.invoke()
            _uiState.value = _uiState.value.copy(commentsList = comments, isLoading = false)
        }
    }

   fun validateAndLoadLikes() {
    viewModelScope.launch {
        try {
            Log.d(TEST_MESSAGE, "Inicio de validateAndLoadLikes")
            val userUid = loginUseCase.getSessionActive()
            Log.d(TEST_MESSAGE, "userUid: $userUid")
            if (userUid != null) {
                val likeUser = likeUserServiceSP.getLikeUser()
                Log.d(TEST_MESSAGE, "likeUser desde SharedPreferences: $likeUser")
                val isLocalDataValid =
                    likeUser.userUId == userUid && likeUser.uidFavComments.isNotEmpty()
                Log.d(TEST_MESSAGE, "isLocalDataValid: $isLocalDataValid")

                if (isLocalDataValid) {
                    _uiState.value = _uiState.value.copy(likes = likeUser.uidFavComments)
                    Log.d(TEST_MESSAGE, "Likes cargados desde SharedPreferences: ${likeUser.uidFavComments}")
                } else {
                    val likes = getLikesByUserUseCase.invoke(userUid)
                    if (likes.isEmpty()) {
                        Log.d(TEST_MESSAGE, "No se encontraron likes en Firestore, creando nueva entrada")
                        val newLikeUser = likeUser(userUid, emptyList())
                        getLikesByUserUseCase.saveLikeUser(newLikeUser)
                        likeUserServiceSP.saveLikeUser(newLikeUser)
                        Log.d(TEST_MESSAGE, "likeUser guardado en Firestore y SharedPreferences: $newLikeUser")
                        _uiState.value = _uiState.value.copy(likes = emptyList())
                    } else {
                        Log.d(TEST_MESSAGE, "Likes obtenidos de Firestore: $likes")
                        val updatedLikeUser = likeUser(userUid, likes)
                        likeUserServiceSP.saveLikeUser(updatedLikeUser)
                        Log.d(TEST_MESSAGE, "likeUser guardado en SharedPreferences: $updatedLikeUser")
                        _uiState.value = _uiState.value.copy(likes = likes)
                    }
                }
                isLikesLoaded.postValue(true)
                Log.d(TEST_MESSAGE, "Likes cargados exitosamente")
            } else {
                Log.d(TEST_MESSAGE, "userUid es null")
                isLikesLoaded.postValue(false)
            }
        } catch (e: Exception) {
            Log.e(TEST_MESSAGE, "Error al recuperar likes: ${e.message}", e)
            isLikesLoaded.postValue(false)
        }
    }
}


}