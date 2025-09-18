package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
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
import com.google.firebase.firestore.ListenerRegistration

class HomepageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var userDocListener: ListenerRegistration? = null

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
        val myCropsBtn: ImageButton = findViewById(R.id.myCrops)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        hamMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // BTN WORK
        val irrigationMethodsBTN = findViewById<ImageButton>(R.id.irrigationMethods_btn)
        irrigationMethodsBTN.setOnClickListener {
            startActivity(Intent(this, IrrigationMethodsActivity::class.java))
        }

        val marketBTN = findViewById<ImageButton>(R.id.market_btn)
        marketBTN.setOnClickListener {
            startActivity(Intent(this, MarketActivity::class.java))
        }

        val pestBTN = findViewById<ImageButton>(R.id.pest_btn)
        pestBTN.setOnClickListener {
            startActivity(Intent(this, PesticidesActivity::class.java))
        }

        val weatherBTN = findViewById<ImageButton>(R.id.weatherBTN)
        weatherBTN.setOnClickListener {
            startActivity(Intent(this, WeatherActivity::class.java))
        }

        val mktPriceBTN = findViewById<ImageButton>(R.id.mktPrice_btn)
        mktPriceBTN.setOnClickListener {
            startActivity(Intent(this, MarketPriceActivity::class.java))
        }

        val tipsBTN = findViewById<ImageButton>(R.id.tips_btn)
        tipsBTN.setOnClickListener {
            startActivity(Intent(this, FarmingtipsActivity::class.java))
        }

        val governmentSchemesBTN = findViewById<ImageButton>(R.id.government_schemesBTN)
        governmentSchemesBTN.setOnClickListener {
            startActivity(Intent(this, GovSchemesActivity::class.java))
        }

        // Setup header textViews
        val headerView = navigationView.getHeaderView(0)
        val userName = headerView.findViewById<TextView>(R.id.userName)
        val userEmail = headerView.findViewById<TextView>(R.id.userEmail)
        val userDistrict = headerView.findViewById<TextView>(R.id.userDistrict)
        val userState = headerView.findViewById<TextView>(R.id.userState)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            userEmail.text = currentUser.email ?: "No Email"

            // Use a real-time listener so header updates immediately when user fills the form
            userDocListener = firestore.collection("users").document(currentUser.uid)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        // ignore or show Toast
                        return@addSnapshotListener
                    }
                    if (snapshot == null || !snapshot.exists()) {
                        // Not available yet -> show placeholders
                        userName.text = "Farmer"
                        userDistrict.text = "District"
                        userState.text = "State"
                        return@addSnapshotListener
                    }

                    userName.text = snapshot.getString("name") ?: "Farmer"
                    userDistrict.text = snapshot.getString("district") ?: "District"
                    userState.text = snapshot.getString("state") ?: "State"
                }
        }

        myCropsBtn.setOnClickListener {
            val uid = auth.currentUser?.uid
            if (uid == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check Firestore for crops before deciding
            firestore.collection("users").document(uid).collection("crops")
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.isEmpty) {
                        // No crops → go to AddCropActivity
                        startActivity(Intent(this, AddCropActivity::class.java))
                    } else {
                        // Crops exist → go to MycropsActivity
                        startActivity(Intent(this, MycropsActivity::class.java))
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error checking crops: ${e.message}", Toast.LENGTH_SHORT).show()
                    // fallback: let user add crops if something goes wrong
                    startActivity(Intent(this, AddCropActivity::class.java))
                }
        }


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
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userDocListener?.remove()
    }
}
