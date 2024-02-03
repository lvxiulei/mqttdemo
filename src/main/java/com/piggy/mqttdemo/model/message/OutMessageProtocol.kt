package com.piggy.mqttdemo.model.message

import com.piggy.mqttdemo.model.protocol.Protocol

/**
 * @Author lvxiulei on 2024-01-26 10:42
 * @Description 下行消息
 * @Version V1.0
 */
open class OutMessageProtocol: Protocol() {

}

/**
 * 服务器应答
 * @param replyTxnNo 消息流水号
 * @param replyMsgType 应答指令类型，应答的服务器端消息对应的消息类型
 * @param replyCode 应答指令的结果，仅当结果0x01时代表成功，其他为具体消息定义的错误码
 */
open class ServiceReplyProtocol(
    var replyTxnNo: Short,
    var replyMsgType: Short,
    var replyCode: Byte,
    override var msgType: Short,
    override var txnNo: Short,
    override var deviceId: String) : OutMessageProtocol()