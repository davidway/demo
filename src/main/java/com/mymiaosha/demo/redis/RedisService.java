package com.mymiaosha.demo.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;


    public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = jedis.get(prefix.getPrefix()+key);
            T t = stringToBean(str,clazz);
            return t;
        }finally {
            reutrnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix,String key,T value){

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            int seconds = prefix.expireSeconds();
            if ( seconds <=0){
                jedis.set(prefix.getPrefix()+key,str);
            }else{
                jedis.setex(prefix.getPrefix()+key,seconds,str);
            }

             return true;

        }finally {
            reutrnToPool(jedis);
        }
    }

    public <T> boolean exists(KeyPrefix prefix,String key,T value){

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            boolean exists = jedis.exists(prefix.getPrefix()+key);
            return exists;

        }finally {
            reutrnToPool(jedis);
        }
    }

    public <T> Long incr(KeyPrefix prefix,String key,T value){

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            Long result = jedis.incr(prefix.getPrefix()+key);
            return result;

        }finally {
            reutrnToPool(jedis);
        }
    }
    public <T> Long decr(KeyPrefix prefix,String key,T value){

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            Long result = jedis.decr(prefix.getPrefix()+key);
            return result;

        }finally {
            reutrnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if ( value==null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz==int.class || clazz==Integer.class){
            return ""+value;
        }else if ( clazz == String.class){
            return (String)value;
        }else if (clazz == long.class|| clazz==Long.class){
            return String.valueOf(clazz);
        }else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T stringToBean(String value,Class<T> clazz) {
        if ( value==null|| value.length()<0){
            return null;
        }

        if(clazz==int.class || clazz==Integer.class){
            return (T)Integer.valueOf(value);
        }else if ( clazz == String.class){
            return (T)value;
        }else if (clazz == long.class|| clazz==Long.class){
            return (T)Long.valueOf(value);
        }else {
            return JSON.toJavaObject(JSON.parseObject(value),clazz);
        }
    }

    private void reutrnToPool(Jedis jedis) {
        if ( jedis!=null){
            jedis.close();
        }
    }



}
