package com.example.electivaiv.domain.commonViewModel

import androidx.lifecycle.ViewModel
import com.example.electivaiv.domain.model.User
import com.example.electivaiv.domain.usecase.SaveUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val saveUserDataUseCase: SaveUserDataUseCase
) : ViewModel(){
    fun saveUserData(user: User){
        saveUserDataUseCase.invoke(user)
    }
}