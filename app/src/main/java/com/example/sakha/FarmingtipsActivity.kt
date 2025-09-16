package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class FarmingtipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_farmingtips)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val soilPrepBTN = findViewById<Button>(R.id.soilPrepBTN)
        val soilPrepLink = "https://ncert.nic.in/vocational/pdf/iepf102.pdf"
        val soilPrep = getString(R.string.soil_prep)

        val irrigationBTN = findViewById<Button>(R.id.irrigationBTN)
        val irrigationLink = "https://www.fibl.org/fileadmin/documents/shop/2522-irrigation.pdf"
        val irrigation = getString(R.string.irrigation)

        val cropRotationBTN = findViewById<Button>(R.id.cropRotationBTN)
        val cropRotationLink = "https://www.sare.org/wp-content/uploads/Crop-Rotation-on-Organic-Farms.pdf"
        val cropRotation = getString(R.string.crop_rotation)

        val pestManagementBTN = findViewById<Button>(R.id.pestManagementBTN)
        val pestManagementLink = "https://ncert.nic.in/vocational/pdf/kefc106.pdf"
        val pestManagement = getString(R.string.pest_management)

        soilPrepBTN.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", soilPrepLink)
            intent.putExtra("TITLE", soilPrep)
            startActivity(intent)
        }

        irrigationBTN.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", irrigationLink)
            intent.putExtra("TITLE", irrigation)
            startActivity(intent)
        }

        cropRotationBTN.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", cropRotationLink)
            intent.putExtra("TITLE", cropRotation)
            startActivity(intent)
        }

        pestManagementBTN.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", pestManagementLink)
            intent.putExtra("TITLE", pestManagement)
            startActivity(intent)
        }
    }
}