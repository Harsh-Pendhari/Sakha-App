package com.example.sakha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class AddCropDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addcropdetails)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dateInput = findViewById<EditText>(R.id.dateInput)

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

        val cropTypes = listOf("Select Crop Type","Grain/ Cereal","Pulses/ Legumes","Fruits","Vegetables","Flowers","Spices/ Condiments","Medicinal/ Aromatics",
            "Fiber Crops","Oil seeds","Sugar Crops")

        val cropTypeDropdown = findViewById<Spinner>(R.id.cropTypeDropdown)

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


        val growthStatus = listOf("Select Growth Status","Germination","Growing","Flowering","Fruiting","Mature","Ready to Harvest")
        val growthStatusDropdown = findViewById<Spinner>(R.id.cropStatusDropdown)

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
        growthStatusDropdown.adapter = growthStatusAdapter
        growthStatusDropdown.setSelection(0, false)
    }
}
