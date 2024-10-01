package com.example.kotlintrials.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlintrials.MyApplication
import com.example.kotlintrials.Utils
import com.example.kotlintrials.modal.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope

class ChatAppViewModel {
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val message = MutableLiveData<String>()
    private val firestore = FirebaseFirestore.getInstance()

    val usersRepo = UsersRepo()


    //getting all users
    fun getUsers(): LiveData<List<Users>>{
        return usersRepo.getUsers()
    }
/*
    //getting current user information
    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO)
    {
        val context = MyApplication.instance.applicationContext

        firestore.collection("Users").document(Utils.getUiLoggedIn()).addSnapshotListener{value, error ->
            if(value!!exists() && value != null){
                val users = value.toObject(Users::class.java)
                name.value = users?.userName!!
                imageUrl.value = users?.profileImageUrl!!

            }
        }
    }*/
}