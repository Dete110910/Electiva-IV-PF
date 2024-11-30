package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.local.services.UserServiceSP
import javax.inject.Inject

class VerifyIsAuthorUseCase @Inject constructor(
    private val userServiceSP: UserServiceSP
){
    fun invoke (uid: String): Boolean{
        return userServiceSP.verifyIsAuthor(uid)
    }
}