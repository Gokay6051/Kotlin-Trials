package com.example.kotlintrials

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.kotlintrials.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    private val firebaseManagement = FirebaseManagement()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(firebaseManagement.isLoggedIn() == false){
            Toast.makeText(applicationContext, "${firebaseManagement.isLoggedIn().toString()}", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val user = firebaseManagement.getCurrentUser()
        val userName = user?.email ?: "Unknown"
        val password = user?.uid ?: "Unknown"
        binding.textViewUserMain.text = "User Name:  $userName"
        binding.textViewPasswordMain.text = "Password:  $password"

        binding.buttonLogoutMain.setOnClickListener {
            firebaseManagement.signOut() // Firebase'den çıkış yap
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish() // Mevcut aktiviteyi kapat
            Toast.makeText(applicationContext, "Logged out", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}