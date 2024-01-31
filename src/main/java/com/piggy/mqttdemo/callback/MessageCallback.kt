package com.piggy.mqttdemo.callback

import com.piggy.mqttdemo.listener.MqttMessageListener
import com.piggy.mqttdemo.utils.ByteUtils
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @Author lvxiulei on 2024-01-19 14:17
 * @Description MQTT客户端的异步事件发生时的回调
 * @Version V1.0
 */
class MessageCallback(private var mqttMessageListener: MqttMessageListener) : MqttCallback {

    private val log: Logger = LoggerFactory.getLogger(MessageCallback::class.java)

    /**
     * 丢失了服务端连接后触发该回调
     */
    override fun connectionLost(cause: Throwable?) {
        log.error("****与服务器断开连接[connectionLost]，cause：${cause?.message}")
    }

    /**
     * 应用收到消息后触发该回调
     */
    override fun messageArrived(topic: String, message: MqttMessage) {
        log.info("****应用收到消息-[messageArrived]: topic：$topic, message：${ByteUtils.getHexString(message.payload)}")
        mqttMessageListener.receiver(topic, message)
    }

    /**
     * 消息发布者发布完成触发该回调
     */
    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        log.info("****消息发布完成-[deliveryComplete], messageId=${token?.messageId}, topics=${token?.topics}")
    }
}