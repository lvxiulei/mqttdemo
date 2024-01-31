package com.piggy.mqttdemo.timer

import jakarta.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component

/**
 * @Author lvxiulei on 2024-01-23 22:36
 * @Description
 * @Version V1.0
 */
@Component
class ThreadPoolTaskStatistics {
    val log: Logger = LoggerFactory.getLogger(ThreadPoolTaskStatistics::class.java)

    @Resource(name = "asyncInstantTaskServiceExecutor") lateinit var asyncInstantTaskServiceExecutor: ThreadPoolTaskExecutor
    @Resource(name = "asyncLazyTaskServiceExecutor") lateinit var asyncLazyTaskServiceExecutor: ThreadPoolTaskExecutor
    @Resource(name = "asyncUnCompleteOrderTaskServiceExecutor") lateinit var asyncUnCompleteOrderTaskServiceExecutor: ThreadPoolTaskExecutor

    @Scheduled(cron = "0/2 * * * * ? ")   //每隔两秒钟扫描线程池资源消耗情况
    fun taskLog() {
        val sb = StringBuilder("[TASK_STATISTICS] 线程池资源使用提醒：\n")
        val poolList = arrayListOf<ThreadPoolTaskInfo>()
        poolList.add(ThreadPoolTaskInfo(asyncInstantTaskServiceExecutor, "InstantTask", "高速线程池"))
        poolList.add(ThreadPoolTaskInfo(asyncLazyTaskServiceExecutor, "LazyTask", "低速大容量线程池"))
        poolList.add(ThreadPoolTaskInfo(asyncUnCompleteOrderTaskServiceExecutor, "UnCompleteOrderTask", "未完成订单专用线程池"))
        val poolInfoStr = StringBuilder()
        for (info in poolList){
            val logStr = getPoolStringInfo(info)
            if (StringUtils.isNotBlank(logStr)) poolInfoStr.append(logStr)
        }
        if (StringUtils.isNotBlank(poolInfoStr.toString())) {
            sb.append(poolInfoStr)
            log.info(sb.toString())
        }
        // log.info("~~~~~~~~~~~~~~~~~~~~~~~~${System.nanoTime() - start}")
    }

    fun getPoolStringInfo(poolInfo: ThreadPoolTaskInfo): String? {
        var print = false
        val pool = poolInfo.poolExecutor.threadPoolExecutor
        if (pool.queue.size > 1) print = true   //队列有堆积，打印日志
        if (pool.activeCount >= (poolInfo.poolExecutor.maxPoolSize - 2)) print = true   //线程池活动线程数过大，打印日志
        if (!print) return null
        val sb = StringBuilder("[ ")
        sb.append(poolInfo.name)

        sb.append(" activity:")
        sb.append(pool.activeCount)
        sb.append(" size:")
        sb.append(pool.poolSize)
        sb.append(" queue:")
        sb.append(pool.queue.size)
        sb.append(" complete:")
        sb.append(pool.completedTaskCount)
        sb.append("   **")
        sb.append(poolInfo.remark)
        sb.append(" ] \n")
        return sb.toString()
    }
}

class ThreadPoolTaskInfo(var poolExecutor: ThreadPoolTaskExecutor, var name: String, var remark: String)
