package com.example.sakha

import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var stateDistricts: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load JSON from assets
        val jsonString = assets.open("state_districts.json").bufferedReader().use { it.readText() }
        stateDistricts = JSONObject(jsonString)

        // Get all states (keys of the JSON)
        val stateList = stateDistricts.keys().asSequence().toList().sorted()

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

        // State dropdown
        val stateAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, stateList)
        dropdownState.setAdapter(stateAdapter)
        dropdownState.setDropDownBackgroundResource(R.color.dropdown_bg)
        dropdownState.dropDownHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dropdownState.dropDownWidth = WindowManager.LayoutParams.MATCH_PARENT
        dropdownState.threshold = 1

        // On state selection → load districts
        dropdownState.setOnItemClickListener { parent, _, position, _ ->
            // Use the adapter's filtered item, not stateList[position]
            val selectedState = (parent.getItemAtPosition(position) as String).trim()

            if (stateDistricts.has(selectedState)) {
                val districtsJson = stateDistricts.getJSONArray(selectedState)
                val districtList = MutableList(districtsJson.length()) { i ->
                    districtsJson.getString(i)
                }

                val districtAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, districtList)
                dropdownDistrict.setAdapter(districtAdapter)
                dropdownDistrict.setDropDownBackgroundResource(R.color.dropdown_bg)
                dropdownDistrict.dropDownHeight = WindowManager.LayoutParams.WRAP_CONTENT
                dropdownDistrict.dropDownWidth = WindowManager.LayoutParams.MATCH_PARENT
                dropdownDistrict.setText("", false)   // clear previous selection
                dropdownDistrict.threshold = 1        // enable filtering on first char
            }
        }
    }
}
