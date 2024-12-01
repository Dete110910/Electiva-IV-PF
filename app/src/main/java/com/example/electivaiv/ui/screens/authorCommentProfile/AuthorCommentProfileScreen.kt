package com.example.electivaiv.ui.screens.authorCommentProfile

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.electivaiv.common.Constants.Companion.AVERAGE_RATING
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.authorCommentProfileCard
import com.example.electivaiv.common.ext.minimizedCommentsCard
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.ui.screens.main.RatingStars
import com.example.electivaiv.ui.theme.ElectivaIVTheme

@Composable
fun AuthorCommentProfileScreen(
    comment: PostComment,
    onNavigate: (String) -> Unit,
    authorCommentProfileViewModel: AuthorCommentProfileViewModel = hiltViewModel()
) {
    val uiState by authorCommentProfileViewModel.uiState.collectAsState()
    var rate by remember { mutableStateOf(0.0) }
    var isFav by remember { mutableStateOf(false) }
    val isAuthor = authorCommentProfileViewModel.verifyIsAuthor(comment.authorUid)

    authorCommentProfileViewModel.getCommentsByUser(comment.authorUid)
    LaunchedEffect(uiState.otherComments) {
        if (uiState.otherComments.isNotEmpty()) {
            rate = authorCommentProfileViewModel.getAverageRate(uiState.otherComments)
        }
    }
    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {
            Footer(
                onNavigate = {
                    onNavigate(ScreensRoutes.TopRestaurantsScreen.route)
                }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (userInfoCard, otherComments) = createRefs()
            UserInfoCard(
                comment = comment,
                rate = rate,
                isFav = isFav,
                isAuthor = isAuthor,
                onChangeFav = {
                    isFav = !isFav
                }, modifier = Modifier.constrainAs(userInfoCard) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(otherComments.top)
                })

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier.constrainAs(otherComments) {
                    top.linkTo(userInfoCard.bottom, 10.dp)
                    start.linkTo(userInfoCard.start)
                }
            ) {
                items(uiState.otherComments) { otherComments ->
                    MinimizedCommentsCard(otherComments)
                }
            }
        }
    }
}

@Composable
fun UserInfoCard(
    comment: PostComment,
    rate: Double,
    isFav: Boolean,
    isAuthor: Boolean,
    onChangeFav: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.authorCommentProfileCard(),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            val (profilePhoto, userName, favButton, averageRatingText, averageRatingStars) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(
                    model = comment.authorProfilePhoto
                ),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(profilePhoto) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(parent.start, 20.dp)
                    }
                    .size(60.dp)
                    .clip(shape = CircleShape)
            )

            Text(
                text = comment.authorName,
                modifier = Modifier.constrainAs(userName) {
                    top.linkTo(profilePhoto.top, 5.dp)
                    start.linkTo(profilePhoto.end, 20.dp)
                },
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Button(
                onClick = {
                    onChangeFav(!isFav)
                },
                modifier = Modifier.constrainAs(favButton) {
                    top.linkTo(userName.top)
                    end.linkTo(parent.end, 20.dp)
                }.alpha(if (!isAuthor) 1f else 0f),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                enabled = !isAuthor,

            ) {
                Icon(
                    imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
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

            RatingStars(rate, Modifier.constrainAs(averageRatingStars) {
                top.linkTo(averageRatingText.bottom, 5.dp)
                start.linkTo(averageRatingText.start, 5.dp)
            }, 40.dp)
        }
    }
}

@Composable
fun MinimizedCommentsCard(comment: PostComment) {
    Card(
        modifier = Modifier.minimizedCommentsCard(),
        shape = RoundedCornerShape(1.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(0.1.dp, color = Color.Black)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (restName, restRate, description) = createRefs()
            val fontSize = 18.sp

            Text(
                text = comment.restaurantName,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(restName) {
                    top.linkTo(parent.top, 15.dp)
                    start.linkTo(parent.start, 15.dp)
                }
            )
            Text(
                text = "${comment.rate}",
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(restRate) {
                    top.linkTo(parent.top, 15.dp)
                    start.linkTo(restName.end, 30.dp)
                }
            )
            Text(
                text = comment.text,
                fontSize = fontSize,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(restName.bottom, 15.dp)
                    start.linkTo(restName.start, 15.dp)
                    end.linkTo(parent.end, 5.dp)
                    bottom.linkTo(parent.bottom, 15.dp)
                }
                    .fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
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
            comment = comment,
            onNavigate = {

            }
        )
    }
}
