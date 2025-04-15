package com.dumanyusuf.chattapp.presenatation.chatt_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    val chatListState=viewModel.chatList.collectAsState()

    val listState= rememberLazyListState()

    LaunchedEffect(users.userId) {
        val currentUserId = viewModel.getCurentUserId()
        viewModel.getOrCreateChat(currentUserId, users.userId) { createdChatId ->
            chatId = createdChatId
            if (chatId != null) {
                viewModel.getMessage(chatId!!)
            }
        }
    }

    LaunchedEffect (chatListState.value.chatList.size){
        listState.animateScrollToItem(chatListState.value.chatList.size)
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


       if (chatListState.value.loading){
           Box(
               modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
               CircularProgressIndicator(
                   color = Color.Red
               )
           }
       }
        else{
           LazyColumn(
               state = listState,
               modifier = Modifier
                   .weight(1f)
                   .padding(horizontal = 8.dp)
           ) {
               items(chatListState.value.chatList) { msg ->
                   val isCurrentUser = msg.senderId == viewModel.getCurentUserId()
                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(vertical = 4.dp),
                       horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
                   ) {
                       Box(
                           modifier = Modifier
                               .background(
                                   if (isCurrentUser) Color(0xFFD0F0C0) else Color(0xFFF0F0F0),
                                   shape = RoundedCornerShape(12.dp)
                               )
                               .padding(12.dp),
                           contentAlignment = Alignment.BottomEnd
                       ) {
                           Column {
                               Text(text = msg.text, fontSize = 16.sp)
                               Text(
                                   modifier = Modifier.align(Alignment.End),
                                   fontSize = 12.sp,
                                   color = Color.Gray,
                                   text = viewModel.formatMessageTime(msg.timestamp))
                           }
                       }
                   }
               }
           }
       }

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
