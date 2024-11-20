package com.example.electivaiv.common.messages

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlin.coroutines.coroutineContext


@Composable
fun ShowToastComposable(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(LocalContext.current, text, duration).show()
}