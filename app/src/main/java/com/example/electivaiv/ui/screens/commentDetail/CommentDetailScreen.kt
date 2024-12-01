package com.example.electivaiv.ui.screens.commentDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.commentDetailWithImagesCard
import com.example.electivaiv.common.ext.commentDetailWithoutImagesCard
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.ui.screens.addComment.LoadImages
import com.example.electivaiv.ui.screens.main.RatingStars
import com.example.electivaiv.ui.theme.ElectivaIVTheme

@Composable
fun CommentDetailScreen(
    comment: PostComment,
    onNavigate: (String) -> Unit
) {
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
            CommentDetailCard(
                comment = comment
            )
        }
    }
}

@Composable
fun CommentDetailCard(comment: PostComment) {
    Card(
        modifier = if (comment.images.isNotEmpty()) Modifier.commentDetailWithImagesCard() else Modifier.commentDetailWithoutImagesCard(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(0.5.dp, color = Color.Black)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            val (profilePhoto, commentTitle, commentRate, commentDescription, imagesRef) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(
                    model = comment.authorProfilePhoto
                ),
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .constrainAs(profilePhoto) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(parent.start, 20.dp)
                    }
                    .size(60.dp)
                    .clip(shape = CircleShape)
            )

            Text(
                modifier = Modifier
                    .constrainAs(commentTitle) {
                        top.linkTo(profilePhoto.top, 5.dp)
                        start.linkTo(profilePhoto.end, 10.dp)
                    },
                text = "${comment.authorName} dijo sobre ${comment.restaurantName}",
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
            )

            RatingStars(comment.rate, Modifier.constrainAs(commentRate) {
                top.linkTo(commentTitle.bottom, 5.dp)
                start.linkTo(commentTitle.start)
            }, 30.dp)
            Text(
                modifier = Modifier
                    .constrainAs(commentDescription) {
                        top.linkTo(commentRate.bottom, 15.dp)
                        start.linkTo(parent.start)
                    }
                    .padding(10.dp),
                text = comment.text,
                fontSize = 17.sp,
                textAlign = TextAlign.Start
            )

            LazyRow(
                modifier = Modifier.constrainAs(imagesRef) {
                    top.linkTo(commentDescription.bottom, 10.dp)
                    start.linkTo(profilePhoto.start)
                    end.linkTo(parent.end, 20.dp)
                }
            ) {
                items(comment.images) { image ->
                    LoadImages(image.toUri())
                }
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewCommentDetailScreen() {
    val images = listOf("", "")
    val comment = PostComment(
        "",
        "Daniel",
        "https://firebasestorage.googleapis.com/v0/b/electiva-iv-593f3.firebasestorage.app/o/1732771443755.jpg?alt=media&token=95832bc0-3d32-4190-9986-0df8bb121bc0",
        "Ratattouile",
        4.0,
        "No me gustó el restaurante. Tiene ratones y arañas y todo tipo de animales de selva",
        images
    )
    ElectivaIVTheme {
        CommentDetailScreen(
            comment = comment,
            onNavigate = {

            }
        )
    }
}