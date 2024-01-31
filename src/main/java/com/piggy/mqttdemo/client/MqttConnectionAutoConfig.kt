package com.piggy.mqttdemo.client

import com.piggy.mqttdemo.config.MqttProperties
import com.piggy.mqttdemo.event.MqttReceiveEvent
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executor

/**
 * @Author lvxiulei on 2024-01-19 15:05
 * @Description
 * @Version V1.0
 */
@ConditionalOnClass(Executor::class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MqttProperties::class)
class MqttConnectionAutoConfig {

    @Bean
    fun mqttConnection(mqttProperties: MqttProperties, context: ApplicationContext): MqttConnection {
        val mqttConsumerConnect = MqttConnection(mqttProperties)
        mqttConsumerConnect.setMqttMessageListener { topic, message ->
            context.publishEvent(MqttReceiveEvent(topic, message))
        }
        mqttConsumerConnect.start()
        return mqttConsumerConnect
    }

    @Bean
    fun mqttSendTemplate(mqttConnection: MqttConnection) : MqttSendTemplate {
        return MqttSendTemplate(mqttConnection)
    }

}