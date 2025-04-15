package com.dumanyusuf.chattapp.domain.model

import com.google.firebase.Timestamp

data class Chats(
    val chatId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val createdAt: Timestamp = Timestamp.now()
){
    fun toMap():Map<String,Any>{
        return mapOf(
            "chaId" to chatId,
            "senderId" to senderId,
            "receiverId" to receiverId,
            "createdAt" to createdAt,
        )
    }
}
