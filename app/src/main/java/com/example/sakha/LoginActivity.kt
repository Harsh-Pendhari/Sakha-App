package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setContentView(R.layout.activity_login)

        // Firebase init
        auth = FirebaseAuth.getInstance()

        // Views
        val forgotPassword = findViewById<Button>(R.id.forgotPassword)
        val errorMsg = findViewById<TextView>(R.id.error_msg)
        val registerBtn = findViewById<Button>(R.id.new_registration)
        val emailInput = findViewById<EditText>(R.id.userid)
        val passwordInput = findViewById<EditText>(R.id.user_pwd)
        val loginBtn = findViewById<Button>(R.id.login_btn)

        errorMsg.isVisible = false
        forgotPassword.isVisible = false // we’ll enable this later if you want reset password

        // Navigate to Register
        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        // Handle Login
        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showError(errorMsg, "Please enter both email and password")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                        // 👉 Navigate to home/dashboard activity after login
                        startActivity(Intent(this, UserdetailsformActivity::class.java))
                        finish()
                    } else {
                        showError(errorMsg, "Login failed: ${task.exception?.message}")
                        forgotPassword.isVisible = true // show reset option if login fails
                    }
                }
        }

        // Handle Forgot Password
        forgotPassword.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isEmpty()) {
                showError(errorMsg, "Enter your email to reset password")
            } else {
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Password reset link sent to $email", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        showError(errorMsg, "Error: ${e.message}")
                    }
            }
        }
    }

    private fun showError(errorMsg: TextView, message: String) {
        errorMsg.text = message
        errorMsg.isVisible = true
    }
}
