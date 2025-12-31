package com.fishing.master

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.location.AMapLocationClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapsInitializer
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.fishing.master.adapter.FishingSpotAdapter
import com.fishing.master.databinding.ActivityMapBinding
import com.fishing.master.model.FishingSpot
import com.fishing.master.viewmodel.MapViewModel

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private val viewModel: MapViewModel by viewModels()
    private var locationManager: LocationManager? = null
    private var aMap: AMap? = null
    private val markers = mutableListOf<Marker>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    companion object {
        private const val TAG = "MapActivity"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)
        if (aMap == null) {
            aMap = binding.mapView.map
        }

        locationManager = LocationManager(this)

        if (checkLocationPermissions()) {
            initializeMap()
        } else {
            requestLocationPermissions()
        }

        setupBottomSheet()
        setupRecyclerView()
        setupInteractions()

    }

    private fun initializeMap() {
        aMap?.let { map ->
            // 设置地图类型
            map.mapType = AMap.MAP_TYPE_NORMAL
            // 显示定位按钮
            map.uiSettings.isMyLocationButtonEnabled = true
            // 显示缩放控件
            map.uiSettings.isZoomControlsEnabled = false
            // 允许手势缩放
            map.uiSettings.isZoomGesturesEnabled = true
            // 允许手势旋转
            map.uiSettings.isRotateGesturesEnabled = true

            // 开始定位
            startLocation()
        }
    }

    private fun startLocation() {
        Log.d(TAG, "开始定位请求...")
        locationManager?.startLocation { result ->
            when (result) {
                is LocationResult.Success -> {
                    Log.d(TAG, "定位成功回调 - 纬度: ${result.latitude}, 经度: ${result.longitude}")
                    Log.d(TAG, "定位成功回调 - 城市: ${result.city}, 区域: ${result.district}")
                    
                    // 移动地图到当前位置
                    val currentLocation = LatLng(result.latitude, result.longitude)
                    aMap?.moveCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition(currentLocation, 15f, 0f, 0f)
                        )
                    )

                    // 更新附近的钓鱼点
                    viewModel.updateFishingSpotsNearby(result.latitude, result.longitude)
                }
                is LocationResult.Error -> {
                    val errorLog = "定位失败回调 - 错误码: ${result.errorCode}, 错误信息: ${result.errorMessage}"
                    Log.e(TAG, errorLog)
                    Log.e(TAG, "定位失败详情: ${getLocationErrorDescription(result.errorCode)}")
                    
                }
            }
        }
    }
    
    /**
     * 获取定位错误码描述
     */
    private fun getLocationErrorDescription(errorCode: Int): String {
        return when (errorCode) {
            1 -> "重要参数为空"
            2 -> "仅扫描到单个wifi，无法精准定位"
            3 -> "请求参数为空"
            4 -> "网络异常，链路不通"
            5 -> "XML格式错误"
            6 -> "定位结果解析失败"
            7 -> "KEY错误或未绑定服务"
            8 -> "定位初始化异常"
            9 -> "定位客户端启动失败"
            10 -> "基站、WIFI信息错误"
            11 -> "定位请求被拒绝（权限问题）"
            12 -> "定位请求超时"
            13 -> "定位结果为空"
            14 -> "定位服务响应异常"
            15 -> "定位服务返回异常"
            16 -> "定位服务返回异常"
            18 -> "定位服务返回异常"
            19 -> "定位服务返回异常"
            else -> "未知错误码: $errorCode"
        }
    }

    private fun checkLocationPermissions(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        return fineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                initializeMap()
            } else {
                Toast.makeText(this, "需要位置权限才能使用地图功能", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.spotsRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.fishingSpots.observe(this) { spots ->
            val adapter = FishingSpotAdapter(spots) { spot ->
                // 点击列表项时，移动地图到该位置
                moveToSpot(spot)
                Toast.makeText(this, "Selected: ${spot.name}", Toast.LENGTH_SHORT).show()
            }
            binding.spotsRecyclerView.adapter = adapter
            
            // 在地图上显示所有钓鱼点
            updateMapMarkers(spots)
        }
    }

    private fun updateMapMarkers(spots: List<FishingSpot>) {
        // 清除旧标记
        markers.forEach { it.remove() }
        markers.clear()

        // 添加新标记
        aMap?.let { map ->
            spots.forEach { spot ->
                val latLng = LatLng(spot.lat.toDouble(), spot.lng.toDouble())
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(spot.name)
                        .snippet(spot.description)
                )
                markers.add(marker)
            }
        }
    }

    private fun moveToSpot(spot: FishingSpot) {
        val latLng = LatLng(spot.lat.toDouble(), spot.lng.toDouble())
        aMap?.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition(latLng, 15f, 0f, 0f)
            )
        )
    }

    /**
     * 设置底部列表的拖拽行为
     */
    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        
        // 设置初始状态为折叠（显示 peekHeight）
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        
        // 设置折叠高度（dp转px）
        val peekHeight = (300 * resources.displayMetrics.density).toInt()
        bottomSheetBehavior.peekHeight = peekHeight
        
        // 允许拖拽展开
        bottomSheetBehavior.isDraggable = true
        
        // 不允许完全隐藏
        bottomSheetBehavior.isHideable = false
        
        // 设置展开时的偏移量（可以覆盖整个屏幕）
        bottomSheetBehavior.setExpandedOffset(0)
        
        // 监听状态变化
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: android.view.View, newState: Int) {
                // 可以在这里处理状态变化
            }
            
            override fun onSlide(bottomSheet: android.view.View, slideOffset: Float) {
                // slideOffset: -1 到 1，0 表示折叠状态，1 表示完全展开
            }
        })
    }

    private fun setupInteractions() {
        // 定位按钮点击事件
        // 注意：高德地图的定位按钮已经内置，这里可以添加其他交互
    }

    // 地图生命周期管理
    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager?.stopLocation()
        binding.mapView.onDestroy()
    }
}
