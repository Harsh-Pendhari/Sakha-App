package com.example.sakha

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val homeTxt = findViewById<TextView>(R.id.welcomeText)

        homeTxt.setText("Dashboard")


        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        val hamMenu: ImageButton = findViewById(R.id.hamMenu)
        hamMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val marketBTN = findViewById<ImageButton>(R.id.market_btn)

        marketBTN.setOnClickListener{
            startActivity(Intent(this, MarketActivity::class.java))
        }

        val myCropsBTN = findViewById<ImageButton>(R.id.myCrops)
        myCropsBTN.setOnClickListener {
            startActivity(Intent(this, MycropsActivity::class.java))
        }

        val govBTN = findViewById<ImageButton>(R.id.government_schemesBTN)
        govBTN.setOnClickListener {
            startActivity(Intent(this, GovSchemesActivity::class.java))
        }

        val ideasTipsBTN = findViewById<ImageButton>(R.id.tips_btn)
        ideasTipsBTN.setOnClickListener {
            startActivity(Intent(this, FarmingtipsActivity::class.java))
        }

        val weatherBTN = findViewById<ImageButton>(R.id.weatherBTN)
        weatherBTN.setOnClickListener{
            startActivity(Intent(this, WeatherActivity::class.java))
        }

        val irrigationMethodsBTN = findViewById<ImageButton>(R.id.irrigationMethods_btn)
        irrigationMethodsBTN.setOnClickListener{
            startActivity(Intent(this, IrrigationMethodsActivity::class.java))
        }

        val pestBTN = findViewById<ImageButton>(R.id.pest_btn)
        pestBTN.setOnClickListener{
            startActivity(Intent(this, PesticidesActivity::class.java))
        }

        val marketPriceBTN = findViewById<ImageButton>(R.id.mktPrice_btn)
        marketPriceBTN.setOnClickListener {
            startActivity(Intent(this, MarketPriceActivity::class.java))
        }

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profit_tracker -> {
                    startActivity(Intent(this, ExpenseTrackerActivity::class.java))
                }

                R.id.nav_dashboard -> {
                    startActivity(Intent(this, HomepageActivity::class.java))
                    finish()
                }

                R.id.nav_settings -> Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                
                R.id.nav_logout -> finish()
            }
            drawerLayout.closeDrawers()
            true
        }

    }
}
