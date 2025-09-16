package com.example.sakha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PesticidesAdapter(private val pesticidesList: List<Pesticides>,
                               private val onItemClick: (Pesticides) -> Unit) :
    RecyclerView.Adapter<PesticidesAdapter.PesticidesViewHolder>() {

    class PesticidesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pesticideIcon: ImageView = itemView.findViewById(R.id.pesticideIcon)
        val pesticideName: TextView = itemView.findViewById(R.id.PesticideName)
        val pesticideDescription: TextView = itemView.findViewById(R.id.PesticideDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesticidesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pesticides_items, parent, false)
        return PesticidesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesticidesViewHolder, position: Int) {
        val method = pesticidesList[position]
        holder.pesticideIcon.setImageResource(method.icon)
        holder.pesticideName.text = method.pesticideName
        holder.pesticideDescription.text = method.description

        holder.itemView.setOnClickListener{
            onItemClick(method)
        }
    }

    override fun getItemCount(): Int = pesticidesList.size
}
