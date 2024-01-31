package com.piggy.mqttdemo.service.impl.handler

import com.piggy.mqttdemo.event.reply.DeviceReplyEvent
import com.piggy.mqttdemo.service.handler.IDeviceReplyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * @Author lvxiulei on 2024-01-27 21:53
 * @Description
 * @Version V1.0
 */
@Service
class DeviceReplyServiceImpl : IDeviceReplyService {

    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @EventListener
    fun deviceReply(event: DeviceReplyEvent) {
        val replyMessage = event.replyMessage
        val replyProtocol = replyMessage.getData()
        log.info("[receive]设备通用应答信息上报 - 0x2701 :{}", replyProtocol.toString())

        // TODO 应答

    }


}