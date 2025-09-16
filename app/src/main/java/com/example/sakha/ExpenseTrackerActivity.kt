package com.example.sakha

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExpenseTrackerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_expense_tracker)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etCost = findViewById<EditText>(R.id.etCost)
        val etRevenue = findViewById<EditText>(R.id.etRevenue)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        tvResult.setText("")

        btnCalculate.setOnClickListener {
            val costText = etCost.text.toString()
            val revenueText = etRevenue.text.toString()

            if (costText.isEmpty() || revenueText.isEmpty()) {
                tvResult.text = "Please enter both values"
                tvResult.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
                return@setOnClickListener
            }

            val cost = costText.toDouble()
            val revenue = revenueText.toDouble()
            val profitOrLoss = revenue - cost

            if (profitOrLoss > 0) {
                tvResult.text = "Profit: ₹%.2f".format(profitOrLoss)
                tvResult.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
            } else if (profitOrLoss < 0) {
                tvResult.text = "Loss: ₹%.2f".format(-profitOrLoss)
                tvResult.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            } else {
                tvResult.text = "No Profit, No Loss"
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.gray))
            }
        }
    }
}
