package com.example.sakha

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class MktPriceTabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mkt_price_tab)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMktPrice)

        val priceList = loadCSVData()

        val adapter = WholesalePriceAdapter(priceList)
        recyclerView.adapter = adapter
    }

    private fun loadCSVData(): List<WholesalePrice> {
        val list = mutableListOf<WholesalePrice>()
        try {
            val inputStream = assets.open("wholesale_prices.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            var isHeader = true

            while (reader.readLine().also { line = it } != null) {
                if (isHeader) { // Skip first line (column names)
                    isHeader = false
                    continue
                }
                val tokens = line!!.split(",")
                if (tokens.size >= 3) {
                    val commodity = tokens[0]
                    val todayPrice = tokens[1]
                    val yesterdayPrice = tokens[2]

                    list.add(WholesalePrice(commodity, todayPrice, yesterdayPrice))
                }
            }

            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}
