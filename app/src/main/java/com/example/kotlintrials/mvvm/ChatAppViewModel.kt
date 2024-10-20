package com.example.kotlintrials.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlintrials.MyApplication
import com.example.kotlintrials.Utils
import com.example.kotlintrials.modal.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.kotlintrials.SharedPrefs
import kotlinx.coroutines.launch

class ChatAppViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val message = MutableLiveData<String>()
    private val firestore = FirebaseFirestore.getInstance()

    val usersRepo = UsersRepo()


    init {
        getCurrentUser()
    }

    //getting all users
    fun getUsers(): LiveData<List<Users>>{
        return usersRepo.getUsers()
    }

    //getting current user information
    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO)
    {
        val context = MyApplication.instance.applicationContext

        firestore.collection("Users")
            .whereEqualTo("uid", Utils.getUidLoggedIn())
            .get()
            .addOnSuccessListener { querySnapShot ->
                if(querySnapShot != null){
                    for(document in querySnapShot){
                        val users = document.toObject(Users::class.java)
                        name.value = users.userName!!
                        imageUrl.value = users.profileImageUrl!!

                        val mySharedPrefs = SharedPrefs(context)
                        mySharedPrefs.setValue("userName", users.userName)
                    }
                }
                else{
                    Log.e("ChatAppViewModel", "QuerySnapShot is null")
                }
            }
            .addOnFailureListener(){
                Log.e("ChatAppViewModel", "Error: $it")
            }
/*
        firestore.collection("Users").document("djirNtyLbHhAY71vy4I0")
            .addSnapshotListener{value, error ->
                if(error != null){
                    Log.e("ChatAppViewModel", "Error: $error")
                    return@addSnapshotListener
                }
                if(value != null && value!!.exists()){
                    val users = value.toObject(Users::class.java)
                    if(users != null){
                        name.value = users?.userName!!
                        imageUrl.value = users?.profileImageUrl!!

                        val mySharedPrefs = SharedPrefs(context)
                        mySharedPrefs.setValue("userName", users.userName)
                    }
                    else{
                        Log.e("ChatAppViewModel", "User is null")
                    }
                }
                else{
                    Log.e("ChatAppViewModel", "Value is null: $value")
                }
        }*/
    }

    //Send message
    fun sendMessage(sender: String, receiver: String, friendName: String, friendImage: String) = viewModelScope.launch(Dispatchers.IO) {
        val context = MyApplication.instance.applicationContext

        val hashMap = hashMapOf<String, Any>(
            "sender" to sender,
            "receiver" to receiver,
            "message" to message.value.toString(),
            "time" to System.currentTimeMillis()
        )

        val uniqueId = listOf(sender, receiver).sorted()
        uniqueId.joinToString("")

        val friendsNameSplit = friendName.split("\\s".toRegex())[0]
        val mySharedPrefs = SharedPrefs(context)
        mySharedPrefs.setValue("friendId", receiver)
        mySharedPrefs.setValue("chatRoomId", uniqueId.toString())
        mySharedPrefs.setValue("friendName", friendsNameSplit)
        mySharedPrefs.setValue("friendImage", friendImage)

        //sending message
        firestore.collection("Messages").document(uniqueId.toString()).collection("Chats")
            .document(Utils.getTime())
            .set(hashMap)
            .addOnCompleteListener() { task ->
                //all work for recent chats list
                val hashMapForRecent = hashMapOf<String, Any>(
                    "friendId" to receiver,
                    "time" to Utils.getTime(),
                    "sender" to Utils.getUidLoggedIn(),
                    "message" to message.value.toString(),
                    "friendsImage" to friendImage,
                    "name" to friendName,
                    "person" to "you"

                )

                //updates the last messages with the spoken user
                firestore.collection("Conversation${Utils.getUidLoggedIn()}").document(receiver).set(hashMapForRecent)

                firestore.collection("Conversation${receiver}").document(Utils.getUidLoggedIn()).update("message", message.value.toString(),  "time", Utils.getTime(), "person", name.value.toString())
            }
    }
}