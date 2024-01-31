package com.piggy.mqttdemo.event

import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.context.ApplicationEvent

/**
 * @Author lvxiulei on 2024-01-21 20:17
 * @Description 接收MQTT消息事件，spring event。
 * @Version V1.0
 */
class MqttReceiveEvent(var topic: String, var message: MqttMessage) : ApplicationEvent("mqtt_receive") {

}