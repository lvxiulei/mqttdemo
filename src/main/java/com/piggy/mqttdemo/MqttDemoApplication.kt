package com.piggy.mqttdemo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @Author lvxiulei on 2024-01-10 00:05
 * @Description MQTT
 * @Version V1.0
 */

fun main(args: Array<String>) {
    runApplication<MqttDemoApplication>(*args)
}

@EnableScheduling
@SpringBootApplication
class MqttDemoApplication : ApplicationRunner {
    val log: Logger = LoggerFactory.getLogger(MqttDemoApplication::class.java)

    @Value("\${server.port}")
    var port: Int = 0

    @OptIn(ExperimentalStdlibApi::class)
    override fun run(args: ApplicationArguments?) {
        log.info("\n" +
                "███╗   ███╗ ██████╗ ████████╗████████╗██████╗ ███████╗███╗   ███╗ ██████╗ \n" +
                "████╗ ████║██╔═══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝████╗ ████║██╔═══██╗\n" +
                "██╔████╔██║██║   ██║   ██║      ██║   ██║  ██║█████╗  ██╔████╔██║██║   ██║\n" +
                "██║╚██╔╝██║██║▄▄ ██║   ██║      ██║   ██║  ██║██╔══╝  ██║╚██╔╝██║██║   ██║\n" +
                "██║ ╚═╝ ██║╚██████╔╝   ██║      ██║   ██████╔╝███████╗██║ ╚═╝ ██║╚██████╔╝\n")
        log.info("127.0.0.1:${port}")
        val bytes = byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte())
        val s = bytes.toHexString()
        log.info("HexString:$s")
    }


}