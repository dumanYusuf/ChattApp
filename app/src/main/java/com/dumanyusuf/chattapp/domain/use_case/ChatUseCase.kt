package com.dumanyusuf.chattapp.domain.use_case

import com.dumanyusuf.chattapp.domain.model.Chats
import com.dumanyusuf.chattapp.domain.model.Message
import com.dumanyusuf.chattapp.domain.repo.FirebaseRepo
import com.dumanyusuf.chattapp.util.Resource
import javax.inject.Inject

class ChatUseCase @Inject constructor(private val repo: FirebaseRepo) {


    suspend fun creatAtChat(chats: Chats):Resource<String>{
        return repo.createChat(chats)
    }

    suspend fun sendMessage(chatId: String, message: Message): Resource<Unit>{
        return repo.sendMessage(chatId,message)
    }


}