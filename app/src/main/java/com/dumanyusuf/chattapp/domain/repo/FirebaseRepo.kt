package com.dumanyusuf.chattapp.domain.repo

import com.dumanyusuf.chattapp.domain.model.Chats
import com.dumanyusuf.chattapp.domain.model.Message
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.util.Resource

interface FirebaseRepo {


    suspend fun getUsersExcept(curentUserId:String):Resource<List<Users>>

    suspend fun createChat(chat: Chats): Resource<String>

    suspend fun sendMessage(chatId: String, message: Message): Resource<Unit>

    suspend fun getMessages(chatId: String): Resource<List<Message>>

}