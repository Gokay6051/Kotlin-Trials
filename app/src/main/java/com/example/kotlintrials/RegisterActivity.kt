package com.example.kotlintrials

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlintrials.databinding.ActivityRegisterBinding

class RegisterActivity : ComponentActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSaveRegister.setOnClickListener {
            var userName = binding.editTextUserNameRegister.text.toString()
            var password = binding.editTextPasswordRegister.text.toString()
            var sharedPreferences = this.getSharedPreferences("UserData", MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            //add Data
            editor.putString("userName", "$userName").apply()
            editor.putString("password", "$password").apply()
            Toast.makeText(applicationContext, "Data Saved, please log in", Toast.LENGTH_LONG).show()
            binding.editTextUserNameRegister.text.clear()
            binding.editTextPasswordRegister.text.clear()
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }


        binding.buttonLoginPageRegister.setOnClickListener(){
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}