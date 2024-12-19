package com.melashvili.fitnesstracker.services

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.melashvili.fitnesstracker.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var btnRegister: Button

    private lateinit var etRegisterEmail: EditText

    private lateinit var etRegisterPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()

        btnRegister = findViewById<Button>(R.id.btnRegister)
        etRegisterEmail = findViewById<EditText>(R.id.etRegisterEmail)
        etRegisterPassword = findViewById<EditText>(R.id.etRegisterPassword)

        btnRegister.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val email = etRegisterEmail.text.toString().trim()
        val password = etRegisterPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
