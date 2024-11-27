package com.example.electivaiv.common.ext


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.fieldModifier(): Modifier {
    return this
        .fillMaxWidth()
        .padding(16.dp, 4.dp)
}

fun Modifier.basicButton(): Modifier {
    return this
        .fillMaxWidth()
        .padding(20.dp, 4.dp, 20.dp, 0.dp)
}

fun Modifier.textTitleModifier(): Modifier {
    return this
        .height(100.dp)
        .padding(30.dp, 30.dp)
}

fun Modifier.textTitleHeaderModifier(): Modifier {
    return this
        .padding(30.dp, 10.dp)
}

fun Modifier.headerModifier(): Modifier {
    return this
        .fillMaxWidth()
        .height(70.dp)
}

fun Modifier.footerModifier(): Modifier {
    return this.fillMaxWidth()
}

fun Modifier.mainCommentCard(): Modifier {
    return this
        .padding(6.dp, 2.dp, 6.dp, 2.dp)
        .fillMaxWidth()
        .height(120.dp)
}