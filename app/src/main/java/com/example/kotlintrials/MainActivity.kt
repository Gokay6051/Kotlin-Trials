package com.example.kotlintrials

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlintrials.databinding.ActivityMainBinding
import com.example.kotlintrials.ui.theme.KotlinTrialsTheme

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("UserData", MODE_PRIVATE)
        binding.buttonLogin.setOnClickListener() {
            var _userName = preferences.getString("userName", "")
            var _password = preferences.getString("password", "")

            var userName = binding.editTextUserName.text.toString()
            var password = binding.editTextPassword.text.toString()

            if (userName == _userName && password == _password) {
                intent = Intent(applicationContext, WelcomeActivity::class.java)
                startActivity(intent)
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