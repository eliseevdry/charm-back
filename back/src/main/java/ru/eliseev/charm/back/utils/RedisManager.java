package ru.eliseev.charm.back.utils;

import lombok.experimental.UtilityClass;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@UtilityClass
public class RedisManager {
    private static final String HOST = ConfigFileUtils.get("app.redis.host");
    private static final String PORT_STR = ConfigFileUtils.get("app.redis.port");
    private static final int PORT = Integer.parseInt(PORT_STR != null ? PORT_STR : "6379");
    private static JedisPool pool;

    static {
        init();
    }

    private static void init() {
        pool = new JedisPool(HOST, PORT);
    }

    public static Jedis getResource() {
        return pool.getResource();
    }

    public static void close() {
        if (pool != null) {
            pool.close();
        }
    }
}
