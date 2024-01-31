package com.piggy.mqttdemo.service.handler

import com.piggy.mqttdemo.event.upstream.DeviceBaseEvent


/**
 * @author LiuFei
 * @date 2023/9/19 17:19
 * 设备基础信息
 */
interface IDeviceBaseService {
    /**
     * 状态信息上报 - 0x2702
     */
    fun receive(event: DeviceBaseEvent)
}