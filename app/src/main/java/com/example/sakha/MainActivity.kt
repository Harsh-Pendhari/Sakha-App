package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val uid = currentUser.uid
        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                if (!doc.exists()) {
                    // No document yet → force user details form
                    startActivity(Intent(this, UserdetailsformActivity::class.java))
                    finish()
                    return@addOnSuccessListener
                }

                // Prefer an explicit flag: profileCompleted
                val profileCompleted = doc.getBoolean("profileCompleted") ?: false

                // Alternatively check required fields:
                val name = doc.getString("name") ?: ""
                val district = doc.getString("district") ?: ""
                val state = doc.getString("state") ?: ""

                if (!profileCompleted || name.isBlank() || district.isBlank() || state.isBlank()) {
                    // incomplete profile → show details form
                    startActivity(Intent(this, UserdetailsformActivity::class.java))
                } else {
                    // profile OK → go to homepage
                    startActivity(Intent(this, HomepageActivity::class.java))
                }
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error loading user details", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
    }
}
