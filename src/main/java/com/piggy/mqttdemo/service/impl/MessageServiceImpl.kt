package com.piggy.mqttdemo.service.impl

import com.piggy.mqttdemo.analyze.input.DeviceBaseProtocolMessage
import com.piggy.mqttdemo.analyze.input.DeviceReplyProtocolMessage
import com.piggy.mqttdemo.client.MqttSendTemplate
import com.piggy.mqttdemo.event.MqttReceiveEvent
import com.piggy.mqttdemo.event.reply.DeviceReplyEvent
import com.piggy.mqttdemo.event.reply.SendMessageToDeviceEvent
import com.piggy.mqttdemo.event.upstream.DeviceBaseEvent
import com.piggy.mqttdemo.service.IMessageService
import com.piggy.mqttdemo.utils.CRC16Util
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext

/**
 * @Author lvxiulei on 2024-01-23 16:49
 * @Description 消息服务
 * @Version V1.0
 */
@Service
class MessageServiceImpl : IMessageService {

    val log: Logger = LoggerFactory.getLogger(MessageServiceImpl::class.java)

    @Autowired lateinit var context: WebApplicationContext
    @Autowired lateinit var mqttSend: MqttSendTemplate

    /**
     * 接收MQTT上行消息
     * 设备端订阅TOPIC规则：DEV/BMS/1/BT207206015KC00231023001
     * 设备端发布TOPIC规则：SVC/BMS/1/BT207206015KC00231023001/2701
     * @param event 消息接收事件
     */
    @Async("asyncInstantTaskServiceExecutor")
    @EventListener
    override fun receiveMsg(event: MqttReceiveEvent) {
        try {
            val topics = event.topic.split("/")
            if (topics.size != 5) return
            val msgType = topics[4]
            if (StringUtils.isBlank(msgType)) return
            when (topics[4]) {
                "2701" -> {  //设备通用应答 - 0x2701
                    val protocol = DeviceReplyProtocolMessage(event.message.payload, event.topic)
                    context.publishEvent(DeviceReplyEvent(protocol))
                }
                "2702" -> {  //终端登录 - 0x2702
                    val protocol = DeviceBaseProtocolMessage(event.message.payload, event.topic)
                    val crc16Arr = ByteArray(event.message.payload.size - 2)
                    System.arraycopy(event.message.payload, 0, crc16Arr, 0, crc16Arr.size)
                    val crc16 = CRC16Util.crc16(crc16Arr)
                    log.info("crc16:$crc16")
                    context.publishEvent(DeviceBaseEvent(protocol))
                }
            }
        } catch (ex: Exception) {
            log.error("处理消息异常：$event", ex)
        }
    }

    /**
     * 发送mqtt消息，
     * 通过事件方式，异步发送， ——私有协议
     */
    @Async("mqttSendTask")
    @EventListener
    override fun sendMsg(event: SendMessageToDeviceEvent) {
        val topic = "DEV/BMS/1/${event.protocol.deviceId}"
        mqttSend.sendMsg(event.protocol.bytes, topic)
    }

}