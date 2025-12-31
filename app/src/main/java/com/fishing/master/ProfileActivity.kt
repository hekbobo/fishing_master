package com.fishing.master

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fishing.master.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader()
        setupProfileSection()
        setupBottomNavigation()
    }

    private fun setupHeader() {
        // 返回按钮
        binding.btnBack.setOnClickListener {
            finish()
        }

        // 通知图标
        binding.notificationIcon.setOnClickListener {
            // TODO: 打开通知页面
        }

        // 设置图标
        binding.settingsIcon.setOnClickListener {
            // TODO: 打开设置页面
        }
    }

    private fun setupProfileSection() {
        // 编辑资料按钮
        binding.editProfileButton.setOnClickListener {
            // TODO: 打开编辑资料页面
            android.widget.Toast.makeText(this, "编辑资料", android.widget.Toast.LENGTH_SHORT).show()
        }

        // 查看图鉴
        binding.viewEncyclopediaText.setOnClickListener {
            // TODO: 打开图鉴页面
            android.widget.Toast.makeText(this, "查看图鉴", android.widget.Toast.LENGTH_SHORT).show()
        }

        // 我的装备箱
        binding.gearBoxItem.setOnClickListener {
            // TODO: 打开装备箱页面
            android.widget.Toast.makeText(this, "我的装备箱", android.widget.Toast.LENGTH_SHORT).show()
        }

        // 成就徽章
        binding.achievementsItem.setOnClickListener {
            // TODO: 打开成就页面
            android.widget.Toast.makeText(this, "成就徽章", android.widget.Toast.LENGTH_SHORT).show()
        }

        // 隐私与安全
        binding.privacyItem.setOnClickListener {
            // TODO: 打开隐私设置页面
            android.widget.Toast.makeText(this, "隐私与安全", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        // 重置所有导航项为未选中状态
        fun resetSelection() {
            val unselectedAlpha = 0.6f
            binding.navMap.alpha = unselectedAlpha
            binding.navDiscover.alpha = unselectedAlpha
            binding.navRecord.alpha = unselectedAlpha
            binding.navProfile.alpha = unselectedAlpha
        }

        fun selectItem(view: android.view.View, name: String) {
            resetSelection()
            view.alpha = 1.0f
        }

        // 地图
        binding.navMap.setOnClickListener {
            val intent = android.content.Intent(this, MapActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 发现
        binding.navDiscover.setOnClickListener {
            // TODO: 导航到发现页面
            selectItem(it, "Discover")
        }

        // 记录
        binding.navRecord.setOnClickListener {
            // TODO: 导航到记录页面
            selectItem(it, "Record")
        }

        // 我的（当前页面）
        binding.navProfile.setOnClickListener {
            selectItem(it, "Profile")
        }

        // 设置"我的"为选中状态
        binding.navProfile.alpha = 1.0f

        // 中心FAB按钮
        binding.centerFab.setOnClickListener {
            // TODO: 打开添加功能
        }
    }
}

