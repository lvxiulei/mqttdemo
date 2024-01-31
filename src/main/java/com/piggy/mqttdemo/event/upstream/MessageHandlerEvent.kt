package com.piggy.mqttdemo.event.upstream

import com.piggy.mqttdemo.analyze.input.DeviceBaseProtocolMessage
import com.piggy.mqttdemo.analyze.input.DeviceReplyProtocolMessage
import org.springframework.context.ApplicationEvent

/**
 * @Author lvxiulei on 2023-09-21 09:23
 * @Description 上行消息事件处理
 * @Version V1.0
 */
open class MessageHandlerEvent(source: String) : ApplicationEvent(source) {}

/**
 * 设备通用应答解析事件 0x2701
 */
class DeviceReplyEvent(var reply: DeviceReplyProtocolMessage) : MessageHandlerEvent("message_receiver") {

}

/**
 * 终端登录 设备基础信息事件- 0x2702
 */
class DeviceBaseEvent(var baseProtocolMessage: DeviceBaseProtocolMessage): MessageHandlerEvent("message_receiver") {

}
