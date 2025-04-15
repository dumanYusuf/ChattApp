package com.dumanyusuf.chattapp.presenatation.chatt_page

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.chattapp.domain.model.Chats
import com.dumanyusuf.chattapp.domain.model.Message
import com.dumanyusuf.chattapp.domain.use_case.ChatUseCase
import com.dumanyusuf.chattapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(private val chatUseCase: ChatUseCase):ViewModel() {


    fun createChat(chat: Chats, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val result = chatUseCase.creatAtChat(chat)
            when (result) {
                is Resource.Success -> {
                    onResult(result.data)
                }
                is Resource.Error -> {
                    onResult(null)
                }
                else -> Unit
            }
        }
    }


    fun sendMesasage(chatId: String, message: Message){
        viewModelScope.launch {
            chatUseCase.sendMessage(chatId, message)
            Log.e("sendMessage","success sendMessage")
        }
    }

    fun getCurentUserId():String{
        return FirebaseAuth.getInstance().currentUser?.uid?:""
    }


}