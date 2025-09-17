package com.example.sakha

import android.content.Intent
import android.media.Image
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
        val myCropsBtn: ImageButton = findViewById(R.id.myCrops)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

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

        hamMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Populate nav header
        val headerView = navigationView.getHeaderView(0)
        val userName = headerView.findViewById<TextView>(R.id.userName)
        val userEmail = headerView.findViewById<TextView>(R.id.userEmail)
        val userDistrict = headerView.findViewById<TextView>(R.id.userDistrict)
        val userState = headerView.findViewById<TextView>(R.id.userState)

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

        // MyCrops btn logic
        myCropsBtn.setOnClickListener {
            val uid = auth.currentUser?.uid
            if (uid == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firestore.collection("users").document(uid).collection("crops")
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.isEmpty) {
                        startActivity(Intent(this, AddCropActivity::class.java))
                    } else {
                        startActivity(Intent(this, MycropsActivity::class.java))
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error loading crops", Toast.LENGTH_SHORT).show()
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
}
