package com.example.sakha

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class PesticidesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pesticides)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.pesticidesView)

        val pesticide = listOf(
            Pesticides(1,"Insecticides", "Controls Insects", R.drawable.pesticides_icon,"https://mankindag.com/product/insecticide/"),
            Pesticides(2,"Herbicides", "Kills Weeds", R.drawable.herbicides_icon,"https://mankindag.com/product/herbicide/"),
            Pesticides(3,"Fungicides","Controls Fungi", R.drawable.fungicides_icon,"https://mankindag.com/product/fungicide/"),
        )

        recyclerView.adapter = PesticidesAdapter(pesticide){ method ->
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", method.link)
            intent.putExtra("TITLE", method.pesticideName)
            startActivity(intent)
        }
    }
}