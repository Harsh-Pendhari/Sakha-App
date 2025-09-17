package com.example.sakha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WholesalePriceAdapter(private val priceList: List<WholesalePrice>) :
    RecyclerView.Adapter<WholesalePriceAdapter.PriceViewHolder>() {

    class PriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCommodity: TextView = itemView.findViewById(R.id.tvCommodity)
        val tvTodayPrice: TextView = itemView.findViewById(R.id.tvTodayPrice)
        val tvYesterdayPrice: TextView = itemView.findViewById(R.id.tvYesterdayPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wholesale_price, parent, false)
        return PriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val item = priceList[position]
        holder.tvCommodity.text = item.commodity
        holder.tvTodayPrice.text = item.todayPrice
        holder.tvYesterdayPrice.text = item.yesterdayPrice
    }

    override fun getItemCount(): Int = priceList.size
}
