package com.example.kotlintrials.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.kotlintrials.FirebaseManagement
import com.example.kotlintrials.activities.MainActivity.Companion.db
import com.example.kotlintrials.activities.MainActivity.Companion.firebaseManagement
import com.example.kotlintrials.dataClasses.User
import com.example.kotlintrials.databinding.ActivityRegisterBinding
import com.example.kotlintrials.modal.Users
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private  lateinit var userName: String
    private lateinit var password: String
    private lateinit var alrtDialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        binding.buttonSaveRegister.setOnClickListener {
            userName = binding.editTextUserNameRegister.text.toString()
            password = binding.editTextPasswordRegister.text.toString()

            if (userName.isNotEmpty() && password.isNotEmpty()) {
                firebaseManagement.createUser(userName, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        addUserDb()
                    } else {
                        Toast.makeText(applicationContext, it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_LONG).show()
            }
        }

        binding.checkBoxShowPasswordRegister.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.editTextPasswordRegister.transformationMethod = null
            } else {
                binding.editTextPasswordRegister.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
            }
        }

        binding.buttonLoginPageRegister.setOnClickListener {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addUserDb() {
        val user = firebaseManagement.getCurrentUser()
        val userId = user?.uid ?: "Unknown"

        if (userId != "Unknown") {
            val userData = Users(
                uid = userId,
                email = binding.editTextUserNameRegister.text.toString(),
                userName = binding.editTextUserNameRegister.text.toString(),
                profileImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8YbZwjUWRYSAk3rTAE_IJU4fLzj2Fl_rczQ&s"
            )
            db.collection("Users")
                .add(userData)
                .addOnSuccessListener { documentReference ->
                    alertMessage()

                    Log.d("RegisterActivity", "DocumentSnapshot added with ID: ${documentReference.id}")
                    binding.editTextUserNameRegister.text.clear()
                    binding.editTextPasswordRegister.text.clear()
                    Toast.makeText(applicationContext, "User created successfully", Toast.LENGTH_LONG).show()
                    intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w("RegisterActivity", "Error adding document", e)
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun alertMessage() {
        alrtDialog = android.app.AlertDialog.Builder(this)
            .setTitle("Signing up")
            .setMessage("Please wait...")
            .setCancelable(false)
            .create()
        alrtDialog.show()
    }
}