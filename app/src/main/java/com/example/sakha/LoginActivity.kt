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
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setContentView(R.layout.activity_login)

        val forgotPassword = findViewById<Button>(R.id.forgotPassword)
        val errorMsg = findViewById<TextView>(R.id.error_msg)
        val registerBtn = findViewById<Button>(R.id.new_registration)
        val emailInput = findViewById<EditText>(R.id.userid)
        val loginBtn = findViewById<Button>(R.id.login_btn)

        forgotPassword.isVisible = false
        errorMsg.isVisible = false

        registerBtn.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        loginBtn.setOnClickListener{
            startActivity(Intent(this, UserdetailsformActivity::class.java))
            finish()
        }


    }
}