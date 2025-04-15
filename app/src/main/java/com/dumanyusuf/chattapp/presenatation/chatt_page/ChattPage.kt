package com.dumanyusuf.chattapp.presenatation.chatt_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.chattapp.R
import com.dumanyusuf.chattapp.domain.model.Chats
import com.dumanyusuf.chattapp.domain.model.Message
import com.dumanyusuf.chattapp.domain.model.Users
import com.google.firebase.Timestamp

@Composable
fun ChattPage(
    navController: NavController,
    users: Users,
    viewModel: ChatViewModel= hiltViewModel()
) {
    var message by remember { mutableStateOf("") }
    var chatId by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(users.userProfilPage) {
        println("👀 Profil fotoğraf URL: ${users.userProfilPage}")
    }


    LaunchedEffect(users.userId) {
        val newChat = Chats(
            senderId =viewModel.getCurentUserId(),
            receiverId = users.userId,
            createdAt = Timestamp.now()
        )

        viewModel.createChat(newChat) { createdChatId ->
            chatId = createdChatId
        }
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
                onValueChange = {
                    message = it

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
                        val msg = Message(
                            senderId = viewModel.getCurentUserId(),
                            text = message,
                            timestamp = Timestamp.now()
                        )
                        viewModel.sendMesasage(chatId!!, msg)
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
