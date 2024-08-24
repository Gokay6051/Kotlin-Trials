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

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        preferences = getSharedPreferences("UserData", MODE_PRIVATE)
        val isLoggedIn = preferences.getBoolean("isLoggedIn", false)

        Toast.makeText(
            applicationContext,
            "isLoggedIn: $isLoggedIn",
            Toast.LENGTH_LONG
        ).show()

        if (!isLoggedIn) {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish the current activity to prevent going back to it
        }

        var userName = preferences.getString("userName", "")
        var password = preferences.getString("password", "")
        binding.textViewUserMain.text = "User Name:  $userName"
        binding.textViewPasswordMain.text = "Password:  $password"

        setContentView(binding.root)

        binding.buttonLogoutMain.setOnClickListener {
            with(preferences.edit()){
                putBoolean("isLoggedIn", false)
                apply()
            }
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}