package com.example.gfg_firebase.Product_RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gfg_firebase.R

class ProductAdapter(val list: List<Product>, private val context: Context): RecyclerView.Adapter<ProductAdapter.ViewHolder>()
    {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
       val ProductName = itemView.findViewById<TextView>(R.id.product_name)
       val ProductPrice = itemView.findViewById<TextView>(R.id.product_price)
        val ProductUrl  = itemView.findViewById<ImageView>(R.id.product_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcy_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
         val item = list[position]
         holder.ProductName.text = item.product_name
         holder.ProductPrice.text = item.product_price

        Glide.with(context).
        load(item.imageUrl)
            .into(holder.ProductUrl)

    }

    override fun getItemCount(): Int
    {
          return list.size
    }

    }