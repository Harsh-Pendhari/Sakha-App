package com.example.sakha

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GovSchemesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_govschemes)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val iPMKisanMannDhanYojana = findViewById<Button>(R.id.scheme1)
        val iPMKisanMannDhanYojanaURL = "https://maandhan.in/"
        val scheme1 = R.string.scheme1

        val iAgroInfraFund = findViewById<Button>(R.id.scheme2)
        val iAgroInfraFundURL = "https://agriinfra.dac.gov.in/Home/EligibleProjects"
        val scheme2 = R.string.scheme2

        val iSoilHealthCard = findViewById<Button>(R.id.scheme3)
        val iSoilHealthCardURL = "https://www.soilhealth.dac.gov.in/admin/"
        val scheme3 = R.string.scheme3

        val iPMKisanSammanNidhi = findViewById<Button>(R.id.scheme4)
        val iPMKisanSammanNidhiURL = "https://pmkisan.gov.in/homenew.aspx"
        val scheme4 = R.string.scheme4

        val iPMKisan = findViewById<Button>(R.id.scheme5)
        val iPMKisanURL = "https://www.pmkisan.gov.in/KnowYour_Registration.aspx"
        val scheme5 = R.string.scheme5

        val iPMFasalBimaYojana = findViewById<Button>(R.id.scheme6)
        val iPMFasalBimaYojanaURL = "https://pmfby.gov.in/"
        val scheme6 = R.string.scheme6


        iPMKisanMannDhanYojana.setOnClickListener{
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", iPMKisanMannDhanYojanaURL)
            intent.putExtra("TITLE", scheme1)
            startActivity(intent)
        }

        iAgroInfraFund.setOnClickListener{
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", iAgroInfraFundURL)
            intent.putExtra("TITLE", scheme2)
            startActivity(intent)
        }

        iSoilHealthCard.setOnClickListener{
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", iSoilHealthCardURL)
            intent.putExtra("TITLE", scheme3)
            startActivity(intent)
        }

        iPMKisanSammanNidhi.setOnClickListener{
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", iPMKisanSammanNidhiURL)
            intent.putExtra("TITLE", scheme4)
            startActivity(intent)
        }

        iPMKisan.setOnClickListener{
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", iPMKisanURL)
            intent.putExtra("TITLE", scheme5)
            startActivity(intent)
        }

        iPMFasalBimaYojana.setOnClickListener{
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", iPMFasalBimaYojanaURL)
            intent.putExtra("TITLE", scheme6)
            startActivity(intent)
        }

    }
}