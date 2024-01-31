package com.piggy.mqttdemo.exception

import com.piggy.mqttdemo.utils.ByteUtils
import com.piggy.mqttdemo.utils.CRC16Util
import java.io.IOException

/**
 * @Author lvxiulei on 2024-01-26 10:21
 * @Description 消息解析/封装
 * @Version V1.0
 */
class CRC16CheckException(byte: ByteArray) : IOException(buildErrorMsg(byte)) {

}

fun buildErrorMsg(byte: ByteArray): String{
    val byteStr = ByteUtils.arr2HexStr(byte, false)
    val data = ByteArray(byte.size - 2)
    System.arraycopy(byte, 0, data, 0, data.size)
    val code = CRC16Util.appendCrc16Iot(data)
    val dataCode = ByteArray(2)
    dataCode[0] = byte[byte.size - 2]
    dataCode[1] = byte[byte.size - 1]
    return "消息校验异常：CheckCRC16: ${ByteUtils.arr2HexStr(code, false)}, " +
            "DataCRC16: ${ByteUtils.arr2HexStr(dataCode, false)} " +
            "msg = $byteStr"
}