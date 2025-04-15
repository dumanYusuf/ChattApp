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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(private val chatUseCase: ChatUseCase):ViewModel() {


    private val _chatList=MutableStateFlow<ChatState>(ChatState())
    val chatList:StateFlow<ChatState> = _chatList.asStateFlow()


    fun getMessage(chatId: String) {
        viewModelScope.launch {
            chatUseCase.getMessage(chatId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _chatList.value = ChatState(chatList = result.data ?: emptyList())
                        Log.e("mesajlar", "başarılı bir şekilde geldi mesajlar: ${result.data}")
                    }
                    is Resource.Error -> {
                        _chatList.value = ChatState(error = result.message ?: "Bilinmeyen bir hata oluştu")
                        Log.e("mesajlar", "hata: ${result.message}")
                    }
                    is Resource.Loading -> {
                        _chatList.value = ChatState(loading = true)
                        Log.e("loading", "yükleniyor")
                    }
                }
            }
        }
    }



    fun getOrCreateChat(currentUserId: String, otherUserId: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            when (val result = chatUseCase.getOrCreateChat(currentUserId, otherUserId)) {
                is Resource.Success -> onResult(result.data)
                is Resource.Error -> onResult(null)
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



    fun formatMessageTime(timestamp: Timestamp): String {
        val messageDate = timestamp.toDate()

        val calendar = Calendar.getInstance()
        val now = Calendar.getInstance()
        now.time = Date()

        calendar.time = messageDate

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val fullDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        return when {
            isSameDay(now, calendar) -> {
                dateFormat.format(messageDate)
            }
            isYesterday(now, calendar) -> {
                "Dün"
            }
            else -> {
                fullDateFormat.format(messageDate)
            }
        }
    }

    fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    fun isYesterday(today: Calendar, date: Calendar): Boolean {
        val yesterday = Calendar.getInstance()
        yesterday.time = today.time
        yesterday.add(Calendar.DAY_OF_YEAR, -1)

        return isSameDay(yesterday, date)
    }


}