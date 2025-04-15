package com.dumanyusuf.chattapp.presenatation.chatt_page

import com.dumanyusuf.chattapp.domain.model.Chats

data class ChatState(
    val chatList:List<Chats> = emptyList(),
    val error:String="",
    val loading:Boolean=false
)
