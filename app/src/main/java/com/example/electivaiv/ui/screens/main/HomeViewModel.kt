package com.example.electivaiv.ui.screens.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.domain.usecase.GetCommentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        listDataBaseComments()
    }

    fun listDataBaseComments() {
        viewModelScope.launch {
            val comments = getCommentsUseCase.invoke()
            _uiState.value = _uiState.value.copy(commentsList = comments, isLoading = false)
        }
    }
}