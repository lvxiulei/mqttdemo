package com.piggy.mqttdemo.utils

import com.piggy.mqttdemo.analyze.output.ServiceReplyMessage
import com.piggy.mqttdemo.config.ApplicationContextProvider
import com.piggy.mqttdemo.event.reply.SendMessageToDeviceEvent
import com.piggy.mqttdemo.model.message.ServiceReplyProtocol
import com.piggy.mqttdemo.model.protocol.Protocol
import java.nio.ByteBuffer

/**
 * 校验CRC
 */
fun checkCrc(buffer: ByteBuffer): Boolean {
    val b = ByteArray(buffer.array().size - 2)
    System.arraycopy(buffer.array(), 0, b, 0, b.size)
    val crc16 = CRC16Util.int2Bytes(CRC16Util.crc16(b))
    val uploadCrc = byteArrayOf(buffer.get(buffer.array().size - 2), buffer.get(buffer.array().size - 1))
    return crc16.contentEquals(uploadCrc)
}

/**
 * 响应设备应答
 */
fun replyDevice(protocol: Protocol, source: String) {
    val replyMsg = ServiceReplyMessage(
        ServiceReplyProtocol(
            protocol.txnNo,
            protocol.msgType,
            0x01,
            protocol.msgType,
            protocol.txnNo,
            protocol.deviceId
        )
    )
    val event = SendMessageToDeviceEvent(replyMsg.getData(), source)
    ApplicationContextProvider.applicationContext!!.publishEvent(event)
}
