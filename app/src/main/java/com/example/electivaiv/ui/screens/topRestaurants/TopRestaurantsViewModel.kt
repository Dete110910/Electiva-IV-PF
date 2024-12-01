package com.example.electivaiv.ui.screens.topRestaurants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.domain.model.RankedRestaurant
import com.example.electivaiv.domain.usecase.GetTopRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRestaurantsViewModel @Inject constructor(
    private val getTopRestaurantsUseCase: GetTopRestaurantsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(emptyList<RankedRestaurant>())
    val uiState: StateFlow<List<RankedRestaurant>> = _uiState.asStateFlow()

    val location: StateFlow<String> = uiState.map { list ->
        list.firstOrNull()?.location ?: "Loading..."
    }.stateIn(viewModelScope, SharingStarted.Lazily, "Loading...")

    init {
        getTopRestaurants()
    }

    fun getTopRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            val restaurants = getTopRestaurantsUseCase.invoke()
            _uiState.value = restaurants
        }
    }
}