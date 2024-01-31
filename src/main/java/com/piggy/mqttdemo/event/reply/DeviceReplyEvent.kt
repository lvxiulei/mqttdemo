package com.piggy.mqttdemo.event.reply

import com.piggy.mqttdemo.analyze.input.DeviceReplyProtocolMessage
import org.springframework.context.ApplicationEvent

/**
 * @Author lvxiulei on 2024-01-27 21:32
 * @Description
 * @Version V1.0
 */
open class MessageHandlerEvent(source: String): ApplicationEvent(source) {}

class DeviceReplyEvent(var replyMessage: DeviceReplyProtocolMessage): MessageHandlerEvent("message_receiver") {


}