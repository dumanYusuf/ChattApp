package com.dumanyusuf.chattapp.presenatation.home_page

import com.dumanyusuf.chattapp.domain.model.Users

data class HomePageState(
    val userList:List<Users> = emptyList(),
    val isLoading:Boolean=false,
    val error:String=""

)
