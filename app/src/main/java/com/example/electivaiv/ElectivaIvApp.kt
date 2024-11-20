package com.example.electivaiv

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.electivaiv.ui.screens.singup.SingUpScreen
import com.example.electivaiv.ui.screens.singup.SingUpViewModel
import com.example.electivaiv.ui.theme.ElectivaIVTheme

@Composable
fun ElectivaIvApp(){
    val viewModel: SingUpViewModel = hiltViewModel()
    ElectivaIVTheme {
        SingUpScreen(
            modifier = Modifier,
            viewModel
        )
    }
}