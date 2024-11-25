package com.example.electivaiv.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.electivaiv.R
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.mainCommentCard
import com.example.electivaiv.ui.theme.ElectivaIVTheme

val comments = listOf("", "")

@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {
            Footer()
        }
    ) { innerPadding ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Green)
        ) {
            val (floatingButton) = createRefs()
            FloatingActionButton(
                onClick = {

                },
                modifier = Modifier.constrainAs(floatingButton) {
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                }
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")

            }

            LazyColumn {
                items(comments) { comment ->
                    MainCommentCard(comment)
                }
            }

        }
    }
}

@Composable
fun MainCommentCard(comment: String) {
    Card(
        modifier = Modifier.mainCommentCard(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (profileImage, commentTitle, commentRate, commentDescription) = createRefs()
            Image(
                painter = painterResource(R.drawable.img_default_profile_photo),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(profileImage) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(parent.start, 10.dp)
                    }
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Text(
                modifier = Modifier.constrainAs(commentTitle) {
                    top.linkTo(profileImage.top, 5.dp)
                    start.linkTo(profileImage.end, 10.dp)
                },
                text = "Pepito dijo sobre Gusteau",
                fontSize = 15.sp,
            )

            RatingStars(4.5, Modifier.constrainAs(commentRate) {
                top.linkTo(commentTitle.bottom, 5.dp)
                start.linkTo(commentTitle.start)

            })
            Text(
                modifier = Modifier
                    .constrainAs(commentDescription) {
                        top.linkTo(profileImage.bottom)
                        start.linkTo(profileImage.start)
                    }
                    .padding(10.dp),
                text = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen",
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RatingStars(rating: Double, modifier: Modifier) {
    val fullStars = rating.toInt()
    val hasHalfStar = rating % 1 >= 0.5
    val totalStars = 5

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = modifier) {
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Full Star",
                tint = Color.Yellow
            )
        }
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Half Star",
                tint = Color.Gray
            )
        }
        repeat(totalStars - fullStars - if (hasHalfStar) 1 else 0) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "Empty Star",
                tint = Color.Yellow
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    ElectivaIVTheme {
        MainScreen()
    }
}