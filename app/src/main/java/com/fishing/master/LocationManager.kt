package com.fishing.master

import android.content.Context
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

/**
 * 钓鱼大师应用的定位管理类
 * 用于获取当前位置，帮助用户找到附近的钓鱼地点
 */
class LocationManager(context: Context) {
    private val locationClient: AMapLocationClient
    private val locationOption: AMapLocationClientOption
    
    companion object {
        private const val TAG = "LocationManager"
    }

    init {
        // 初始化定位客户端
        locationClient = AMapLocationClient(context.applicationContext)
        
        // 初始化定位参数
        locationOption = AMapLocationClientOption().apply {
            // 设置定位模式为高精度模式
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            // 设置是否返回逆地理地址信息
            isNeedAddress = true
            // 设置是否只定位一次
            isOnceLocation = true
            // 设置是否强制刷新WIFI状态
            isWifiActiveScan = true
            // 设置是否允许模拟位置
            isMockEnable = false
            // 设置定位间隔，单位毫秒
            httpTimeOut = 20000
        }

        // 设置定位参数
        locationClient.setLocationOption(locationOption)
    }

    /**
     * 开始定位
     * @param callback 定位结果回调
     */
    fun startLocation(callback: (LocationResult) -> Unit) {
        Log.d(TAG, "开始定位...")
        val listener = AMapLocationListener { location ->
            if (location.errorCode == 0) {
                // 定位成功
                Log.d(TAG, "定位成功 - 纬度: ${location.latitude}, 经度: ${location.longitude}")
                Log.d(TAG, "地址: ${location.address}")
                Log.d(TAG, "城市: ${location.city}, 区域: ${location.district}")
                Log.d(TAG, "POI名称: ${location.poiName}")
                Log.d(TAG, "定位类型: ${location.locationType}")
                Log.d(TAG, "定位精度: ${location.accuracy}米")
                
                val result = LocationResult.Success(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    address = location.address,
                    poiName = location.poiName,
                    city = location.city,
                    district = location.district
                )
                callback(result)
            } else {
                // 定位失败
                val errorMsg = "定位失败 - 错误码: ${location.errorCode}, 错误信息: ${location.errorInfo}"
                Log.e(TAG, errorMsg)
                Log.e(TAG, "错误详情: ${getErrorDescription(location.errorCode)}")
                
                val result = LocationResult.Error(
                    errorCode = location.errorCode,
                    errorMessage = location.errorInfo
                )
                callback(result)
            }
        }
        
        locationClient.setLocationListener(listener)
        locationClient.startLocation()
    }
    
    /**
     * 获取错误码描述
     */
    private fun getErrorDescription(errorCode: Int): String {
        return when (errorCode) {
            1 -> "一些重要参数为空，如context；请对定位传递的参数进行非空判断"
            2 -> "定位失败，由于仅扫描到单个wifi，不能精准的计算出位置；请重新尝试"
            3 -> "获取到的请求参数为空，可能获取过程中出现异常；请重新尝试"
            4 -> "请求服务器过程中的异常，多为网络情况差，链路不通导致；请检查设备网络是否通畅"
            5 -> "返回的XML格式错误；可能是由于接口地址错误导致；请检查接口地址是否正常"
            6 -> "定位服务返回的定位结果解析失败；可能是由于接口返回异常导致；请检查接口返回是否正常"
            7 -> "KEY错误；请检查KEY是否绑定服务，绑定服务的SHA1码与apk签名文件的SHA1码是否一致"
            8 -> "定位初始化异常；请重新启动定位"
            9 -> "定位客户端启动失败；请检查AndroidManifest.xml文件是否配置了APSService定位服务"
            10 -> "定位时的基站、WIFI信息错误；请检查设备网络是否通畅，或尝试更换其他网络"
            11 -> "定位请求被拒绝；请检查是否在AndroidManifest.xml文件中声明了定位权限"
            12 -> "定位请求超时；请检查设备网络是否通畅，或尝试更换其他网络"
            13 -> "定位结果为空；请检查设备网络是否通畅，或尝试更换其他网络"
            14 -> "定位服务响应异常；请检查设备网络是否通畅，或尝试更换其他网络"
            15 -> "定位服务返回异常；请检查设备网络是否通畅，或尝试更换其他网络"
            16 -> "定位服务返回异常；请检查设备网络是否通畅，或尝试更换其他网络"
            18 -> "定位服务返回异常；请检查设备网络是否通畅，或尝试更换其他网络"
            19 -> "定位服务返回异常；请检查设备网络是否通畅，或尝试更换其他网络"
            else -> "未知错误码: $errorCode"
        }
    }

    /**
     * 停止定位
     */
    fun stopLocation() {
        locationClient.stopLocation()
        locationClient.onDestroy()
    }

    /**
     * 更新定位参数
     */
    fun updateLocationOption(option: AMapLocationClientOption) {
        locationClient.setLocationOption(option)
    }
}

/**
 * 定位结果密封类
 */
sealed class LocationResult {
    /**
     * 定位成功
     */
    data class Success(
        val latitude: Double,      // 纬度
        val longitude: Double,     // 经度
        val address: String?,      // 地址
        val poiName: String?,      // POI名称
        val city: String?,         // 城市
        val district: String?      // 区域
    ) : LocationResult()

    /**
     * 定位失败
     */
    data class Error(
        val errorCode: Int,
        val errorMessage: String?
    ) : LocationResult()
}