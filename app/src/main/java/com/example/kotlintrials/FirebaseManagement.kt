package com.example.kotlintrials

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseManagement {
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUser() : FirebaseUser? {
        return auth.currentUser
    }

    fun isLoggedIn() : Boolean {
        return getCurrentUser() != null
    }

    fun signIn(userName: String, password: String): Task<Void> {
        return auth.signInWithEmailAndPassword(userName, password).continueWithTask { task->
            if (task.isSuccessful) {
                Tasks.forResult(null)
            }
            else {
                Tasks.forException(task.exception ?: Exception("Sign in failed"))
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun createUser(userName: String, password: String): Task<Void> {
        return auth.createUserWithEmailAndPassword(userName, password).continueWithTask { task ->
            if(task.isSuccessful){
                Tasks.forResult(null)
            }
            else{
                Tasks.forException(task.exception ?: Exception("User creation failed"))
            }
        }
    }
}