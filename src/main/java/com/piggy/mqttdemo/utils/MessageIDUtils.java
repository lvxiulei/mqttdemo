package com.piggy.mqttdemo.utils;

import java.util.Random;

/**
 * @Author lvxiulei on 2024-01-23 16:59
 * @Description
 * @Version V1.0
 */
public class MessageIDUtils {

    private final String BASE = "RUNTIME:";
    private final String MESSAGE = BASE + "MESSAGE_ID:";
    private final long TTL = 60 * 60 * 24 * 30;

    private static MessageIDUtils _instances;

    private MessageIDUtils() {

    }

    public static MessageIDUtils getInstances() {
        return _instances;
    }

    public short getMessageId(String client) {
        return (short) buildMessageID();
//        String key = MESSAGE + client;
//        try {
//            if (_mcAppRedisService.exists(key)){
//                long messageID = _mcAppRedisService.increment(key, 1, TTL, TimeUnit.SECONDS);
//                if (messageID >= Short.MAX_VALUE){
//                    messageID =  buildMessageID();
//                }
//                _mcAppRedisService.set(key, messageID + "", TTL, TimeUnit.SECONDS);
//                return (short) messageID;
//            }else{
//                int messageID =  buildMessageID();
//                _mcAppRedisService.set(key, messageID + "", TTL, TimeUnit.SECONDS);
//                return (short) messageID;
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//            return (short) buildMessageID();
//        }
    }

    private static int buildMessageID(){
        int max = Short.MAX_VALUE / 2;
        int min = 2;
        Random random = new Random();
        return random.nextInt(max)%( max - min + 1) + min;
    }
}
