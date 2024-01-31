package com.piggy.mqttdemo.utils

import org.slf4j.LoggerFactory
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * @Author lvxiulei on 2024-01-23 16:59
 * @Description 坐标转换为火星坐标 高德API（https://lbs.amap.com/api/webservice/guide/api/convert）
 * @Version V1.0
 */
object GPSUtils {

    private val log = LoggerFactory.getLogger(GPSUtils::class.java)

    /**
     * 椭球参数
     */
    private const val pi = 3.14159265358979324
    
    /**
     * 卫星椭球坐标投影到平面地图坐标系的投影因子
     */
    private const val a = 6378245.0
    
    /**
     * 椭球的偏心率
     */
    private const val ee = 0.00669342162296594323
    
    /**
     * 经纬度 GPS转高德
     *
     * @param wgLon GPS经度
     * @param wgLat GPS维度
     * @return 转化后的经纬度坐标
     */
    fun transform(wgLon: Double, wgLat: Double): AMap {
        if (outOfChina(wgLat, wgLon)) {
            return AMap(wgLon, wgLat)
        }
        var dLat = transformLat(wgLon - 105.0, wgLat - 35.0)
        var dLon = transformLon(wgLon - 105.0, wgLat - 35.0)
        val radLat = wgLat / 180.0 * pi
        var magic = sin(radLat)
        magic = 1 - ee * magic * magic
        val sqrtMagic = sqrt(magic)
        dLat = dLat * 180.0 / (a * (1 - ee) / (magic * sqrtMagic) * pi)
        dLon = dLon * 180.0 / (a / sqrtMagic * cos(radLat) * pi)
        val transLat = wgLat + dLat
        val transLon = wgLon + dLon
        return AMap(transLon, transLat)
    }
    
    /**
     * 判断是否为国外坐标，，不在国内不做偏移
     *
     * @param lat
     * @param lon
     * @return
     */
    private fun outOfChina(lat: Double, lon: Double): Boolean {
        if (lon < 72.004 || lon > 137.8347) return true
        return if (lat < 0.8293 || lat > 55.8271) true else false
    }
    
    /**
     * 纬度转换
     *
     * @param x
     * @param y
     * @return
     */
    private fun transformLat(x: Double, y: Double): Double {
        var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * sqrt(abs(x))
        ret += (20.0 * sin(6.0 * x * pi) + 20.0 * sin(2.0 * x * pi)) * 2.0 / 3.0
        ret += (20.0 * sin(y * pi) + 40.0 * sin(y / 3.0 * pi)) * 2.0 / 3.0
        ret += (160.0 * sin(y / 12.0 * pi) + 320 * sin(y * pi / 30.0)) * 2.0 / 3.0
        return ret
    }
    
    /**
     * 经度转换
     *
     * @param x
     * @param y
     * @return
     */
    private fun transformLon(x: Double, y: Double): Double {
        var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * sqrt(abs(x))
        ret += (20.0 * sin(6.0 * x * pi) + 20.0 * sin(2.0 * x * pi)) * 2.0 / 3.0
        ret += (20.0 * sin(x * pi) + 40.0 * sin(x / 3.0 * pi)) * 2.0 / 3.0
        ret += (150.0 * sin(x / 12.0 * pi) + 300.0 * sin(x / 30.0 * pi)) * 2.0 / 3.0
        return ret
    }
    
    /**
     * 高德经纬度类
     */
    class AMap(
        /**
         * 经度
         */
        var longitude: Double,
        /**
         * 维度
         */
        var latitude: Double
    ) {}
    
//    fun main() {
//        val lon = 108.766167
//        val lat = 34.207948
//        val aMap = transform(lon, lat)
//        // 108.766167,34.207948
//        println("GPS转高德之前：$lon,$lat")
//        // 108.77088779593853,34.206482360676944
//        println("GPS转高德之后：" + aMap.longitude + "," + aMap.latitude)
//        /**
//         * 108.77090467665,34.206496310764
//         * 高德API（https://lbs.amap.com/api/webservice/guide/api/convert）经纬度转换之后.
//         * 两者误差不是很大
//         */
//    }
}