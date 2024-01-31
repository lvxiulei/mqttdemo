package com.piggy.mqttdemo.service

import com.piggy.mqttdemo.event.MqttReceiveEvent
import com.piggy.mqttdemo.event.reply.SendMessageToDeviceEvent

/**
 * @Author lvxiulei on 2024-01-23 16:48
 * @Description 消息服务
 * @Version V1.0
 */
interface IMessageService {

    /**
     * 接收MQTT上行消息
     * @param event 消息接收事件
     */
    fun receiveMsg(event: MqttReceiveEvent)

    /**
     * 发送MQTT下行消息
     * @param event 消息发送事件
     */
    fun sendMsg(event: SendMessageToDeviceEvent)
}