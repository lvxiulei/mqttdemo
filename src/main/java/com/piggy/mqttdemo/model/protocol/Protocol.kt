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
    open lateinit var topic: String  // 消息主题
    open lateinit var deviceId: String  // 设备ID
    open var msgType by Delegates.notNull<Short>() // 消息类型 对应协议的消息ID(示例：0x2704)
    open var txnNo by Delegates.notNull<Short>()   // 消息流水号
    var protocolVersion: Int = 1  // 协议版本号

    override fun toString(): String {
        return "Protocol(" +
                "bytes=${ByteUtils.arr2HexStr(bytes, false)}, " +
                "topic='$topic', " +
                "deviceId='$deviceId', " +
                "protocolVersion=$protocolVersion, " +
                "msgType=$msgType, " +
                "txnNo=$txnNo)"
    }

}