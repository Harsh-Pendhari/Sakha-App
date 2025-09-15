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

        val iAgroInfraFund = findViewById<Button>(R.id.scheme2)
        val iAgroInfraFundURL = "https://agriinfra.dac.gov.in/Home/EligibleProjects"

        val iSoilHealthCard = findViewById<Button>(R.id.scheme3)
        val iSoilHealthCardURL = "https://www.soilhealth.dac.gov.in/admin/"

        val iPMKisanSammanNidhi = findViewById<Button>(R.id.scheme4)
        val iPMKisanSammanNidhiURL = "https://pmkisan.gov.in/homenew.aspx"

        val iPMKisan = findViewById<Button>(R.id.scheme5)
        val iPMKisanURL = "https://www.pmkisan.gov.in/KnowYour_Registration.aspx"

        val iPMFasalBimaYojana = findViewById<Button>(R.id.scheme6)
        val iPMFasalBimaYojanaURL = "https://pmfby.gov.in/"


        iPMKisanMannDhanYojana.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(iPMKisanMannDhanYojanaURL)))
        }

        iAgroInfraFund.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(iAgroInfraFundURL)))
        }

        iSoilHealthCard.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(iSoilHealthCardURL)))
        }

        iPMKisanSammanNidhi.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(iPMKisanSammanNidhiURL)))
        }

        iPMKisan.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(iPMKisanURL)))
        }

        iPMFasalBimaYojana.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(iPMFasalBimaYojanaURL)))
        }

    }
}