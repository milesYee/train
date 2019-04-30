package cn.train.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClientPool {
    public static RedisClientPool redisClientPool = getInstance();
    public static JedisPool jedisPool;

    public static synchronized RedisClientPool getInstance(){
        if(redisClientPool==null){
            redisClientPool = new RedisClientPool();
        }
        return redisClientPool;
    }

    public RedisClientPool(){
        if(jedisPool==null){
            init();
        }
    }

    private void init(){
        JedisPoolConfig jedisPoolConfig = initPoolConfig();
        jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379,5000);
    }

    private static JedisPoolConfig initPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(300);
        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPoolConfig;
    }
}
