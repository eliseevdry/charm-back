package ru.eliseev.charm.back.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import redis.clients.jedis.Jedis;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.utils.RedisManager;

import java.io.IOException;
import java.util.Optional;
import java.util.Queue;

import static ru.eliseev.charm.back.utils.RedisManager.EXP_SEC;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileCacheService {

    private static final long NULL_OBJECT_ID = -1L;
    private final JsonMapper jsonMapper = JsonMapper.getInstance();
    private static final ProfileCacheService INSTANCE = new ProfileCacheService();

    public static ProfileCacheService getInstance() {
        return INSTANCE;
    }


    public Optional<ProfileSimpleDto> lPop(String queueKey) throws IOException {
        try (Jedis jedis = RedisManager.getResource()) {
            Optional<String> jsonOpt = jedis.lrange(queueKey, 0, 0).stream().findFirst();
            if (jsonOpt.isEmpty()) {
                return Optional.empty();
            }
            ProfileSimpleDto dto = jsonMapper.readValue(jsonOpt.get(), ProfileSimpleDto.class);
            if (!isNullObject(dto)) {
                jedis.lpop(queueKey);
            }
            return Optional.of(dto);
        }
    }

    public boolean isNullObject(ProfileSimpleDto dto) {
        return dto.getId() == NULL_OBJECT_ID;
    }

    public void setQueue(String queueKey, Queue<ProfileSimpleDto> queue) throws IOException {
        try (Jedis jedis = RedisManager.getResource()) {
            if (queue.isEmpty()) {
                ProfileSimpleDto nullObject = new ProfileSimpleDto();
                nullObject.setId(NULL_OBJECT_ID);
                queue.offer(nullObject);
            }
            for (ProfileSimpleDto dto : queue) {
                String json = jsonMapper.writeValueAsString(dto);
                jedis.rpush(queueKey, json);
            }
            jedis.expire(queueKey, EXP_SEC);
        }
    }
}
