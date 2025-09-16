package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MarketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_market)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val fruitBTN = findViewById<Button>(R.id.fruits_and_vegetables)
        val fruitBTNLink = "https://go4fresh.com/"
        val fruitTitle = getString(R.string.fruitandvegetables)

        fruitBTN.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", fruitBTNLink)
            intent.putExtra("TITLE", fruitTitle)
            startActivity(intent)
        }

        val cropBTN = findViewById<Button>(R.id.crops)
        val cropBTNLink = "https://agrimp.com/"
        val croptTitle = getString(R.string.crops)

        cropBTN.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", cropBTNLink)
            intent.putExtra("TITLE", croptTitle)
            startActivity(intent)
        }

        val liveStockBTN = findViewById<Button>(R.id.livestock)
        val liveStockBTNLink = "https://www.pashushala.com/"
        val liveStockTitle = getString(R.string.livestock)

        liveStockBTN.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", liveStockBTNLink)
            intent.putExtra("TITLE", liveStockTitle)
            startActivity(intent)
        }

        val machineryBTN = findViewById<Button>(R.id.machinery)
        val machineryBTNLink = "https://www.tractorforeveryone.com/"
        val machineryTitle = getString(R.string.machinery)

        machineryBTN.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", machineryBTNLink)
            intent.putExtra("TITLE", machineryTitle)
            startActivity(intent)
        }
    }
}
