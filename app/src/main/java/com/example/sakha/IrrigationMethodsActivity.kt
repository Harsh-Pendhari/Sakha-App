package com.example.sakha

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class IrrigationMethodsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_irrigationmethods)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewIrrigation)

        val irrigationMethods = listOf(
            IrrigationMethods(1,"Drip Irrigation", "Delivers water directly into the roots, saves water, suitable for vegetables and orchards.", R.drawable.drip_irrigation_icon,"https://www.dripworks.com/blog/innovations-in-drip-irrigation-latest-trends-and-technologies"),
            IrrigationMethods(2,"Sprinkler Irrigation", "Sprays water like natural rainfall, good for cereals & group crops.", R.drawable.sprinkler_irrigation_icon,"https://howtotechinfo.com/best-ai-smart-sprinklers-for-efficient-watering/#:~:text=The%202025%20lineup%20of%20AI%20smart%20sprinklers%20makes,sensors%2C%20and%20app%20control%20for%20precise%20watering%20schedules"),
            IrrigationMethods(3,"Surface Irrigation","Water flows over the field surface by gravity, suitable for paddy & wheat.", R.drawable.surface_irrigation_icon,"https://keshtezar.com/blog/posts/surface-irrigation-traditional-and-modern-techniques-for-efficient-water-delivery/#:~:text=Integrating%20technology%20in%20surface%20irrigation%20has%20led%20to,to%20schedule%20irrigation%20events%20based%20on%20real-time%20data"),
            IrrigationMethods(4,"Manual Irrigation","Traditional method using buckets, pipes, or cans.", R.drawable.manual_irrigation_icon),
            IrrigationMethods(5,"Center Pivot Irrigation","Rotates around a central pivot, spraying water in a circular pattern.", R.drawable.centre_pivot_irrigation_icon,"https://solomonagri.com/the-essential-guide-to-center-pivot-irrigation-systems/"),
            IrrigationMethods(6,"Lateral Move Irrigation","Moves laterally across the field, spraying water uniformly.", R.drawable.lateral_move_irrigation_icon,"https://irrigationmontroyal.com/en/lateral-move-irrigation/"),
            IrrigationMethods(7,"Sub-Irrigation","Delivers water from below the soil surface, keeps roots moist, reduces evaporation.", R.drawable.sub_irrigation_icon),
        )

        recyclerView.adapter = IrrigationMethodsAdapter(irrigationMethods){ method ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(method.link)
            startActivity(intent)
        }
    }
}