package com.piggy.mqttdemo.analyze.input

import com.piggy.mqttdemo.analyze.InputMessage
import com.piggy.mqttdemo.model.message.DeviceReplyProtocol
import java.nio.ByteBuffer

/**
 * @Author lvxiulei on 2024-01-26 10:43
 * @Description 设备通用应答解析
 * @Version V1.0
 */
class DeviceReplyProtocolMessage(bytes: ByteArray, topic: String) : InputMessage<DeviceReplyProtocol>(bytes, topic, DeviceReplyProtocol()) {

    /**
     * 解析消息主体
     */
    override fun analyzeBody(buffer: ByteBuffer) {
        protocol.replyTxnNo = buffer.getShort(0).toUShort().toInt()
        protocol.replyMsgType = buffer.getShort(2).toUShort().toInt()
        protocol.replyCode = buffer.get(4)
        println(protocol)
    }

}