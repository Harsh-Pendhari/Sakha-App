package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

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
        val soilPrep = getString(R.string.soil_prep)
        val soilPrepLink = getString(R.string.soil_prep)

        val irrigationBTN = findViewById<Button>(R.id.irrigationBTN)
        val irrigation = getString(R.string.irrigation)
        val irrigationLink = getString(R.string.irrigation)

        val cropRotationBTN = findViewById<Button>(R.id.cropRotationBTN)
        val cropRotation = getString(R.string.crop_rotation)
        val cropRotationLink = getString(R.string.crop_rotation)

        val pestManagementBTN = findViewById<Button>(R.id.pestManagementBTN)
        val pestManagement = getString(R.string.pest_management)
        val pestManagementLink = getString(R.string.pest_management)


    }
}