package com.example.kotlintrials

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date

class Utils {
    companion object {
        private val auth = FirebaseAuth.getInstance()
        private var uid: String = ""


        fun getUidLoggedIn(): String {
            if(auth.currentUser != null){
                return auth.currentUser!!.uid
            }
            return uid
        }

        fun getTime (): String {
            val formatter = SimpleDateFormat("HH:mm:ss")
            val date: Date = Date(System.currentTimeMillis())
            val stringDate = formatter.format(date)

            return stringDate
        }
    }
}