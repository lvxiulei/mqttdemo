package com.piggy.mqttdemo.service.impl.handler

import com.alibaba.fastjson2.JSONObject
import com.piggy.mqttdemo.event.upstream.DeviceBaseEvent
import com.piggy.mqttdemo.service.handler.IDeviceBaseService
import com.piggy.mqttdemo.utils.replyDevice
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * @Author lvxiulei on 2023-09-21 11:38
 * @Description 设备基础信息上报
 * @Version V1.0
 */
@Service
class DeviceBaseServiceImpl : IDeviceBaseService {

    val log: Logger = LoggerFactory.getLogger(DeviceBaseServiceImpl::class.java)

    @EventListener
    override fun receive(event: DeviceBaseEvent) {
        val baseProtocolMessage = event.baseProtocolMessage
        val protocol = baseProtocolMessage.getData()
        log.info("解析设备基础信息结果：${JSONObject.toJSONString(protocol)}")
        // TODO 业务逻辑

        // 应答 平台通用应答 - 0xA701
        replyDevice(protocol, "0xA701")
    }


}