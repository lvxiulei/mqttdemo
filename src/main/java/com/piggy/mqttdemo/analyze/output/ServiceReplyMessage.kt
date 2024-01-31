package com.piggy.mqttdemo.analyze.output

import com.piggy.mqttdemo.analyze.OutPutMessage
import com.piggy.mqttdemo.model.message.ServiceReplyProtocol
import java.nio.ByteBuffer

/**
 * @Author lvxiulei on 2024-01-31 15:41
 * @Description
 * @Version V1.0
 */
class ServiceReplyMessage(protocol: ServiceReplyProtocol) : OutPutMessage<ServiceReplyProtocol>(protocol) {
    private val length = 5

    companion object {
        fun createMsg(clientId: String, replyCode: Byte, replyMsgId: Short, replyType: Short): ServiceReplyMessage {
            val replyProtocol = ServiceReplyProtocol()
            replyProtocol.replyCode = replyCode
            replyProtocol.replyMsgId = replyMsgId
            replyProtocol.replyMsgType = replyType
            replyProtocol.deviceId = clientId
            return ServiceReplyMessage(replyProtocol)
        }
    }

    override fun buildBody(protocol: ServiceReplyProtocol): ByteArray {
        val buffer = ByteBuffer.allocate(length)
        buffer.putShort(protocol.replyMsgId)
        buffer.putShort(protocol.replyMsgType)
        buffer.put(protocol.replyCode)
        return buffer.array()
    }
}