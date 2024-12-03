package com.example.electivaiv.ui.screens.addComment

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.textCardModifier
import com.example.electivaiv.domain.model.PostComment

@Composable
fun AddCommentScreen(
    onCloseUi: () -> Unit,
    addCommentViewModel: AddCommentViewModel = hiltViewModel(),
) {
    var launchPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Header() },
        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    launchPicker = true
                }
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            var restaurantName by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }
            var rating by remember { mutableIntStateOf(0) }
            val images by addCommentViewModel.images
            var urisList: List<Uri> by remember { mutableStateOf(mutableListOf()) }

            val pickMultipleVisualMedia = rememberLauncherForActivityResult(
                ActivityResultContracts.PickMultipleVisualMedia(100)
            ) { uris ->
                if (uris.isNotEmpty()) {
                    urisList = uris
                    addCommentViewModel.saveImages(uris = uris)
                    Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

            LaunchedEffect(launchPicker) {
                if (launchPicker) {
                    launchPicker = false
                    pickMultipleVisualMedia.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            }
            val (restaurantNameRef, descriptionRef, ratingButtonRef, imagesRef, saveButtonRef, cancelButtonRef) = createRefs()

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
                        bottom.linkTo(imagesRef.top)
                    },
                currentRating = rating,
                onRatingSelected = { newRating ->
                    rating = newRating
                }
            )

            LazyRow (
                modifier = Modifier.constrainAs(imagesRef){
                    top.linkTo(ratingButtonRef.bottom)
                    start.linkTo(ratingButtonRef.start)
                    end.linkTo(ratingButtonRef.end)
                    bottom.linkTo(saveButtonRef.top)

                }
            ) {
                items(urisList){ uri ->
                    LoadImages(uri)
                }
            }

            Button(
                onClick = {
                    if (restaurantName.isNotBlank() && description.isNotBlank()) {
                        val newComment = PostComment(
                            "",
                            "",
                            addCommentViewModel.getProfilePhoto() ?: "https://firebasestorage.googleapis.com/v0/b/electiva-iv-593f3.firebasestorage.app/o/img_profile_photo.png?alt=media&token=5ca66a03-b215-4aca-8889-d65499a030db",
                            restaurantName,
                            rating.toDouble(),
                            description,
                            images
                        )
                        addCommentViewModel.saveComment(newComment)
                        onCloseUi()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(saveButtonRef) {
                        top.linkTo(imagesRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(start = 40.dp, end = 40.dp, top = 2.dp),
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

@Composable
fun LoadImages(
    uri: Uri,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    padding: Dp = 8.dp
) {
    Image(
        painter = rememberAsyncImagePainter(model = uri),
        contentDescription = "Selected Image",
        modifier = modifier.size(size).padding(padding)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAddCommentScreen() {
    AddCommentScreen(
        onCloseUi = { }
    )
}