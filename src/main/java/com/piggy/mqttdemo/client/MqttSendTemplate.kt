package com.piggy.mqttdemo.client

import com.piggy.mqttdemo.utils.ByteUtils
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @Author lvxiulei on 2024-01-21 20:25
 * @Description MQTT发送消息类
 * @Version V1.0
 */
class MqttSendTemplate(private var mqttConnection: MqttConnection) {
    private val log: Logger = LoggerFactory.getLogger(MqttSendTemplate::class.java)

    /**
     * 发送MQTT消息，同步调用。
     * @param msg 消息主体，字符串类型，在发送时会按 Charsets.UTF_8 编码类型转为字节流。
     * @param topic 消息主题
     * @param qos 服务等级 默认0
     * @see Charsets.UTF_8
     */
    fun sendMsg(msg: String, topic: String, qos: Int = 0) {
        val mqttMessage = MqttMessage()
        mqttMessage.payload = msg.toByteArray(Charsets.UTF_8)
        mqttMessage.qos = qos
        if (mqttConnection._mqttClient == null) return
        mqttConnection._mqttClient!!.publish(topic, mqttMessage)
        log.info("MQTT_SEND: [topic: $topic qos: $qos] --> msg:$msg")
    }

    /**
     * 发送MQTT消息，同步调用。
     * @param data 消息主体 byte[]
     * @param topic 消息主题
     * @param qos 服务等级 默认0
     */
    @OptIn(ExperimentalStdlibApi::class)
    fun sendMsg(data: ByteArray, topic: String, qos: Int = 0) {
        val message = MqttMessage()
        message.qos = qos
        message.payload = data
        if (mqttConnection._mqttClient == null) return
        mqttConnection._mqttClient!!.publish(topic, message)
        log.info("MQTT_SEND: [topic: $topic qos: $qos] --> msg:${ByteUtils.getHexString(data)}")
    }

}