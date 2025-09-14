package com.example.sakha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class AddCropDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcropdetails)



        val txtFieldCropType = findViewById<TextInputLayout>(R.id.cropType_dropdown_layout)
        val dropdownCropType = findViewById<AutoCompleteTextView>(R.id.cropTypeDropdown)

        txtFieldCropType.isHintEnabled = true
        dropdownCropType.hint = ""

        val cropTypes = listOf("Grain/ Cereal","Pulses/ Legumes","Fruits","Vegetables","Flowers","Spices/ Condiments","Medicinal/ Aromatics",
            "Fiber Crops","Oil seeds","Sugar Crops")

        val cropAdapter = ArrayAdapter(this, R.layout.custom_dropdown_item, cropTypes)
        dropdownCropType.setAdapter(cropAdapter)
        dropdownCropType.setDropDownBackgroundResource(R.color.dropdown_bg)

        dropdownCropType.dropDownHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dropdownCropType.dropDownWidth = WindowManager.LayoutParams.MATCH_PARENT
        dropdownCropType.threshold = 1

    }
}
