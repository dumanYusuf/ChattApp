package com.dumanyusuf.chattapp.presenatation.home_page

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.domain.use_case.LogOutUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
):ViewModel() {







    fun logOut(){
        logOutUseCase.LogOut()
        Log.e("basarılı","cıkıs işlemi basarılı")
    }


}