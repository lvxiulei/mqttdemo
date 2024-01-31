package com.piggy.mqttdemo.client

import com.piggy.mqttdemo.callback.MessageCallback
import com.piggy.mqttdemo.config.MqttProperties
import com.piggy.mqttdemo.listener.MqttMessageListener
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.*

/**
 * @Author lvxiulei on 2024-01-10 00:10
 * @Description Emq连接类
 * @Version V1.0
 */
class MqttConnection(private val mqttProperties: MqttProperties) {

    companion object {
        // 1未建立连接 2准备建立连接 3已建立连接 10连接错误
        private const val STATE_NOT_CONNECTED = 1
        private const val STATE_CURRENT_CONNECTION = 2
        private const val STATE_CONNECTED = 3
        private const val STATE_ERROR = 10
    }

    private val log: Logger = LoggerFactory.getLogger(MqttConnection::class.java)

    var _mqttClient: IMqttClient? = null
    private var state = STATE_NOT_CONNECTED
    private var mqttMessageListener: MqttMessageListener? = null
    private val mqttConnectGuardExecutor: ScheduledExecutorService = ScheduledThreadPoolExecutor(1)
    private val mqttConnectThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor { runnable -> Thread(runnable, "mqtt-connection") }

    init {
        log.info("MQTT：准备建立连接...")
        mqttConnectGuardExecutor.scheduleAtFixedRate(
            { start() },
            mqttProperties.tryConnectInterval,
            mqttProperties.tryConnectInterval,
            TimeUnit.MILLISECONDS
        )
    }

    /**
     * 建立连接
     */
    private fun connect() {
        var tryReconnect = false
        val serverURI = mqttProperties.serverURI
        val clientId = mqttProperties.clientId
        if (state == STATE_CURRENT_CONNECTION || state == STATE_CONNECTED) return
        if (state == STATE_ERROR) {
            tryReconnect = true
            log.info("MQTT：尝试从错误状态恢复，当前：state：$state, host: $serverURI, clientId: $clientId")
        }
        state = STATE_CURRENT_CONNECTION
        try {
            // 连接项设置
            val connectOptions = MqttConnectOptions()
            connectOptions.userName = mqttProperties.username
            connectOptions.password = mqttProperties.password.toCharArray()
            connectOptions.connectionTimeout = mqttProperties.connectionTimeout
            connectOptions.keepAliveInterval = mqttProperties.keepAliveInterval
            connectOptions.isCleanSession = mqttProperties.cleanSession
            // 初始化client
            val mqttClient = MqttClient(serverURI, clientId, MemoryPersistence())
            _mqttClient = mqttClient
            mqttClient.setCallback(MessageCallback(mqttMessageListener!!))
            mqttClient.connect(connectOptions)
            if (!mqttClient.isConnected) {
                log.info("MQTT：连接broker失败...")
                return
            }
            state = STATE_CONNECTED
            if (!mqttProperties.topics.isNullOrEmpty()) {
                for (defTopic in mqttProperties.topics!!) {
                    mqttClient.subscribe(defTopic.topic, defTopic.qos)
                }
            }
            log.info("MQTT：已连接 {}", mqttProperties.serverURI)
            if (tryReconnect) log.info("MQTT：已从错误状态恢复- state：$state -- host=${mqttProperties.serverURI}")
        } catch (ex: Exception) {
            state = STATE_ERROR
            log.error(ex.message, ex)
        }
    }

    fun setMqttMessageListener(mqttMessageListener: MqttMessageListener?) {
        this.mqttMessageListener = mqttMessageListener
    }

    fun start() {
        mqttConnectThreadExecutor.execute { connect() }
    }

    /**
     * 订阅主题-多个主题分别设置QOS
     * @param topicFilters 主题数组
     * @param qosArray 服务质量数组
     */
    fun subscribe(topicFilters: Array<String>?, qosArray: IntArray?) {
        _mqttClient!!.subscribe(topicFilters, qosArray)
    }

    /**
     * 订阅主题-多个主题共享QOS
     * @param topicFilters 主题数组
     * @param qos 服务质量
     */
    fun subscribe(topicFilters: Array<String>, qos: Int) {
        val qosArray = IntArray(topicFilters.size)
        for (i in topicFilters.indices) {
            qosArray[i] = qos
        }
        _mqttClient!!.subscribe(topicFilters, qosArray)
    }

    /**
     * 单个主题订阅
     * @param topicFilter 主题
     * @param qos 服务质量
     */
    fun subscribe(topicFilter: String, qos: Int) {
        _mqttClient!!.subscribe(topicFilter, qos)
    }
}