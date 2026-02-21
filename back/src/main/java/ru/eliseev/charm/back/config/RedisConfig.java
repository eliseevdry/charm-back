package ru.eliseev.charm.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import ru.eliseev.charm.utils.ConfigFileUtils;

@Configuration
public class RedisConfig {
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        final String host = ConfigFileUtils.get("app.redis.host");
        final String port = ConfigFileUtils.get("app.redis.port");
        String url = String.format("redis://%s:%s/", host, port);
        return new JedisPool(jedisPoolConfig, url);
    }
}
