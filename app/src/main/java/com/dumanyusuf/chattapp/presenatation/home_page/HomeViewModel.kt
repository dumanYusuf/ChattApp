package com.dumanyusuf.chattapp.presenatation.home_page

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.domain.use_case.HomePageUseCase
import com.dumanyusuf.chattapp.domain.use_case.LogOutUseCase
import com.dumanyusuf.chattapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val homePageUseCase: HomePageUseCase
):ViewModel() {


    private val _stateUsers = mutableStateOf(HomePageState())
    val stateUsers: State<HomePageState> get() = _stateUsers


    fun getUserExcept(currentUserId: String) {
        println("veriler yüklendi1")
        if (_stateUsers.value.userList.isNotEmpty()) return
        viewModelScope.launch {
            _stateUsers.value = HomePageState(isLoading = true)
            when (val result = homePageUseCase.getUsersExcept(currentUserId)) {
                is Resource.Success -> {
                    Log.e("users","veriler yuklendi")
                    _stateUsers.value = HomePageState(
                        userList = result.data ?: emptyList(),
                        isLoading = false,
                    )
                }
                is Resource.Error -> {
                    _stateUsers.value = HomePageState(
                        error = result.message ?: "Bilinmeyen bir hata oluştu",
                        isLoading = false
                    )
                }
                is Resource.Loading->{
                    Log.e("loading","loading")
                }
            }
        }
    }

    fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }




    fun logOut(){
        logOutUseCase.LogOut()
        Log.e("basarılı","cıkıs işlemi basarılı")
    }


}