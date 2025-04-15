package com.dumanyusuf.chattapp.presenatation.chatt_page

import com.dumanyusuf.chattapp.domain.model.Chats
import com.dumanyusuf.chattapp.domain.model.Message

data class ChatState(
    val chatList:List<Message> = emptyList(),
    val error:String="",
    val loading:Boolean=false
)
