package com.example.electivaiv.ui.screens.usersWithLikes

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UsersWithLikesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Estos son los usuarios a los que les gusta",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Aquí puedes agregar más contenido, como una lista de usuarios
    }
}