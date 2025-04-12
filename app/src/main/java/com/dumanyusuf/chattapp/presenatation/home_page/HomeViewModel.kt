package com.dumanyusuf.chattapp.presenatation.home_page

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dumanyusuf.chattapp.domain.use_case.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase
):ViewModel() {




    fun logOut(){
        logOutUseCase.LogOut()
        Log.e("basarılı","cıkıs işlemi basarılı")
    }


}