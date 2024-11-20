package com.example.electivaiv.common.composable

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val sizeFontTitle = 30.sp
val weightFont = FontWeight.Bold

@Composable
fun TextTitle(
    value: String,
    modifier: Modifier = Modifier
){
    Text(
        text = value,
        modifier = modifier,
        fontSize = sizeFontTitle,
        fontWeight = weightFont
    )
}