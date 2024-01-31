package com.piggy.mqttdemo.utils

/**
 * @Author lvxiulei on 2024-01-23 16:59
 * @Description
 * @Version V1.0
 */
object BitUtils {

    /**
     * 把字节数组转为相应的二进制表示
     * 示例:[1,2] -> 0000000100000010
     * @param byteArray 字节数组
     * @return String 二进制串
     */
    fun byteArrayToBinary(byteArray: ByteArray?): String {
        if (byteArray == null) return ""
        val binaryStringBuilder = StringBuilder()
        val totalBits = byteArray.size * 8
        for (byte in byteArray) {
            val binaryString = Integer.toBinaryString(byte.toInt() and 0xFF)
            val paddedBinaryString = binaryString.padStart(8, '0')
            binaryStringBuilder.append(paddedBinaryString)
        }
        val binaryString = binaryStringBuilder.toString()
        return binaryString.padStart(totalBits, '0')
    }

    /**
     * 把数字转为相应的二进制表示
     * 示例:1 -> 00000001
     * @param num 数字
     * @return String 二进制串
     */
    fun intToBinary(num: Int?): String {
        if (num == null) return ""
        return num.toString(2).padStart(8, '0')
    }
}