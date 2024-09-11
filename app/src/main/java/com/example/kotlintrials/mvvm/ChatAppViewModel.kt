package com.example.kotlintrials.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlintrials.modal.Users

class ChatAppViewModel {
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val message = MutableLiveData<String>()

    val usersRepo = UsersRepo()


    //getting all users
    fun getUsers(): LiveData<List<Users>>{
        return usersRepo.getUsers()
    }
}