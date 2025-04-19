package com.dumanyusuf.chattapp.presenatation.person_page

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dumanyusuf.chattapp.R
import com.dumanyusuf.chattapp.Screan
import com.dumanyusuf.chattapp.domain.model.Users

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PersonPage(
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val profileImageUrl by viewModel.profileImageUrl.collectAsState()


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.uploadProfileImage(it)
        }
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Galeri izni gerekli!", Toast.LENGTH_SHORT).show()
        }
    }


    LaunchedEffect(Unit) {
        viewModel.setProfileImageFromFirestore()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween 
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(painter = painterResource(R.drawable.back), contentDescription = "Geri")
                }
                Text(text = stringResource(R.string.profil), modifier = Modifier.padding())
            }

            IconButton(onClick = {
                viewModel.logOut()
                navController.navigate(Screan.SignInPage.route)
            }) {
                Icon(painter = painterResource(R.drawable.logout), contentDescription = "Çıkış")
            }
        }



        // İçerik
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(170.dp).padding(bottom = 20.dp)
            ) {
                if (!profileImageUrl.isNullOrEmpty()) {
                    GlideImage(
                        model = profileImageUrl,
                        contentDescription = "Profil Fotoğrafı",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(170.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)

                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.person),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .align(Alignment.Center)
                    )
                }


                Row (modifier = Modifier.padding(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){

                   Box{
                       IconButton(
                           onClick = {
                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                   permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                               } else {
                                   permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                               }
                           },
                           modifier = Modifier
                               .align(Alignment.BottomEnd)
                               .size(42.dp)
                               .padding(8.dp)
                       ) {
                           Icon(
                               painter = painterResource(R.drawable.gallery),
                               contentDescription = "Fotoğraf Yükle"
                           )
                       }
                   }

                    Spacer(modifier = Modifier.padding(10.dp))
                   Box {
                       IconButton(
                           onClick = {

                           },
                           modifier = Modifier
                               .align(Alignment.BottomEnd)
                               .size(42.dp)
                               .padding(8.dp)
                       ) {
                           Icon(
                               painter = painterResource(R.drawable.camera),
                               contentDescription = "Fotoğraf Yükle"
                           )
                       }
                   }
                   }


            }

            Text(text = "userName", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "userMail",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
