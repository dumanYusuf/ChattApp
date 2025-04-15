package com.dumanyusuf.chattapp.domain.model

import com.google.firebase.Timestamp

data class Message(
    val senderId: String = "",
    val text: String = "",
    val timestamp: Timestamp = Timestamp.now()
){
    fun toMap():Map<String,Any>{
        return mapOf(
            "senderId" to senderId,
            "text" to text,
            "timestamp" to timestamp
        )
    }
}
