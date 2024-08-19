package com.example.kotlintrials

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlintrials.databinding.ActivityWelcomeBinding

class WelcomeActivity : ComponentActivity() {
    lateinit var binding: ActivityWelcomeBinding
    lateinit var preferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        preferences = getSharedPreferences("UserData", MODE_PRIVATE)
        var userName = preferences.getString("userName", "")
        var password = preferences.getString("password", "")
        binding.textViewUserWelcome.text = "User Name:  $userName"
        binding.textViewPasswordWelcome.text = "Password:  $password"

        setContentView(binding.root)

        binding.buttonLogoutWelcome.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}