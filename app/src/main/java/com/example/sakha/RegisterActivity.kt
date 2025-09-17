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
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setContentView(R.layout.activity_register)

        // Firebase init
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Views
        val nameField = findViewById<EditText>(R.id.userFname)
        val emailField = findViewById<EditText>(R.id.usermail)
        val phoneField = findViewById<EditText>(R.id.usernum)
        val passwordField = findViewById<EditText>(R.id.userPwd)
        val confirmPasswordField = findViewById<EditText>(R.id.confuser_Pwd)
        val registerBtn = findViewById<Button>(R.id.register_btn)
        val loginBtn = findViewById<Button>(R.id.login_btn)
        val errorMsg = findViewById<TextView>(R.id.error_msg)

        errorMsg.isVisible = false

        // Login button
        loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Register button
        registerBtn.setOnClickListener {
            val fullName = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val phone = phoneField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            // Validation
            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showError(errorMsg, "Please fill all fields")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showError(errorMsg, "Passwords do not match")
                return@setOnClickListener
            }

            // Create user in FirebaseAuth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid
                        val user = User(fullName, email, phone)

                        if (uid != null) {
                            db.collection("Users").document(uid).set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                    // Navigate to login or dashboard
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    showError(errorMsg, "Failed to save user data: ${e.message}")
                                }
                        }
                    } else {
                        showError(errorMsg, "Registration failed: ${task.exception?.message}")
                    }
                }
        }
    }

    private fun showError(errorMsg: TextView, message: String) {
        errorMsg.text = message
        errorMsg.isVisible = true
    }
}
