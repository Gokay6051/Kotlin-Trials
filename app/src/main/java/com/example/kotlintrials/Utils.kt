package com.example.kotlintrials

import com.google.firebase.auth.FirebaseAuth

class Utils {
    companion object {
        private val auth = FirebaseAuth.getInstance()
        private var uid: String = ""


        fun getUiLoggedIn(): String {
            if(auth.currentUser != null){
                return auth.currentUser!!.uid
            }
            return uid
        }
    }
}