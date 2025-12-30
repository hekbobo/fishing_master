package com.fishing.master

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fishing.master.adapter.FishingSpotAdapter
import com.fishing.master.viewmodel.MapViewModel

class MapActivity : AppCompatActivity() {

    private val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map) // Use layout resource ID directly

        setupRecyclerView()
        setupInteractions()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.spotsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.fishingSpots.observe(this) { spots ->
            val adapter = FishingSpotAdapter(spots) { spot ->
                Toast.makeText(this, "Selected: ${spot.name}", Toast.LENGTH_SHORT).show()
            }
            recyclerView.adapter = adapter
        }
    }

    private fun setupInteractions() {
        // Since we are using findViewById (didn't enable ViewBinding for this new activity explicitly in build.gradle yet, but it's on globally)
        // Let's standardise on ViewBinding for better practice if I can, but to match the pattern I used in MainActivity I should use ViewBinding.
        // However, I haven't generated the binding class yet (it happens on build).
        // For safety I will use findViewById here to guarantee it works immediately without waiting for a build cycle to generate binding classes.
        
        // Back/Close could be handled if we had a back button, but standard back press works.
    }
}
