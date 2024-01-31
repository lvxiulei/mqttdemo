package com.piggy.mqttdemo.config

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * @Author lvxiulei on 2024-01-23 22:36
 * @Description 根据Spring上下文获取bean
 * @Version V1.0
 */
@Component
class ApplicationContextProvider : ApplicationContextAware {
    companion object {
        var applicationContext: ApplicationContext? = null
            private set

        /**
         * 通过class获取Bean
         */
        fun <T> getBean(clazz: Class<T>): T {
            return applicationContext!!.getBean(clazz)
        }

        /**
         * 通过类名获取Bean
         */
        fun getBean(beanName: String): Any {
            return applicationContext!!.getBean(beanName)
        }

        /**
         * 通过类名、class获取Bean
         */
        fun <T> getBean(beanName: String, clazz: Class<T>): T {
            return applicationContext!!.getBean(beanName, clazz)
        }

    }

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        if (Companion.applicationContext == null) {
            Companion.applicationContext = applicationContext
        }
        Companion.applicationContext = applicationContext
    }

}