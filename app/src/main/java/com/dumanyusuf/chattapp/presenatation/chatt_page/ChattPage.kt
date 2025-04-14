package com.dumanyusuf.chattapp.presenatation.chatt_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dumanyusuf.chattapp.R
import com.dumanyusuf.chattapp.domain.model.Users

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChattPage(
    navController: NavController,
    users: Users
) {
    var message by remember { mutableStateOf("") }

    LaunchedEffect(users.userProfilPage) {
        println("👀 Profil fotoğraf URL: ${users.userProfilPage}")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Geri"
                )
            }
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = users.userName,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium
                )
                /*GlideImage(
                    model = users.userProfilPage,
                    contentDescription = "Profil Fotoğrafı",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )*/
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it
                                println(message)
                },
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(20.dp)
                    ),
                placeholder = {
                    Text(
                        text = "Mesajınızı yazın...",
                        color = Color.Gray,
                        style = TextStyle(fontSize = 14.sp)
                    )
                },
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (message.isNotBlank()) {
                        message = ""
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.send),
                    contentDescription = "Gönder"
                )
            }
        }
    }
}
