package com.piggy.mqttdemo.analyze.input

import com.piggy.mqttdemo.analyze.InputMessage
import com.piggy.mqttdemo.model.message.DeviceBaseProtocol
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

/**
 * @author lvxiulei
 * @date 2024/1/29 10:37
 * 终端登录（设备基础信息）- 0x2702
 */
class DeviceBaseProtocolMessage(bytes: ByteArray, topic: String) : InputMessage<DeviceBaseProtocol>(bytes, topic, DeviceBaseProtocol()) {

    val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun analyzeBody(buffer: ByteBuffer) {
        val bytes = buffer.array()
        protocol.replyTxnNo = buffer.getShort(0).toUShort().toInt()
        protocol.batteryManufacturerId = buffer.get(2)
        protocol.bmsManufacturerId = buffer.get(3)
        protocol.hardwareVersion = String(bytes, 4, 8, Charsets.US_ASCII)
        protocol.softVersion = String(bytes, 12, 8, Charsets.US_ASCII)
        protocol.imei = String(bytes, 20, 15, Charsets.US_ASCII)
        protocol.iccid = String(bytes, 35, 20, Charsets.US_ASCII)
        protocol.imsi = String(bytes, 55, 15, Charsets.US_ASCII)
        protocol.configId = buffer.getShort(70).toUShort().toShort()
        protocol.supportInfo = buffer.getShort(72).toUShort().toShort()
        protocol.versionCode = buffer.getShort(74).toUShort().toShort()
        protocol.bmsSoftVersion = String(bytes, 76, 8, Charsets.US_ASCII)
        protocol.bmsHardwareVersion = String(bytes, 84, 8, Charsets.US_ASCII)
        protocol.bmsCode = String(bytes, 92, 32, Charsets.US_ASCII)
    }

}