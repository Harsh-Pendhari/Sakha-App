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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

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

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val nameField = findViewById<EditText>(R.id.userFname)
        val emailField = findViewById<EditText>(R.id.usermail)
        val phoneField = findViewById<EditText>(R.id.usernum)
        val passwordField = findViewById<EditText>(R.id.userPwd)
        val confirmPasswordField = findViewById<EditText>(R.id.confuser_Pwd)
        val registerBtn = findViewById<Button>(R.id.register_btn)
        val loginBtn = findViewById<Button>(R.id.login_btn)
        val errorMsg = findViewById<TextView>(R.id.error_msg)

        errorMsg.isVisible = false

        loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        registerBtn.setOnClickListener {
            val fullName = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val phone = phoneField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showError(errorMsg, "Please fill all fields")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showError(errorMsg, "Passwords do not match")
                return@setOnClickListener
            }

            // Check whether the email already has an auth account
            auth.fetchSignInMethodsForEmail(email)
                .addOnSuccessListener { result ->
                    val methods = result.signInMethods ?: listOf()
                    if (methods.isNotEmpty()) {
                        // Email already in use in FirebaseAuth
                        showError(errorMsg, "Email already in use. Please login or reset password.")
                        // optionally show a quick reset:
                        // auth.sendPasswordResetEmail(email).addOnSuccessListener{...}
                    } else {
                        // safe to create new auth account
                        createAuthAndUser(fullName, email, phone, password, errorMsg)
                    }
                }
                .addOnFailureListener { e ->
                    showError(errorMsg, "Error checking email: ${e.message}")
                }
        }
    }

    private fun createAuthAndUser(fullName: String, email: String, phone: String, password: String, errorMsg: TextView) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid == null) {
                        showError(errorMsg, "Registration error: UID is null")
                        return@addOnCompleteListener
                    }
                    // user model includes a profileCompleted flag (false until they fill details)
                    val user = hashMapOf(
                        "name" to fullName,
                        "email" to email,
                        "phone" to phone,
                        "profileCompleted" to false // important
                    )
                    db.collection("users").document(uid).set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registration successful. Please complete your profile.", Toast.LENGTH_SHORT).show()
                            // go to details form
                            startActivity(Intent(this, UserdetailsformActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            // If write fails (permissions etc), consider deleting created auth user to avoid orphaned auth accounts
                            showError(errorMsg, "Failed to save user data: ${e.message}")
                            // Optionally cleanup: delete the created auth user
                            val createdUser = auth.currentUser
                            createdUser?.delete()
                        }
                } else {
                    val ex = task.exception
                    // provide helpful message
                    if (ex is FirebaseAuthUserCollisionException) {
                        showError(errorMsg, "Email already in use. Please login or reset password.")
                    } else if (ex is FirebaseAuthInvalidCredentialsException) {
                        showError(errorMsg, "Invalid email or password.")
                    } else {
                        showError(errorMsg, "Registration failed: ${ex?.message}")
                    }
                }
            }
    }

    private fun showError(errorMsg: TextView, message: String) {
        errorMsg.text = message
        errorMsg.isVisible = true
    }
}
