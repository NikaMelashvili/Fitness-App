package com.melashvili.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var btnLogin: Button

    private lateinit var tvRegister: TextView

    private lateinit var etLoginEmail: EditText

    private lateinit var etLoginPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        btnLogin = findViewById<Button>(R.id.btnLogin)
        tvRegister = findViewById<TextView>(R.id.tvRegister)
        etLoginEmail = findViewById<EditText>(R.id.etLoginEmail)
        etLoginPassword = findViewById<EditText>(R.id.etLoginPassword)

        btnLogin.setOnClickListener {
            loginUser()
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = etLoginEmail.text.toString().trim()
        val password = etLoginPassword.text.toString().trim()

        if (email.isEmpty()) {
            etLoginEmail.error = "Email is required"
            return
        }

        if (password.isEmpty()) {
            etLoginPassword.error = "Password is required"
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
