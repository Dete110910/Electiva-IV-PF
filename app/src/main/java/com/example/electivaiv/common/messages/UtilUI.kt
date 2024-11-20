package com.example.electivaiv.common.messages

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun ShowToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(LocalContext.current, text, duration).show()
}