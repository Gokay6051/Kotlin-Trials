package com.example.kotlintrials

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
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

            if(userName.isNotEmpty() && password.isNotEmpty()){
                MainActivity.auth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.editTextUserNameRegister.text.clear()
                        binding.editTextPasswordRegister.text.clear()
                        Toast.makeText(applicationContext, "User created successfully", Toast.LENGTH_LONG).show()
                        intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener() {
                    Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_LONG).show()
            }

        }
        binding.buttonLoginPageRegister.setOnClickListener(){
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}