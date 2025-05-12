package ru.eliseev.charm.back.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import redis.clients.jedis.Jedis;
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.utils.RedisManager;

import java.io.IOException;
import java.util.Queue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheService {

    private final JsonMapper jsonMapper = JsonMapper.getInstance();
    private static final CacheService INSTANCE = new CacheService();

    public static CacheService getInstance() {
        return INSTANCE;
    }


    public <T> T poll(String queueKey, Class<T> clazz) throws IOException {
        try (Jedis jedis = RedisManager.getResource()) {
            String json = jedis.lpop(queueKey);
            if (json == null) {
                return null;
            }
            return jsonMapper.readValue(json, clazz);
        }
    }

    public <T> void setQueue(String queueKey, Queue<T> queue) throws IOException {
        try (Jedis jedis = RedisManager.getResource()) {
            for (T dto : queue) {
                String json = jsonMapper.writeValueAsString(dto);
                jedis.rpush(queueKey, json);
            }
        }
    }
}
