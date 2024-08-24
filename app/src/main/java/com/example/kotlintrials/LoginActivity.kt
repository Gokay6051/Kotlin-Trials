package com.example.kotlintrials

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.kotlintrials.databinding.ActivityLoginBinding

class LoginActivity : ComponentActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        preferences = getSharedPreferences("UserData", MODE_PRIVATE)
        val isLoggedIn = preferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish the current activity to prevent going back to it
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener() {
            val _userName = preferences.getString("userName", "")
            val _password = preferences.getString("password", "")

            val userName = binding.editTextUserName.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (userName == _userName && password == _password) {
                with(preferences.edit()){
                    putBoolean("isLoggedIn", true)
                    apply()
                }
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish() // Finish the current activity to prevent going back to it
            }
            else {
                Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_LONG).show()
            }
            binding.editTextUserName.text.clear()
            binding.editTextPassword.text.clear()
        }

        binding.buttonRegister.setOnClickListener() {
            intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}