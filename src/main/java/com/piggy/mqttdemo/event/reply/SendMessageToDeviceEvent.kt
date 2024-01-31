package com.piggy.mqttdemo.event.reply

import com.piggy.mqttdemo.model.protocol.Protocol
import org.springframework.context.ApplicationEvent

/**
 * @Author lvxiulei on 2024-01-31 16:01
 * @Description
 * @Version V1.0
 */
class SendMessageToDeviceEvent(var protocol: Protocol, source: Any) : ApplicationEvent(source) {

}