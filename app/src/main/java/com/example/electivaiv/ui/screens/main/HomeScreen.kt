package com.example.electivaiv.ui.screens.main

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.example.electivaiv.R
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.mainCommentCard
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.ui.theme.ElectivaIVTheme

@Composable
fun HomeScreen(
    onAddComment: (String) -> Unit,
    onShowUserCommentProfile: (String, PostComment) -> Unit,
    onShowCommentDetail: (String, PostComment) -> Unit,
    onNavigate: (String) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()

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
        },
        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    onAddComment(ScreensRoutes.AddCommentScreen.route)
                }
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
/*
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp, 100.dp)
                )
            } else {
                LazyColumn {
                    Log.d("TEST", "Lista antes de: ${uiState.commentsList}")
                    items(uiState.commentsList) { comment ->
                        comment?.let {
                            MainCommentCard(it)

                        }
                    }
                }
            }

 */
            LazyColumn {
                items(uiState.commentsList) { comment ->
                    MainCommentCard(
                        comment = comment,
                        onShowUserCommentProfile = onShowUserCommentProfile,
                        onShowCommentDetail = onShowCommentDetail
                    )

                }
            }

            HandleHomeScreenLifecycle { state ->
                when (state) {
                    Lifecycle.State.RESUMED -> {
                        homeViewModel.listDataBaseComments()
                    }

                    else -> Unit
                }
            }
        }
    }
}

@Composable
fun MainCommentCard(
    comment: PostComment,
    onShowUserCommentProfile: (String, PostComment) -> Unit,
    onShowCommentDetail: (String, PostComment) -> Unit,
) {
    Card(
        modifier = Modifier
            .mainCommentCard()
            .clickable {
                onShowCommentDetail(ScreensRoutes.CommentDetail.route, comment)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(0.5.dp, color = Color.Black)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (profileImage, commentTitle, commentRate, commentDescription) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(
                    model = comment.authorProfilePhoto
                ),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(profileImage) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(parent.start, 10.dp)
                    }
                    .clickable {
                        onShowUserCommentProfile(ScreensRoutes.AuthorCommentProfile.route, comment)
                    }
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Text(
                modifier = Modifier
                    .constrainAs(commentTitle) {
                        top.linkTo(profileImage.top, 5.dp)
                        start.linkTo(profileImage.end, 10.dp)
                    }
                    .clickable {
                        onShowUserCommentProfile(ScreensRoutes.AuthorCommentProfile.route, comment)
                    },
                text = "${comment.authorName} dijo sobre ${comment.restaurantName}",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
            )

            RatingStars(comment.rate, Modifier.constrainAs(commentRate) {
                top.linkTo(commentTitle.bottom, 5.dp)
                start.linkTo(commentTitle.start)

            })
            Text(
                modifier = Modifier
                    .constrainAs(commentDescription) {
                        top.linkTo(profileImage.bottom, 10.dp)
                        start.linkTo(profileImage.start)
                    }
                    .padding(10.dp),
                text = comment.text,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RatingStars(rating: Double, modifier: Modifier, size: Dp = 25.dp) {
    val fullStars = rating.toInt()
    val hasHalfStar = rating % 1 >= 0.5
    val totalStars = 5

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = modifier) {
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Full Star",
                tint = Color.Yellow,
                modifier = Modifier.size(size)
            )
        }
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Half Star",
                tint = Color.Gray,
                modifier = Modifier.size(size)
            )
        }
        repeat(totalStars - fullStars - if (hasHalfStar) 1 else 0) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "Empty Star",
                tint = Color.Gray,
                modifier = Modifier.size(size)
            )
        }
    }
}

@Composable
fun HandleHomeScreenLifecycle(
    onLifecycleChanged: (Lifecycle.State) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentLifecycleStateFlow by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    onLifecycleChanged(currentLifecycleStateFlow)

}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    ElectivaIVTheme {
        HomeScreen(
            onAddComment = {},
            onShowUserCommentProfile = { a, b -> },
            onShowCommentDetail = { a, b -> },
            onNavigate = {

            }
        )
    }
}