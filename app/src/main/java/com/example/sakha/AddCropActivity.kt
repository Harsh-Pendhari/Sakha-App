package com.example.sakha

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class AddCropActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addcrop)

        val textView = findViewById<TextView>(R.id.nocropText)
        textView.text = Html.fromHtml(getString(R.string.nocroptxt), Html.FROM_HTML_MODE_LEGACY)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addCropBTN = findViewById<Button>(R.id.addCropsBTN)

        addCropBTN.setOnClickListener{
            startActivity(Intent(this, AddCropDetailsActivity::class.java))
        }
    }
}