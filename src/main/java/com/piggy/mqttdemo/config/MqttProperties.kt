package com.piggy.mqttdemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*
import kotlin.collections.ArrayList

/**
 * @Author lvxiulei on 2024-01-14 22:28
 * @Description
 * @Version V1.0
 */
@ConfigurationProperties(prefix = "mqtt")
class MqttProperties {
    /**
     * 服务器地址
     */
    var serverURI: String = ""

    /**
     * 客户端ID
     */
    var clientId: String = UUID.randomUUID().toString().replace("-", "")

    /**
     * 客户端用户名
     */
    var username: String = ""

    /**
     * 客户端密码
     */
    var password: String = ""

    /**
     * 默认topic，即在建立连接后默认订阅的topic。
     */
    var topics: ArrayList<DefMqttTopic>? = null

    /**
     * MQTT连接超时时间， 默认5秒
     */
    var connectionTimeout: Int = 5

    /**
     * MQTT服务器心跳， 服务端默认30秒
     */
    var keepAliveInterval: Int = 30

    /**
     * mqtt服务器连接参数。 cleanSession或cleanStart, 默认true， 非必要请勿设置false。
     */
    var cleanSession = true

    /**
     * 重试间隔，当MQTT服务器异常断开连接后，尝试重新建立连接的间隔时间， 默认10秒。
     */
    var tryConnectInterval: Long = 1000 * 10
}

class DefMqttTopic{
    var topic: String? = null
    var qos: Int = 0
}