package com.example.electivaiv.ui.screens.topRestaurants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.common.ext.mainCommentCard
import com.example.electivaiv.common.ext.mainRestaurantCard
import com.example.electivaiv.domain.model.RankedRestaurant
import com.example.electivaiv.ui.screens.main.RatingStars

@Composable
fun TopRestaurantsScreen(
    onNavigate: (String, String) -> Unit,
    topRestaurantsViewModel: TopRestaurantsViewModel = hiltViewModel()
) {
    val uiState by topRestaurantsViewModel.uiState.collectAsState()
    val location by topRestaurantsViewModel.location.collectAsState()

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
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            item {
                Text(
                    text = location,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(uiState) { restaurant ->
                if (restaurant.name?.length!! < 28)
                    RestaurantCard(restaurant)
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurant: RankedRestaurant) {
    Card(
        modifier = Modifier
            .mainCommentCard(),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(0.1.dp, color = Color.Black)
    ) {
        ConstraintLayout(
            modifier = Modifier.mainRestaurantCard()
        ) {
            val (restaurantPhoto, restaurantName, restaurantRanking, restaurantPrice, restaurantRating, restaurantIsClosed) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(
                    model = restaurant.photo?.thumbnailImageUrl?.url?.url ?: ""
                ),
                contentDescription = "Restaurant Profile Photo",
                modifier = Modifier
                    .constrainAs(restaurantPhoto) {
                        top.linkTo(parent.top, 15.dp)
                        start.linkTo(parent.start, 20.dp)
                        bottom.linkTo(restaurantIsClosed.top, 10.dp)
                    }
                    .size(80.dp)
            )

            Text(
                text = restaurant.name ?: "Unknown Restaurant",
                modifier = Modifier.constrainAs(restaurantName) {
                    top.linkTo(parent.top, 15.dp)
                    end.linkTo(parent.end, 20.dp)
                },
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                //textAlign = TextAlign.Start
            )

            Text(
                text = restaurant.ranking ?: "0",
                modifier = Modifier.constrainAs(restaurantRanking) {
                    top.linkTo(restaurantName.bottom, 5.dp)
                    end.linkTo(restaurantName.end)
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )

            RatingStars(
                rating = restaurant.rating.toDouble(),
                modifier = Modifier.constrainAs(restaurantRating) {
                    top.linkTo(restaurantRanking.bottom, 5.dp)
                    end.linkTo(parent.end, 20.dp)
                },
                size = 20.dp
            )

            Text(
                text = restaurant.price ?: "$0 - $0",
                modifier = Modifier.constrainAs(restaurantPrice) {
                    top.linkTo(restaurantRating.bottom)
                    end.linkTo(restaurantName.end)
                },
                fontSize = 15.sp,
                //fontWeight = FontWeight.Thin
            )
            /*
                        Text(
                            text = "${restaurant.reviewsNum ?: 0} reviews",
                            modifier = Modifier.constrainAs(restaurantReviews) {
                                top.linkTo(restaurantRating.bottom, 10.dp)
                                end.linkTo(restaurantRating.end)
                            }
                        )

             */

            Text(
                text = if (restaurant.isClosed != null && restaurant.isClosed == false) "Open" else "Close",
                modifier = Modifier.constrainAs(restaurantIsClosed) {
                    start.linkTo(parent.start, 20.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                },
                color = if (restaurant.isClosed != null && restaurant.isClosed == false) Color.Green else Color.Red
            )
        }
    }

}