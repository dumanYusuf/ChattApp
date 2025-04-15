package com.dumanyusuf.chattapp.domain.repo

import com.dumanyusuf.chattapp.domain.model.Message
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepo {


    suspend fun getUsersExcept(curentUserId:String):Resource<List<Users>>

    suspend fun sendMessage(chatId: String, message: Message): Resource<Unit>

    suspend fun getMessages(chatId: String): Flow<Resource<List<Message>>>

    suspend fun getOrCreateChat(currentUserId: String, otherUserId: String): Resource<String>


}