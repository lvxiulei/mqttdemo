package com.piggy.mqttdemo.model.protocol

import com.piggy.mqttdemo.utils.ByteUtils
import kotlin.properties.Delegates

/**
 * @Author lvxiulei on 2024-01-26 10:22
 * @Description 协议
 * @Version V1.0
 */
open class Protocol {

    lateinit var bytes: ByteArray // payload
    lateinit var topic: String  // 消息主题
    lateinit var deviceId: String  // 设备ID
    var protocolVersion: Int = 1  // 协议版本号
    var msgType by Delegates.notNull<Short>() // 消息类型 对应协议的消息ID(0x2704)
    var msgId by Delegates.notNull<Short>()   // 消息流水号

    override fun toString(): String {
        return "Protocol(" +
                "bytes=${ByteUtils.arr2HexStr(bytes, false)}, " +
                "topic='$topic', " +
                "deviceId='$deviceId', " +
                "protocolVersion=$protocolVersion, " +
                "msgType=$msgType, " +
                "msgId=$msgId)"
    }

}