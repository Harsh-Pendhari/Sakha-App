package com.example.sakha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class AddCropDetailsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addcropdetails)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val cropNameInput = findViewById<EditText>(R.id.cropname)
        val dateInput = findViewById<EditText>(R.id.dateInput)
        val cropTypeDropdown = findViewById<Spinner>(R.id.cropTypeDropdown)
        val cropStatusDropdown = findViewById<Spinner>(R.id.cropStatusDropdown)
        val addCropBtn = findViewById<Button>(R.id.AddCropDetailsBTN)

        dateInput.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, y, m, d ->
                    dateInput.setText("$d/${m + 1}/$y")
                },
                year, month, day
            )
            datePicker.show()
        }

        val cropTypes = listOf(
            "Select Crop Type","Grain/ Cereal","Pulses/ Legumes","Fruits","Vegetables",
            "Flowers","Spices/ Condiments","Medicinal/ Aromatics","Fiber Crops","Oil seeds","Sugar Crops"
        )

        val cropTypeAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.custom_spinner_item,
            cropTypes
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

        cropTypeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        cropTypeDropdown.adapter = cropTypeAdapter
        cropTypeDropdown.setSelection(0, false)

        // Crop growth status spinner
        val growthStatus = listOf(
            "Select Growth Status","Germination","Growing","Flowering","Fruiting","Mature","Ready to Harvest"
        )

        val growthStatusAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.custom_spinner_item,
            growthStatus
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

        growthStatusAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        cropStatusDropdown.adapter = growthStatusAdapter
        cropStatusDropdown.setSelection(0, false)

        // Save to Firestore
        addCropBtn.setOnClickListener {
            val uid = auth.currentUser?.uid
            if (uid == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cropName = cropNameInput.text.toString().trim()
            val cropType = cropTypeDropdown.selectedItem.toString()
            val plantingDate = dateInput.text.toString().trim()
            val status = cropStatusDropdown.selectedItem.toString()

            // Simple validation
            if (cropName.isEmpty() || cropType.startsWith("Select") ||
                plantingDate.isEmpty() || status.startsWith("Select")) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cropDetails = hashMapOf(
                "cropName" to cropName,
                "cropType" to cropType,
                "plantingDate" to plantingDate,
                "status" to status,
                "timestamp" to System.currentTimeMillis()
            )

            firestore.collection("users").document(uid)
                .collection("crops")
                .add(cropDetails)
                .addOnSuccessListener {
                    Toast.makeText(this, "Crop details added!", Toast.LENGTH_SHORT).show()
                    finish() // Close activity after saving
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
