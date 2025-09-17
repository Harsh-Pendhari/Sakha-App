package com.example.sakha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CropAdapter(private val cropList: List<Crop>) :
    RecyclerView.Adapter<CropAdapter.CropViewHolder>() {

    class CropViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cropName: TextView = itemView.findViewById(R.id.cropName)
        val cropType: TextView = itemView.findViewById(R.id.cropType)
        val cropPlanted: TextView = itemView.findViewById(R.id.cropPlanted)
        val cropStatus: TextView = itemView.findViewById(R.id.cropStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.crop_item, parent, false)
        return CropViewHolder(view)
    }

    override fun onBindViewHolder(holder: CropViewHolder, position: Int) {
        val crop = cropList[position]
        holder.cropName.text = crop.name
        holder.cropType.text = "Type: ${crop.type}"
        holder.cropPlanted.text = "Planted: ${crop.planted}"
        holder.cropStatus.text = "Status: ${crop.status}"
    }

    override fun getItemCount(): Int = cropList.size
}