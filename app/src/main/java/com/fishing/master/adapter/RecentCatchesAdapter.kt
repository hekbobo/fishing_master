package com.fishing.master.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fishing.master.R
import com.fishing.master.model.FishCatch

class RecentCatchesAdapter(
    private val catches: List<FishCatch>
) : RecyclerView.Adapter<RecentCatchesAdapter.CatchViewHolder>() {

    class CatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fishImage: ImageView = itemView.findViewById(R.id.fishImage)
        val fishNameText: TextView = itemView.findViewById(R.id.fishNameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_catch, parent, false)
        return CatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatchViewHolder, position: Int) {
        val fishCatch = catches[position]
        holder.fishNameText.text = fishCatch.fishName

        // Load image
        if (fishCatch.imageResId != null) {
            holder.fishImage.setImageResource(fishCatch.imageResId)
        } else if (fishCatch.imageUrl != null) {
            Glide.with(holder.itemView.context)
                .load(fishCatch.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.fishImage)
        } else {
            // Use default placeholder
            holder.fishImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    override fun getItemCount(): Int = catches.size
}

