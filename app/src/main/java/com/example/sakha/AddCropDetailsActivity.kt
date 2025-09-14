package com.example.sakha

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AddCropDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcropdetails)

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
    }
}
