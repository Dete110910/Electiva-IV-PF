package com.example.electivaiv.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.electivaiv.common.Constants.Companion.APP_NAME
import com.example.electivaiv.common.ext.headerModifier
import com.example.electivaiv.common.ext.textTitleHeaderModifier

@Composable
fun Header() {
    ConstraintLayout (
        modifier = Modifier.headerModifier().height(80.dp).background(Color.Red)
    ){
        Text(
            modifier = Modifier.textTitleHeaderModifier().height(100.dp),
            text = APP_NAME,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}