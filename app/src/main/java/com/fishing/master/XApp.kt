package com.fishing.master

import android.app.Application
import android.util.Log
import com.amap.api.location.AMapLocationClient
import com.amap.api.maps.MapsInitializer

/**
 * 应用全局Application类
 * 用于统一管理应用级别的初始化工作
 */
class XApp : Application() {
    
    companion object {
        private const val TAG = "XApp"
        
        @JvmStatic
        lateinit var instance: XApp
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        Log.d(TAG, "Application onCreate: 开始初始化应用")
        
        // 初始化高德地图SDK隐私合规
        initAmapPrivacy()
        
        // 可以在这里添加其他初始化工作
        // initCrashHandler()
        // initImageLoader()
        // initDatabase()
        // initNetwork()
        
        Log.d(TAG, "Application onCreate: 应用初始化完成")
    }
    
    /**
     * 初始化高德地图SDK隐私合规
     * 必须在调用SDK任何接口前调用
     */
    private fun initAmapPrivacy() {
        try {
            // 地图SDK隐私合规
            MapsInitializer.updatePrivacyShow(this, true, true)
            MapsInitializer.updatePrivacyAgree(this, true)
            
            // 定位SDK隐私合规
            AMapLocationClient.updatePrivacyShow(this, true, true)
            AMapLocationClient.updatePrivacyAgree(this, true)
            
            Log.d(TAG, "高德地图SDK隐私合规设置完成")
        } catch (e: Exception) {
            Log.e(TAG, "高德地图SDK隐私合规设置失败", e)
        }
    }
    
    /**
     * 获取应用上下文
     */
    fun getAppContext() = applicationContext
}

