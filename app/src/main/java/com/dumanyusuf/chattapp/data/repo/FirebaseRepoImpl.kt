package com.dumanyusuf.chattapp.data.repo

import com.dumanyusuf.chattapp.domain.model.Chats
import com.dumanyusuf.chattapp.domain.model.Message
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.domain.repo.FirebaseRepo
import com.dumanyusuf.chattapp.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepoImpl @Inject constructor(private val firebase:FirebaseFirestore) :FirebaseRepo{


    override suspend fun getUsersExcept(curentUserId: String): Resource<List<Users>> {
        return try {
            val snapshot = firebase.collection("Users").get().await()
            val users = snapshot.documents.mapNotNull { it.toObject(Users::class.java) }
                .filter { it.userId != curentUserId}

            Resource.Success(users)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Bilinmeyen hata")
        }
    }

    override suspend fun createChat(chat: Chats): Resource<String> {
        return try {
            val chatRef = firebase.collection("chats").document()
            val chatWithId = chat.copy(chatId = chatRef.id)

            chatRef.set(chatWithId).await()
            Resource.Success(chatRef.id)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Chat oluşturulurken hata oluştu")
        }
    }

    override suspend fun sendMessage(chatId: String, message: Message): Resource<Unit> {
        return try {
            val messageRef = firebase.collection("chats")
                .document(chatId)
                .collection("messages")
                .document()

            messageRef.set(message.toMap()).await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Mesaj gönderilirken hata oluştu")
        }
    }


    override suspend fun getMessages(chatId: String): Resource<List<Message>> {
        TODO("Not yet implemented")
    }


}