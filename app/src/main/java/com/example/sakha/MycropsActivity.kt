package com.example.sakha

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MycropsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cropAdapter: CropAdapter
    private val crops = mutableListOf<Crop>() // dynamic list
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mycrops)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        cropAdapter = CropAdapter(crops)
        recyclerView.adapter = cropAdapter

        loadCropsFromFirestore()
    }

    private fun loadCropsFromFirestore() {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        firestore.collection("users").document(uid).collection("crops")
            .orderBy("timestamp") // newest last
            .get()
            .addOnSuccessListener { snapshot ->
                crops.clear()
                for (doc in snapshot.documents) {
                    val crop = Crop(
                        cropName = doc.getString("cropName") ?: "Unknown",
                        cropType = doc.getString("cropType") ?: "Unknown",
                        plantingDate = doc.getString("plantingDate") ?: "Unknown",
                        status = doc.getString("status") ?: "Unknown"
                    )
                    crops.add(crop)
                }
                cropAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load crops: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        // Reload crops when returning from AddCropActivity
        loadCropsFromFirestore()
    }
}
