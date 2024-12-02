package com.example.electivaiv.ui.screens.usersWithLikes

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header

@Composable
fun UsersWithLikesScreen(
    onNavigate: (String, String) -> Unit
) {
    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {
            Footer(
                onNavigate = { route, popUp ->
                    onNavigate(route, popUp)
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
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
    )
}