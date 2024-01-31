package com.piggy.mqttdemo.analyze

import com.piggy.mqttdemo.exception.CRC16CheckException
import com.piggy.mqttdemo.model.protocol.Protocol
import com.piggy.mqttdemo.utils.MessageIDUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.Serializable
import java.nio.ByteBuffer

/**
 * @Author lvxiulei on 2024-01-26 10:21
 * @Description 消息解析/封装
 * @Version V1.0
 */
@FunctionalInterface
interface Message<T : Protocol> : Serializable {
    fun getData(): T
}

/**
 * 输入消息， 数据流 ——> 对象
 */
abstract class InputMessage<T : Protocol>(private var bytes: ByteArray, protected var topic: String, protected var protocol: T) : Message<T> {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    init {
        analyzeTopic()
    }

    override fun getData(): T {
        protocol.bytes = bytes
        analyze()
        return protocol
    }

    /**
     * 解析topic
     */
    private fun analyzeTopic() {
        log.info("解析TOPIC:$topic")
        val splits = topic.split("/")
        protocol.topic = topic
        protocol.protocolVersion = splits[2].toInt()
        protocol.deviceId = splits[3]
    }

    /**
     * 解析
     */
    private fun analyze() {
        val buffer = ByteBuffer.wrap(bytes)
//        if (bytes[0].toInt() != 0x37) { //0x37命令不校验crc16
//            if (!checkCrc(buffer)) throw CRC16CheckException(bytes)  //crc 校验失败，抛出异常
//        }
        analyzeHeader(buffer)
        val bodyBytes = ByteArray(bytes.size - 4)
        System.arraycopy(bytes, 4, bodyBytes, 0, bodyBytes.size)
        analyzeBody(ByteBuffer.wrap(bodyBytes))
    }

    /**
     * 解析消息头
     */
    private fun analyzeHeader(buffer: ByteBuffer) {
        protocol.msgType = buffer.getShort(0)
        protocol.msgId = buffer.getShort(2)
    }

    /**
     * 解析消息主体
     */
    protected abstract fun analyzeBody(buffer: ByteBuffer)

}

/**
 * 输出消息， 对象 ——> 数据流
 */
abstract class OutPutMessage<T: Protocol>(protected var protocol: T) : Message<T> {
    lateinit var bytes: ByteArray

    override fun getData(): T {
        buildMsg()
        return protocol
    }

    private fun buildMsg() {
        val body = buildBody(protocol)
        ByteBuffer.allocate(body.size + 4)


    }

    private fun buildHeader(buffer: ByteBuffer) {
        buffer.putShort(protocol.msgType)
        val msgId = MessageIDUtils.getInstances().getMessageId(protocol.deviceId)
        buffer.putShort(msgId)
    }

    protected abstract fun buildBody(protocol: T): ByteArray
}








