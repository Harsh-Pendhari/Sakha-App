package com.example.sakha

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import android.content.Intent

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val forgotPassword = findViewById<Button>(R.id.forgotPassword)
        val errorMsg = findViewById<TextView>(R.id.error_msg)
        val registerBtn = findViewById<Button>(R.id.new_registration)

        forgotPassword.isVisible = false
        errorMsg.isVisible = false

        registerBtn.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}