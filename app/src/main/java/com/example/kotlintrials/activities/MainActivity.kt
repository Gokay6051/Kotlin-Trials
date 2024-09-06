package com.example.kotlintrials.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.kotlintrials.FirebaseManagement
import com.example.kotlintrials.dataClasses.User
import com.example.kotlintrials.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    companion object {
        val firebaseManagement = FirebaseManagement()
        lateinit var db: FirebaseFirestore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        if (!firebaseManagement.isLoggedIn()) {
            Toast.makeText(applicationContext, "${firebaseManagement.isLoggedIn()}", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        displayCurrentUser(db)


        binding.buttonLogoutMain.setOnClickListener {
            firebaseManagement.signOut()
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext, "Logged out", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayCurrentUser(db: FirebaseFirestore) {
        val firebase = firebaseManagement.getCurrentUser()
        val uid = firebase?.uid ?: return

        Log.d("MainActivity", "UID: $uid")

        Companion.db.collection("users")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if(!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val user = document.toObject(User::class.java)
                    if(user != null) {
                        binding.textViewUserMain.text = "User Name: ${user.userName}"
                        binding.textViewPasswordMain.text = "Email: ${user.email}"
                    }
                    else {
                        Log.d("MainActivity", "User object is null")
                        Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Log.d("MainActivity", "Query snapshot is empty")
                    Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener() {
                Log.d("MainActivity", "Error: ${it.localizedMessage}")
                Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
            }

    }
}