package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject

class UserdetailsformActivity : AppCompatActivity() {
    private lateinit var stateDistricts: JSONObject
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_userdetailsform)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

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
                dropdownDistrict.setText("", false)
                dropdownDistrict.threshold = 1
            }
        }

        // Village dropdown
        if (stateDistricts.has("Indian_Villages")) {
            val villagesJson = stateDistricts.getJSONArray("Indian_Villages")
            val villageList = MutableList(villagesJson.length()) { i ->
                villagesJson.getString(i)
            }

            val villageAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, villageList)
            dropdownVillage.setAdapter(villageAdapter)
            dropdownVillage.setDropDownBackgroundResource(R.color.dropdown_bg)
            dropdownVillage.dropDownHeight = WindowManager.LayoutParams.WRAP_CONTENT
            dropdownVillage.dropDownWidth = WindowManager.LayoutParams.MATCH_PARENT
            dropdownVillage.threshold = 1
        }

        // Unit Spinner
        val unitSpinner = findViewById<Spinner>(R.id.unitSpinner)
        val landAreaInput = findViewById<EditText>(R.id.landAreaInput)
        val units = listOf("Acres", "Guntha")

        val unitAdapter = ArrayAdapter(this, R.layout.custom_spinner_item, units)
        unitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        unitSpinner.adapter = unitAdapter
        unitSpinner.setSelection(0)

        val irrigationMethodDropdown = findViewById<Spinner>(R.id.irrigationDropdown)

// Options with the hint at index 0
        val irrigationMethods = listOf(
            getString(R.string.irrigation_hint), // "Irrigation Method"
            "Surface Irrigation",
            "Drip Irrigation",
            "Sprinkler Irrigation",
            "Center Pivot Irrigation",
            "Lateral Move Irrigation",
            "Sub-Irrigation",
            "Manual Irrigation"
        )

        val irrigationMethodsAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.custom_spinner_item,
            irrigationMethods
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0 // Disable the first item (hint)
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(getColor(R.color.text_view_textColorHint))
                } else {
                    view.setTextColor(getColor(R.color.text_view_textColor))
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(getColor(R.color.text_view_textColorHint))
                } else {
                    view.setTextColor(getColor(R.color.text_view_textColor))
                }
                return view
            }
        }

        irrigationMethodsAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        irrigationMethodDropdown.adapter = irrigationMethodsAdapter
        irrigationMethodDropdown.setSelection(0, false)


        // Soil type spinner
        val soilTypes = listOf("Select Soil Type","Red","Black","Sandy","Loamy")
        val soilTypeDropdown = findViewById<Spinner>(R.id.soilTypeDropdown)

        val soilTypeAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.custom_spinner_item,
            soilTypes
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0 // Disable the first item (hint)
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(getColor(R.color.text_view_textColorHint))
                } else {
                    view.setTextColor(getColor(R.color.text_view_textColor))
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(getColor(R.color.text_view_textColorHint))
                } else {
                    view.setTextColor(getColor(R.color.text_view_textColor))
                }
                return view
            }
        }

        soilTypeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        soilTypeDropdown.adapter = soilTypeAdapter
        soilTypeDropdown.setSelection(0, false)

        // Water source spinner
        val waterSources = listOf("Select Water Source","Canal Irrigation","River/ Stream","Well","Tube Well/ Bore-well","Tank/ Pond",
            "Rainwater Harvesting","Lake","Dam/ Reservoir","Groundwater","Check Dam/ Farm Pond")

        val waterSourcesDropdown = findViewById<Spinner>(R.id.waterSourceDropdown)

        val waterSourcesAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.custom_spinner_item,
            waterSources
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0 // Disable the first item (hint)
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(getColor(R.color.text_view_textColorHint))
                } else {
                    view.setTextColor(getColor(R.color.text_view_textColor))
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(getColor(R.color.text_view_textColorHint))
                } else {
                    view.setTextColor(getColor(R.color.text_view_textColor))
                }
                return view
            }
        }

        waterSourcesAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        waterSourcesDropdown.adapter = waterSourcesAdapter
        waterSourcesDropdown.setSelection(0, false)

        // Submit button
        val submitBtn = findViewById<Button>(R.id.submit)
        val fertilizersGroup = findViewById<RadioGroup>(R.id.fertilizersGroup)

        submitBtn.setOnClickListener {
            val uid = auth.currentUser?.uid
            if (uid == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val state = dropdownState.text.toString().trim()
            val district = dropdownDistrict.text.toString().trim()
            val village = dropdownVillage.text.toString().trim()
            val landArea = landAreaInput.text.toString().trim()
            val unit = unitSpinner.selectedItem.toString()
            val irrigation = irrigationMethodDropdown.selectedItem.toString()
            val soilType = soilTypeDropdown.selectedItem.toString()
            val waterSource = waterSourcesDropdown.selectedItem.toString()

            val selectedFertilizerId = fertilizersGroup.checkedRadioButtonId
            val fertilizers = if (selectedFertilizerId != -1) {
                findViewById<RadioButton>(selectedFertilizerId).text.toString()
            } else {
                ""
            }

            val userDetails = hashMapOf(
                "state" to state,
                "district" to district,
                "village" to village,
                "landArea" to "$landArea $unit",
                "irrigationMethod" to irrigation,
                "soilType" to soilType,
                "waterSource" to waterSource,
                "fertilizers" to fertilizers
            )

            firestore.collection("users").document(uid)
                .set(userDetails)
                .addOnSuccessListener {
                    Toast.makeText(this, "Details saved successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomepageActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Helper function for dropdowns
    private fun getCustomAdapter(items: List<String>): ArrayAdapter<String> {
        return object : ArrayAdapter<String>(this, R.layout.custom_spinner_item, items) {
            override fun isEnabled(position: Int): Boolean = position != 0

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) view.setTextColor(getColor(R.color.text_view_textColorHint))
                else view.setTextColor(getColor(R.color.text_view_textColor))
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == 0) view.setTextColor(getColor(R.color.text_view_textColorHint))
                else view.setTextColor(getColor(R.color.text_view_textColor))
                return view
            }
        }
    }
}
