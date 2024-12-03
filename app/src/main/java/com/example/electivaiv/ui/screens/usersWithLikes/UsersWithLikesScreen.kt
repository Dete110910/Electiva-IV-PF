package com.example.electivaiv.ui.screens.usersWithLikes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.electivaiv.common.Constants.Companion.AVERAGE_RATING
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.authorCommentProfileCard
import com.example.electivaiv.domain.model.PostComment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.electivaiv.ui.screens.main.RatingStars

@Composable
fun UsersWithLikesScreen(
    onNavigate: (String, String) -> Unit,
    usersWithLikesViewModel: UsersWithLikesViewModel = hiltViewModel(),
) {
    val uiState = usersWithLikesViewModel.uiState.collectAsState()
    var rate by remember { mutableStateOf(0.0) }
    var isFav by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        usersWithLikesViewModel.fetchUsersWithLikes()
    }

    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {
            Footer(
                onNavigate = { route, popUp ->
                    onNavigate(route, popUp)
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Autores favoritos",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(uiState.value.usersWithLikes) { user ->
                        var isFav by remember { mutableStateOf(false) }

                        LaunchedEffect(user.uid) {
                            usersWithLikesViewModel.isAuthorLiked(user.uid) { liked ->
                                isFav = liked
                            }
                            usersWithLikesViewModel.getCommentsByUser(user.uid)
                        }

                        LaunchedEffect(uiState.value.otherComments) {
                            if (uiState.value.otherComments.isNotEmpty()) {
                                rate = usersWithLikesViewModel.getAverageRate(uiState.value.otherComments)
                            }
                        }

                        UserInfoCard(
                            comment = PostComment(
                                authorUid = user.uid,
                                authorName = user.name,
                                authorProfilePhoto = user.profilePhoto ?: "",
                                restaurantName = "",
                                text = "",
                                images = emptyList(),
                                rate = rate
                            ),
                            rate = rate,
                            isFav = isFav,
                            isAuthor = false,
                            onChangeFav = { newFav ->
                                isFav = newFav
                                if (isFav) {
                                    usersWithLikesViewModel.addAuthorToFavorites(user.uid)
                                } else {
                                    usersWithLikesViewModel.removeAuthorFromFavorites(user.uid)
                                    usersWithLikesViewModel.removeUserFromList(user.uid)
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun UserInfoCard(
    comment: PostComment,
    rate: Double,
    isFav: Boolean,
    isAuthor: Boolean,
    onChangeFav: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
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
                modifier = Modifier
                    .constrainAs(favButton) {
                        top.linkTo(userName.top)
                        end.linkTo(parent.end, 20.dp)
                    }
                    .alpha(if (!isAuthor) 1f else 0f),
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