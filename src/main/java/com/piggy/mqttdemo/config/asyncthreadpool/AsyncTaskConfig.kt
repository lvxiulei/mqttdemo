package com.piggy.mqttdemo.config.asyncthreadpool

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

/**
 * @Author lvxiulei on 2024-01-31 13:41
 * @Description
 * @Version V1.0
 */
@Configuration
@EnableAsync
class AsyncTaskConfig {
    private val log: Logger = LoggerFactory.getLogger(AsyncTaskConfig::class.java)

    @Bean
    fun asyncInstantTaskServiceExecutor(): Executor {
        //高速处理通道，适用于即时性强的任务处理
        log.info("start asyncInstantTaskServiceExecutor----------------（高速通道）")
        val executor = ThreadPoolTaskExecutor()
        //配置核心线程数
        var cpuCore = Runtime.getRuntime().availableProcessors()
        log.info("CPU数量：{}", cpuCore)
        if (cpuCore == 0) cpuCore = 30
        val corePoolSize = cpuCore * 30 + 1
        executor.corePoolSize = corePoolSize
        //配置最大线程数
        executor.maxPoolSize = corePoolSize * 2
        //配置队列大小
        executor.queueCapacity = 200
        //配置线程池中的线程的名称前缀
        //配置线程池中的线程的名称前缀
        executor.threadNamePrefix = "instant-async-task_"
        //空闲线程回收时间
        executor.keepAliveSeconds = 120 * 1000
        executor.setThreadGroupName("instantAsyncTaskGroup")
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        //丢弃无法处理的任务,并且抛出异常。
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.AbortPolicy())
        //执行初始化
        executor.initialize()
        return executor
    }

    @Bean
    fun asyncLazyTaskServiceExecutor(): Executor {
        //低速处理通道， 适用于非即时性批量操作。
        log.info("start asyncLazyTaskServiceExecutor----------------（低速）")
        val executor = ThreadPoolTaskExecutor()
        //配置核心线程数
        val cpuCore = Runtime.getRuntime().availableProcessors()
        log.info("CPU数量：{}", cpuCore)
        var core = 4
        if (cpuCore > 4) {
            core = cpuCore / 2
        }
        executor.corePoolSize = core
        //配置最大线程数
        executor.maxPoolSize = core * 8 + 1
        //配置队列大小
        executor.queueCapacity = 100000
        //配置线程池中的线程的名称前缀
        executor.threadNamePrefix = "lazy-async-task_"
        //空闲线程回收时间
        executor.keepAliveSeconds = 120 * 1000
        executor.setThreadGroupName("lazyAsyncTaskGroup")
        //允许回收核心线程
        executor.setAllowCoreThreadTimeOut(true)
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        // 丢弃无法处理的任务,并且抛出异常。
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.AbortPolicy())
        //执行初始化
        executor.initialize()
        return executor
    }

    @Bean
    fun asyncUnCompleteOrderTaskServiceExecutor(): Executor {
        //高速处理通道，适用于即时性强的任务处理
        log.info("start asyncUnCompleteOrderTaskServiceExecutor----------------（处理未完成订单专用线程池, 配置核心：4， 最大：8  队列容量：200）")
        val executor = ThreadPoolTaskExecutor()
        val corePoolSize = 4
        executor.corePoolSize = corePoolSize
        //配置最大线程数
        executor.maxPoolSize = corePoolSize * 2
        //配置队列大小
        executor.queueCapacity = 200
        //配置线程池中的线程的名称前缀
        executor.threadNamePrefix = "uncompleted-order_"
        //空闲线程回收时间
        executor.keepAliveSeconds = 60 * 1000
        executor.setThreadGroupName("uncompletedOrderAsyncTaskGroup")
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        //丢弃无法处理的任务,并且抛出异常。
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.AbortPolicy())
        //执行初始化
        executor.initialize()
        return executor
    }

    /**
     * 默认 核心线程4， 最大线程200， 队列大小0. 满足小任务短耗时，大并发，强即时性。
     * 策略： 队列大小0，直接开启新线程执行发送消息。  最大线程200个。 回收时间60秒，池无法处理时抛出异常，mqtt消息发送失败。
     */
    @Bean
    fun mqttSendTask(): Executor {
        //高速处理通道，适用于即时性强的任务处理
        log.info("start mqttSendTask ----------------（初始MQTT异步任务线程池）")
        val executor = ThreadPoolTaskExecutor()
        //配置核心线程数
        executor.corePoolSize = 4
        //配置最大线程数
        executor.maxPoolSize = 200
        //配置队列大小
        executor.queueCapacity = 0
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("mqtt-send-")
        //空闲线程回收时间
        executor.keepAliveSeconds = 60
        executor.setAllowCoreThreadTimeOut(true)
        executor.setThreadGroupName("mqtt-send-task")
        log.info(
            "[{}] - core:{}, max:{}, queuSize:{}, threadName:{}",
            Objects.requireNonNull(executor.threadGroup).name,
            executor.corePoolSize,
            executor.maxPoolSize,
            executor.queueSize,
            executor.threadNamePrefix
        )
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.AbortPolicy())
        //执行初始化
        executor.initialize()
        return executor
    }

}