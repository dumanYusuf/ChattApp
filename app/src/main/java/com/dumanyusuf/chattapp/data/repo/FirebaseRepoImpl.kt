package com.dumanyusuf.chattapp.data.repo

import android.util.Log
import com.dumanyusuf.chattapp.domain.model.Chats
import com.dumanyusuf.chattapp.domain.model.Message
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.domain.repo.FirebaseRepo
import com.dumanyusuf.chattapp.util.Resource
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
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


    override suspend fun getMessages(chatId: String):Flow<Resource<List<Message>>> = callbackFlow {
        trySend(Resource.Loading())

        val listener = firebase.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.localizedMessage ?: "Mesajlar alınırken bir hata oluştu"))
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull { it.toObject(Message::class.java) }
                trySend(Resource.Success(messages ?: emptyList()))
            }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getOrCreateChat(currentUserId: String, otherUserId: String): Resource<String> {
        return try {
            val snapshot = firebase.collection("chats")
                .whereIn("senderId", listOf(currentUserId, otherUserId))
                .get()
                .await()

            val existingChat = snapshot.documents.firstOrNull { doc ->
                val chat = doc.toObject(Chats::class.java)
                (chat?.senderId == currentUserId && chat.receiverId == otherUserId) ||
                        (chat?.senderId == otherUserId && chat.receiverId == currentUserId)
            }

            if (existingChat != null) {
                Resource.Success(existingChat.id)
            } else {
                val newChatRef = firebase.collection("chats").document()
                val newChat = Chats(
                    chatId = newChatRef.id,
                    senderId = currentUserId,
                    receiverId = otherUserId,
                    createdAt = Timestamp.now()
                )
                newChatRef.set(newChat).await()
                Resource.Success(newChatRef.id)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Chat kontrolü sırasında hata")
        }
    }

}