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
                val userUid = loginUseCase.getSessionActive()
                if (userUid != null) {
                    val likeUser = likeUserServiceSP.getLikeUser()
                    val isLocalDataValid =
                        likeUser.userUId == userUid && likeUser.uidFavComments.isNotEmpty()

                    if (isLocalDataValid) {
                        _uiState.value = _uiState.value.copy(likes = likeUser.uidFavComments)
                    } else {
                        val likes = getLikesByUserUseCase.invoke(userUid)
                        likeUserServiceSP.saveLikeUser(likeUser(userUid, likes))
                        _uiState.value = _uiState.value.copy(likes = likes)
                    }
                    isLikesLoaded.postValue(true)
                } else {
                    Log.d(TEST_MESSAGE, "userUid es null")
                    isLikesLoaded.postValue(false)
                }
            } catch (e: Exception) {
                Log.e(TEST_MESSAGE, "Error al recuperar likes: ${e.message}")
                isLikesLoaded.postValue(false)
            }
        }
    }

    fun syncLikes() {
        viewModelScope.launch {
            try {
                val userUid = loginUseCase.getSessionActive()
                if (userUid != null) {
                    val localLikeUser = likeUserServiceSP.getLikeUser()
                    val firebaseLikes = getLikesByUserUseCase.invoke(userUid)

                    val localLikesSet = localLikeUser.uidFavComments.toSet()
                    val firebaseLikesSet = firebaseLikes.toSet()

                    when {
                        localLikesSet.size > firebaseLikesSet.size -> {
                            // Actualizar Firebase con los datos locales
                            val updatedLikes = localLikesSet.union(firebaseLikesSet).toList()
                            likeUserServiceSP.saveLikeUser(likeUser(userUid, updatedLikes))
                            Log.d(TEST_MESSAGE, "Firebase actualizado con datos locales")
                        }
                        firebaseLikesSet.size > localLikesSet.size -> {
                            // Actualizar datos locales con los datos de Firebase
                            likeUserServiceSP.saveLikeUser(likeUser(userUid, firebaseLikes))
                            Log.d(TEST_MESSAGE, "Datos locales actualizados con datos de Firebase")
                        }
                        else -> {
                            Log.d(TEST_MESSAGE, "Los datos locales y de Firebase están sincronizados")
                        }
                    }

                    _uiState.value = _uiState.value.copy(likes = firebaseLikes)
                    isLikesLoaded.postValue(true)
                } else {
                    Log.d(TEST_MESSAGE, "userUid es null")
                    isLikesLoaded.postValue(false)
                }
            } catch (e: Exception) {
                Log.e(TEST_MESSAGE, "Error al sincronizar likes: ${e.message}")
                isLikesLoaded.postValue(false)
            }
        }
    }
}