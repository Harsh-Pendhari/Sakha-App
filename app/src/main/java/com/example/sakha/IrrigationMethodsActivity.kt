package com.example.sakha

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
            IrrigationMethods("Drip Irrigation", "Delivers water directly into the roots, saves water, suitable for vegetables and orchards.", R.drawable.drip_irrigation_icon ),
            IrrigationMethods("Sprinkler Irrigation", "Sprays water like natural rainfall, good for cereals & group crops.", R.drawable.sprinkler_irrigation_icon),
            IrrigationMethods("Surface Irrigation","Water flows over the field surface by gravity, suitable for paddy & wheat.", R.drawable.surface_irrigation_icon),
            IrrigationMethods("Manual Irrigation","Traditional method using buckets, pipes, or cans.", R.drawable.manual_irrigation_icon),
            IrrigationMethods("Center Pivot Irrigation","Rotates around a central pivot, spraying water in a circular pattern.", R.drawable.centre_pivot_irrigation_icon),
            IrrigationMethods("Lateral Move Irrigation","Moves laterally across the field, spraying water uniformly.", R.drawable.lateral_move_irrigation_icon),
            IrrigationMethods("Sub-Irrigation","Delivers water from below the soil surface, keeps roots moist, reduces evaporation.", R.drawable.sub_irrigation_icon),
        )

        recyclerView.adapter = IrrigationMethodsAdapter(irrigationMethods)
    }
}