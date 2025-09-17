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
            // 🚀 No user logged in → go to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            val uid = currentUser.uid

            // ✅ Check if user details exist
            firestore.collection("users").document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        // User details exist → now check crops
                        firestore.collection("users").document(uid)
                            .collection("crops")
                            .get()
                            .addOnSuccessListener { snapshot ->
                                if (snapshot.isEmpty) {
                                    // 🚜 No crops → go to AddCropDetailsActivity
                                    startActivity(Intent(this, AddCropDetailsActivity::class.java))
                                } else {
                                    // 🌾 Crops exist → go to Homepage (Dashboard)
                                    startActivity(Intent(this, HomepageActivity::class.java))
                                }
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error checking crops", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, HomepageActivity::class.java))
                                finish()
                            }
                    } else {
                        // 📝 User details not filled → go to UserDetailsActivity
                        startActivity(Intent(this, UserdetailsformActivity::class.java))
                        finish()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error loading user details", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
        }
    }
}
