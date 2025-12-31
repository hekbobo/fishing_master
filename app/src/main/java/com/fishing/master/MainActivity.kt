package com.fishing.master

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fishing.master.adapter.RecentCatchesAdapter
import com.fishing.master.databinding.ActivityMainBinding
import com.fishing.master.model.FishCatch
import com.fishing.master.model.WeatherData
import com.fishing.master.viewmodel.MainViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    // ... onCreate code ...
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup observers
        viewModel.weatherData.observe(this) { weatherData ->
            updateWeatherCard(weatherData)
        }
        viewModel.recentCatches.observe(this) { catches ->
            updateRecentCatches(catches)
        }
        
        setupCustomBottomNavigation()
    }

    // ... setupHeader ...

    private fun updateWeatherCard(data: WeatherData) {
        binding.locationText.text = data.location
        binding.temperatureText.text = data.temperature
        binding.weatherConditionText.text = data.condition
        binding.windText.text = data.windSpeed
        binding.waterTempText.text = data.waterTemp
        binding.tideText.text = data.tideTime
    }

    // ... setupQuickActions ...

    private fun updateRecentCatches(catches: List<FishCatch>) {
        val adapter = RecentCatchesAdapter(catches)
        binding.recentCatchesRecyclerView.adapter = adapter
        
        // Handle "View All" click
        binding.viewAllText.setOnClickListener {
            android.widget.Toast.makeText(this, "View All Catches clicked", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCustomBottomNavigation() {
        // Reset all to unselected state
        fun resetSelection() {
            val unselectedAlpha = 0.6f
            binding.navHome.alpha = unselectedAlpha
            binding.navMap.alpha = unselectedAlpha
            binding.navLog.alpha = unselectedAlpha
            binding.navProfile.alpha = unselectedAlpha
        }

        fun selectItem(view: android.view.View, name: String) {
            resetSelection()
            view.alpha = 1.0f
            android.widget.Toast.makeText(this, "Navigated to $name", android.widget.Toast.LENGTH_SHORT).show()
        }

        binding.navHome.setOnClickListener { selectItem(it, "Home") }
        binding.navMap.setOnClickListener { 
            // Navigate to MapActivity
            val intent = android.content.Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        binding.navLog.setOnClickListener { selectItem(it, "Log") }
        binding.navProfile.setOnClickListener { 
            // Navigate to ProfileActivity
            val intent = android.content.Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Set home as selected by default
        binding.navHome.alpha = 1.0f

        // Handle center FAB click
        binding.centerFab.setOnClickListener {
            // Navigate to Fishing Location Activity
        }
    }
}
