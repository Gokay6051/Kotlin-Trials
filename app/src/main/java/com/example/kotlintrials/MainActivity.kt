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
    lateinit var preferences : SharedPreferences
    companion object {
        lateinit var auth : FirebaseAuth
        val user = FirebaseAuth.getInstance().currentUser
        val isLoggedIn = user != null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        Toast.makeText(
            applicationContext,
            "isLoggedIn: $isLoggedIn",
            Toast.LENGTH_LONG
        ).show()
        /*
        if (!isLoggedIn) {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish the current activity to prevent going back to it
        }*/

        val userName = user?.email ?: "Unknown"
        val password = user?.uid ?: "Unknown"
        binding.textViewUserMain.text = "User Name:  $userName"
        binding.textViewPasswordMain.text = "Password:  $password"

        setContentView(binding.root)

        binding.buttonLogoutMain.setOnClickListener {
            auth.signOut() // Firebase'den çıkış yap
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