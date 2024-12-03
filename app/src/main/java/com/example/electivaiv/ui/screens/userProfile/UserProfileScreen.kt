package com.example.electivaiv.ui.screens.userProfile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.electivaiv.common.composable.Footer
import com.example.electivaiv.common.composable.Header
import com.example.electivaiv.ui.theme.ElectivaIVTheme

@Composable
fun UserProfileScreen(
    onNavigate: (String, String) -> Unit,
    userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {

    val context = LocalContext.current

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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (profileImage, nameField, emailField, changeProfilePhotoButton) = createRefs()
            var launchPicker by remember { mutableStateOf(false) }
            val uiState by userProfileViewModel.uiState.collectAsState()

            val pickMultipleVisualMedia = rememberLauncherForActivityResult(
                ActivityResultContracts.PickVisualMedia()
            ) { uris ->
                if (uris != null) {
                    userProfileViewModel.saveProfilePhoto(context,uri = uris)
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

            LaunchedEffect(uiState.profilePhoto) {
                if (uiState.profilePhoto.isNotEmpty()) {
                    userProfileViewModel.setProfilePhoto(uiState.profilePhoto)
                }
            }

            Image(
                painter = rememberAsyncImagePainter(
                    model = uiState.profilePhoto.ifEmpty { userProfileViewModel.getUserProfilePhoto() },

                    ),
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .constrainAs(profileImage) {
                        top.linkTo(parent.top, 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(80.dp)
                    .clip(
                        shape = CircleShape
                    )
            )

            Text(
                text = userProfileViewModel.getUserLocalName(),
                modifier = Modifier
                    .constrainAs(nameField) {
                        top.linkTo(profileImage.bottom, 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .height(35.dp)
                    .border(BorderStroke(0.2.dp, color = Color.Gray)),
                color = Color.Black.copy(alpha = 0.5f)
            )

            Text(
                text = uiState.email.ifEmpty { "Loading email..." },
                modifier = Modifier
                    .constrainAs(emailField) {
                        top.linkTo(nameField.bottom, 5.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .height(35.dp)
                    .border(BorderStroke(0.2.dp, color = Color.Gray)),
                color = Color.Black.copy(alpha = 0.5f)
            )

            OutlinedButton(
                onClick = {
                    launchPicker = true
                },
                modifier = Modifier
                    .constrainAs(changeProfilePhotoButton) {
                        top.linkTo(emailField.bottom, 5.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(start = 30.dp, end = 30.dp)
                    .fillMaxWidth(),
                shape = RectangleShape,
            ) {
                Text(
                    text = "Change Profile Picture"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfileScreen() {
    ElectivaIVTheme {
        /*
        UserProfileScreen(
            onNavigate = { a,b ->

            }
        )

         */
    }
}