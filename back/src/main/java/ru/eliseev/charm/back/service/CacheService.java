package ru.eliseev.charm.back.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.eliseev.charm.back.mapper.JsonMapper;

import java.io.IOException;
import java.util.Queue;

@Setter
@Service
public class CacheService {

    @Autowired
    private JsonMapper jsonMapper;
    @Autowired
    private JedisPool jedisPool;

    public <T> T poll(String queueKey, Class<T> clazz) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.lpop(queueKey);
            if (json == null) {
                return null;
            }
            return jsonMapper.readValue(json, clazz);
        }
    }

    public <T> void setQueue(String queueKey, Queue<T> queue) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            for (T dto : queue) {
                String json = jsonMapper.writeValueAsString(dto);
                jedis.rpush(queueKey, json);
            }
        }
    }
}
