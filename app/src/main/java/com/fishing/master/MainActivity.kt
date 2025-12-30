package com.fishing.master

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fishing.master.adapter.RecentCatchesAdapter
import com.fishing.master.databinding.ActivityMainBinding
import com.fishing.master.model.FishCatch
import com.fishing.master.model.WeatherData
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader()
        setupWeatherCard()
        setupQuickActions()
        setupRecentCatches()
        setupCustomBottomNavigation()
    }

    private fun setupHeader() {
        // Set greeting based on time of day
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val greetingResId = when (hour) {
            in 5..11 -> R.string.greeting_morning
            in 12..17 -> R.string.greeting_afternoon
            in 18..20 -> R.string.greeting_evening
            else -> R.string.greeting_night
        }
        binding.greetingText.setText(greetingResId)
        binding.userNameText.setText(R.string.user_name)

        // Set profile image placeholder
        binding.profileImage.setImageResource(android.R.drawable.ic_menu_myplaces)

        // Handle notification click
        binding.notificationIcon.setOnClickListener {
            android.widget.Toast.makeText(this, "Notifications clicked", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupWeatherCard() {
        // Manual data setting as per design
        binding.locationText.setText(R.string.location_tahoe)
        binding.temperatureText.text = "24°C"
        binding.weatherConditionText.setText(R.string.weather_sunny_intervals)
        binding.windText.text = "12km"
        binding.waterTempText.text = "18°C"
        binding.tideText.text = "06:42 AM"
    }

    private fun setupQuickActions() {
        val clickListener = android.view.View.OnClickListener { view ->
            val message = when (view.id) {
                R.id.actionLogCatch -> "Log Catch clicked"
                R.id.actionNewTrip -> "New Trip clicked"
                R.id.actionMyGear -> "My Gear clicked"
                R.id.actionCommunity -> "Community clicked"
                else -> "Action clicked"
            }
            android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
        }

        binding.actionLogCatch.setOnClickListener(clickListener)
        binding.actionNewTrip.setOnClickListener(clickListener)
        binding.actionMyGear.setOnClickListener(clickListener)
        binding.actionCommunity.setOnClickListener(clickListener)
    }

    private fun setupRecentCatches() {
        val catches = listOf(
            FishCatch(
                id = "1",
                fishName = "大口黑鲈",
                imageResId = android.R.drawable.ic_menu_gallery
            ),
            FishCatch(
                id = "2",
                fishName = "虹鳟",
                imageResId = android.R.drawable.ic_menu_gallery
            ),
            FishCatch(
                id = "3",
                fishName = "蓝鳃太阳鱼",
                imageResId = android.R.drawable.ic_menu_gallery
            ),
            FishCatch(
                id = "4",
                fishName = "鲶鱼",
                imageResId = android.R.drawable.ic_menu_gallery
            )
        )

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
        binding.navMap.setOnClickListener { selectItem(it, "Map") }
        binding.navLog.setOnClickListener { selectItem(it, "Log") }
        binding.navProfile.setOnClickListener { selectItem(it, "Profile") }

        // Set home as selected by default
        binding.navHome.alpha = 1.0f

        // Handle center FAB click
        binding.centerFab.setOnClickListener {
            android.widget.Toast.makeText(this, "Add New Item clicked", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}
