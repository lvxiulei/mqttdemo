package com.piggy.mqttdemo.model.message

import com.piggy.mqttdemo.model.protocol.Protocol

/**
 * @Author lvxiulei on 2024-01-26 10:33
 * @Description
 * @Version V1.0
 */
open class InputMessageProtocol : Protocol() {}

class DeviceReplyProtocol: InputMessageProtocol() {
    var replyTxnNo: Int = 0                   //应答消息ID
    var replyMsgType: Int = 0x00               //应答消息类型
    var replyCode: Byte = 0x00                  //应答结果码
}

/**
 * 终端登录（设备基础信息）- 0x2702
 * 0x2702
 */
open class DeviceBaseProtocol : InputMessageProtocol() {
    var replyTxnNo: Int = 0x00                  // 应答消息ID
    var batteryManufacturerId: Byte = 0x00      // 制造商标识 电池厂商标记
    var bmsManufacturerId: Byte = 0x00          // 制造商标识 保护板厂商标记
    var hardwareVersion: String = ""            // 设备的硬件版本信息
    var softVersion: String = ""                // 设备的软件版本
    var imei: String = ""                       // 设备IMEI号码
    var iccid: String = ""                      // 设备ICCID编码
    var imsi: String = ""                       // IMSI编码
    var configId: Short = 0x00                  // 设备当前的配置ID
    var supportInfo: Short = 0x00               // 设备支持信息（预留）
    var versionCode: Short = 0x00               // 协议版本编码，当前版本为1
    var versionName: String? = null             // 协议版本名称
    var bmsSoftVersion: String = ""             // BMS软件版本
    var bmsHardwareVersion: String = ""         // BMS硬件版本
    var bmsCode: String = ""                    // BMS编码
    override fun toString(): String {
        return "DeviceBaseProtocol(replyTxnNo=$replyTxnNo, batteryManufacturerId=$batteryManufacturerId, bmsManufacturerId=$bmsManufacturerId, hardwareVersion='$hardwareVersion', softVersion='$softVersion', imei='$imei', iccid='$iccid', imsi='$imsi', configId=$configId, supportInfo=$supportInfo, versionCode=$versionCode, versionName=$versionName, bmsSoftVersion='$bmsSoftVersion', bmsHardwareVersion='$bmsHardwareVersion', bmsCode='$bmsCode')"
    }
}