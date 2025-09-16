package com.example.sakha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IrrigationMethodsAdapter(private val irrigationList: List<IrrigationMethods>,
                               private val onItemClick: (IrrigationMethods) -> Unit) :
    RecyclerView.Adapter<IrrigationMethodsAdapter.IrrigationViewHolder>() {

    class IrrigationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val irrigationIcon: ImageView = itemView.findViewById(R.id.irrigationIcon)
        val irrigationName: TextView = itemView.findViewById(R.id.IrrigationName)
        val irrigationDescription: TextView = itemView.findViewById(R.id.IrrigationDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IrrigationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.irrigation_methods_items, parent, false)
        return IrrigationViewHolder(view)
    }

    override fun onBindViewHolder(holder: IrrigationViewHolder, position: Int) {
        val method = irrigationList[position]
        holder.irrigationIcon.setImageResource(method.icon)
        holder.irrigationName.text = method.methodName
        holder.irrigationDescription.text = method.description

        holder.itemView.setOnClickListener{
            onItemClick(method)
        }
    }

    override fun getItemCount(): Int = irrigationList.size
}
