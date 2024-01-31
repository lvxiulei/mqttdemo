package com.piggy.mqttdemo.listener

import org.eclipse.paho.client.mqttv3.MqttMessage

/**
 * @Author lvxiulei on 2024-01-21 19:16
 * @Description MQTT消息监听器
 * @Version V1.0
 */
@FunctionalInterface
fun interface MqttMessageListener {

    /**
     * 接收消息
     * @param topic 消息主题
     * @param message MQTT消息主体
     */
    fun receiver(topic: String, message: MqttMessage)

}