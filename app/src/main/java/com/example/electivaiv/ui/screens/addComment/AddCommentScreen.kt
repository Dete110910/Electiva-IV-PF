package com.example.electivaiv.ui.screens.addComment

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.electivaiv.common.Constants
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.textCardModifier
import com.example.electivaiv.domain.model.PostComment

@Composable
fun AddCommentScreen(
    onSaveComment: (PostComment) -> Unit,
    onCloseUi: () -> Unit,
    addCommentViewModel: AddCommentViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { Header() }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val uiState by addCommentViewModel.uiState
            var restaurantName by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }
            var rating : Int by remember { mutableStateOf(0) }

            val (restaurantNameRef, descriptionRef, ratingButtonRef, saveButtonRef, cancelButtonRef) = createRefs()
            AddTextTextField(
                cardModifier = Modifier
                    .textCardModifier()
                    .constrainAs(restaurantNameRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                textFieldModifier = Modifier.fillMaxWidth(),
                label = "Nombre del restaurante",
                value = restaurantName,
                onValueChange = { newValue ->
                    restaurantName = newValue
                }
            )
            AddTextTextField(
                cardModifier = Modifier
                    .textCardModifier()
                    .constrainAs(descriptionRef) {
                        top.linkTo(restaurantNameRef.bottom)
                        start.linkTo(restaurantNameRef.start)
                    }
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = 60.dp),
                textFieldModifier = Modifier.fillMaxWidth(),
                label = "Cuenta tu experiencia",
                value = description,
                maxLines = 20,
                onValueChange = { newValue ->
                    description = newValue
                }
            )

            StarRating(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .constrainAs(ratingButtonRef) {
                        top.linkTo(descriptionRef.bottom)
                        start.linkTo(restaurantNameRef.start)
                        end.linkTo(restaurantNameRef.end)
                        bottom.linkTo(saveButtonRef.top)
                    },
                currentRating = rating,
                onRatingSelected = { newRating ->
                    rating = newRating
                }
            )
            Button(
                onClick = {
                    if (restaurantName.isNotBlank() && description.isNotBlank()) {
                        Log.d("TEST", "rate: $rating")
                        val newComment = PostComment(
                            "",
                            "",
                            restaurantName,
                            rating.toDouble(),
                            description,
                            emptyList()
                        )
                        addCommentViewModel.saveComment(newComment)
                        onCloseUi()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(saveButtonRef) {
                        top.linkTo(ratingButtonRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(start = 40.dp, end = 40.dp, top = 2.dp)
            ) {
                Text(
                    text = "Guardar"
                )
            }

            Button(
                onClick = onCloseUi,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cancelButtonRef) {
                        top.linkTo(saveButtonRef.bottom)
                        start.linkTo(saveButtonRef.start)
                        end.linkTo(saveButtonRef.end)
                    }
                    .padding(start = 40.dp, end = 40.dp, top = 2.dp)

            ) {
                Text(
                    text = "Cancelar"
                )
            }

        }
    }
}

@Composable
fun AddTextTextField(
    cardModifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    label: String = "",
    value: String,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit
) {
    OutlinedCard(
        modifier = cardModifier
    ) {
        TextField(
            label = {
                Text(label)
            },
            modifier = textFieldModifier,
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            maxLines = maxLines
        )
    }
}

@Composable
fun StarRating(
    modifier: Modifier = Modifier,
    currentRating: Int,
    onRatingSelected: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= currentRating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Star $i",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onRatingSelected(i) },
                tint = if (i <= currentRating) Color(0xFFFFD700) else Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddCommentScreen() {
    AddCommentScreen(
        onSaveComment = {},
        onCloseUi = { }
    )
}