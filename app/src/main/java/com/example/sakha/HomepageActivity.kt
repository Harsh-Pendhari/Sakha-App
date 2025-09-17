package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomepageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        val hamMenu: ImageButton = findViewById(R.id.hamMenu)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Handle menu opening
        hamMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // -------- Get Nav Header Views --------
        val headerView = navigationView.getHeaderView(0)
        val profilePic = headerView.findViewById<ImageView>(R.id.profilePic)
        val userName = headerView.findViewById<TextView>(R.id.userName)
        val userEmail = headerView.findViewById<TextView>(R.id.userEmail)
        val userDistrict = headerView.findViewById<TextView>(R.id.userDistrict)
        val userState = headerView.findViewById<TextView>(R.id.userState)

        // -------- Load User Info --------
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userEmail.text = currentUser.email ?: "No Email"

            firestore.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        userName.text = doc.getString("name") ?: "Farmer"
                        userDistrict.text = doc.getString("district") ?: "District"
                        userState.text = doc.getString("state") ?: "State"
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to load user details", Toast.LENGTH_SHORT).show()
                }
        }

        // -------- Handle Menu Items --------
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profit_tracker -> startActivity(Intent(this, ExpenseTrackerActivity::class.java))
                R.id.nav_dashboard -> {
                    startActivity(Intent(this, HomepageActivity::class.java))
                    finish()
                }
                R.id.feedback -> startActivity(Intent(this, FeedbackActivity::class.java))
                R.id.nav_logout -> {
                    auth.signOut()
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}
