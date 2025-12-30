package com.fishing.master.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fishing.master.R
import com.fishing.master.model.FishingSpot

class FishingSpotAdapter(
    private val spots: List<FishingSpot>,
    private val onItemClick: (FishingSpot) -> Unit
) : RecyclerView.Adapter<FishingSpotAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.spotName)
        val descriptionText: TextView = view.findViewById(R.id.spotDescription)
        val ratingText: TextView = view.findViewById(R.id.ratingText)
        val distanceText: TextView = view.findViewById(R.id.spotDistance)
        val image: ImageView = view.findViewById(R.id.spotImage)
        val tag1: TextView = view.findViewById(R.id.tag1)
        val tag2: TextView = view.findViewById(R.id.tag2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fishing_spot, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
        holder.nameText.text = spot.name
        holder.descriptionText.text = spot.description
        holder.ratingText.text = spot.rating.toString()
        holder.distanceText.text = spot.distance

        if (spot.imageResId != null) {
            holder.image.setImageResource(spot.imageResId)
        } else if (spot.isLocked) {
             // Use a lock icon placeholder if locked and no image
             holder.image.setImageResource(android.R.drawable.ic_lock_lock)
             holder.image.setColorFilter(android.graphics.Color.GRAY)
        }

        // Simple tag binding
        if (spot.tags.isNotEmpty()) {
            holder.tag1.text = spot.tags[0]
            holder.tag1.visibility = View.VISIBLE
        } else {
            holder.tag1.visibility = View.GONE
        }
        
        if (spot.tags.size > 1) {
            holder.tag2.text = spot.tags[1]
            holder.tag2.visibility = View.VISIBLE
        } else {
            holder.tag2.visibility = View.GONE
        }

        holder.itemView.setOnClickListener { onItemClick(spot) }
    }

    override fun getItemCount() = spots.size
}
