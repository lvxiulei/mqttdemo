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
 */
open class ServiceReplyProtocol: OutMessageProtocol() {
    var replyMsgId: Short = 0                   //应答消息ID
    var replyMsgType: Short = 0x00              //应答消息类型
    var replyCode: Byte = 0x01                  //应答结果码
}