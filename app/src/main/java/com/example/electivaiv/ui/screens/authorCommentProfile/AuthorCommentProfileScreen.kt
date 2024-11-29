package com.example.electivaiv.ui.screens.authorCommentProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.electivaiv.common.Constants.Companion.AVERAGE_RATING
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.authorCommentProfileCard
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.ui.screens.main.RatingStars
import com.example.electivaiv.ui.theme.ElectivaIVTheme

@Composable
fun AuthorCommentProfileScreen(
    comment: PostComment,
    authorCommentProfileViewModel: AuthorCommentProfileViewModel = hiltViewModel()
) {
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
        ) {
            UserInfoCard(comment)
        }
    }
}

@Composable
fun UserInfoCard(comment: PostComment) {
    Card(
        modifier = Modifier.authorCommentProfileCard(),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        //border = BorderStroke(0.5.dp, color = Color.Black)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()

        ) {
            val (profilePhoto, userName, favButton, averageRatingText, averageRatingStars) = createRefs()
            val isFav = true
            AsyncImage(
                model = comment.authorProfilePhoto,
                contentDescription = "User Profile Photo",
                modifier = Modifier
                    .constrainAs(profilePhoto) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(parent.start, 20.dp)
                    }
                    .background(Color.Blue)
                    .size(60.dp)
                    .clip(
                        shape = CircleShape
                    )
            )

            Text(
                text = comment.authorName,
                modifier = Modifier.constrainAs(userName) {
                    top.linkTo(profilePhoto.top, 5.dp)
                    start.linkTo(profilePhoto.end, 20.dp)
                },
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Button(
                onClick = {

                },
                modifier = Modifier.constrainAs(favButton){
                    top.linkTo(userName.top)
                    end.linkTo(parent.end, 20.dp)
                },
                colors =  ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Icon(
                    imageVector = if(isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Is favorite",
                    tint = Color.Red
                )
            }

            Text(
                text = AVERAGE_RATING,
                modifier = Modifier.constrainAs(averageRatingText) {
                    top.linkTo(userName.bottom, 8.dp)
                    start.linkTo(userName.start)
                }

            )

            RatingStars(comment.rate.toDouble(), Modifier.constrainAs(averageRatingStars) {
                top.linkTo(averageRatingText.bottom, 5.dp)
                start.linkTo(averageRatingText.start, 5.dp)
            }, 40.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorCommentProfileScreenPreview() {
    val comment = PostComment(
        "",
        "Daniel",
        "https://firebasestorage.googleapis.com/v0/b/electiva-iv-593f3.firebasestorage.app/o/1732771443755.jpg?alt=media&token=95832bc0-3d32-4190-9986-0df8bb121bc0",
        "Ratattouile",
        4.0,
        "No me gustó el restaurante. Tiene ratones",
        emptyList()
    )
    ElectivaIVTheme {
        AuthorCommentProfileScreen(
            comment = comment
        )
    }
}
