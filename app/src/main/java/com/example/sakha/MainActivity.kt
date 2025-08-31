package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        startActivity(Intent(this, RegisterActivity::class.java))
//        finish()

        val states = listOf(
            "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Tripura", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",
            //Union Territories
            "Andaman and Nicobar Islands", "Chandigarh", "Dadra and Nagar Haveli and Daman and Diu",
            "Delhi", "Jammu and Kashmir", "Ladakh", "Lakshadweep", "Puducherry"
        )

        val districtsMap = mapOf(
            "Andhra Pradesh" to listOf("Alluri Sitharama Raju", "Anakapalli", "Bapatla", "Dr. B. R. Ambedkar Konaseema", "East Godavari", "Eluru", "Guntur",
                "Kakinada", "Krishna", "NTR", "Palnadu", "Parvathipuram Manyam", "Prakasam", "Srikakulam", "Sri Potti Sriramulu Nellore", "Visakhapatnam",
                "Vizianagaram", "West Godavari", "Annamayya", "Anantapur", "Chittoor", "Kadapa (YSR)", "Kurnool", "Nandyal", "Sri Sathya Sai", "Tirupati")
        )

        val txtFieldState = findViewById<TextInputLayout>(R.id.state_dropdown_layout)
        val dropdownState = findViewById<AutoCompleteTextView>(R.id.stateDropdown)

        val txtFieldDistrict = findViewById<TextInputLayout>(R.id.district_dropdown_layout)
        val dropdownDistrict = findViewById<AutoCompleteTextView>(R.id.districtDropdown)

        val txtFieldVillage = findViewById<TextInputLayout>(R.id.village_dropdown_layout)
        val dropdownVillage = findViewById<AutoCompleteTextView>(R.id.villageDropdown)

        txtFieldState.isHintEnabled = true
        dropdownState.hint = ""

        txtFieldDistrict.isHintEnabled = true
        dropdownDistrict.hint = ""

        txtFieldVillage.isHintEnabled = true
        dropdownVillage.hint = ""

        val stateAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, states.sorted())
        dropdownState.setAdapter(stateAdapter)
        dropdownState.setDropDownBackgroundResource(R.color.dropdown_bg)
        dropdownState.dropDownHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dropdownState.dropDownWidth = WindowManager.LayoutParams.MATCH_PARENT

        dropdownState.threshold = 1

        dropdownState.setOnItemClickListener { _, _, position, _ ->
            val selectedState = stateAdapter.getItem(position)

            val districts = districtsMap[selectedState] ?: emptyList()

            val districtAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, districts)
            dropdownDistrict.setAdapter(districtAdapter)
            dropdownDistrict.setText("", false) // clear old selection
        }
    }
}