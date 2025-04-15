package com.dumanyusuf.chattapp.domain.use_case

import com.dumanyusuf.chattapp.domain.model.Chats
import com.dumanyusuf.chattapp.domain.model.Message
import com.dumanyusuf.chattapp.domain.repo.FirebaseRepo
import com.dumanyusuf.chattapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatUseCase @Inject constructor(private val repo: FirebaseRepo) {


    suspend fun sendMessage(chatId: String, message: Message): Resource<Unit>{
        return repo.sendMessage(chatId,message)
    }

    suspend fun getMessage(chatId:String): Flow<Resource<List<Message>>> {
        return repo.getMessages(chatId)
    }


    suspend fun getOrCreateChat(currentUserId: String, otherUserId: String) =
        repo.getOrCreateChat(currentUserId, otherUserId)



}