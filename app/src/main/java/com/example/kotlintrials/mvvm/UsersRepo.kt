package com.example.kotlintrials.mvvm

import android.util.Log
import com.example.kotlintrials.modal.Users
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlintrials.Utils

class UsersRepo {
    private val fireStore = FirebaseFirestore.getInstance()

        fun getUsers(): LiveData<List<Users>> {
            val users = MutableLiveData<List<Users>>()

            fireStore.collection("Users").addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                    Log.d("UsersRepo", "getUsers: ${exception.message}")
                }
                else {
                    Log.d("UsersRepo", "getUsers: ${snapshot?.documents}")
                }

                val usersList = ArrayList<Users>()
                snapshot?.documents?.forEach { document ->
                    val user = document.toObject(Users::class.java)

                    //all the users who not same as the user who has logged in add them to list
                    if(user!!.uid != Utils.getUiLoggedIn()){
                        users.let { usersList.add(user) }
                    }

                    users.value = usersList
                }
            }
            return users
        }
}