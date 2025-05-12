package ru.eliseev.charm.back.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.LikeDao;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.Action;
import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharmService {

	private static final CharmService INSTANCE = new CharmService();
	private final LikeDao likeDao = LikeDao.getInstance();
	private final ProfileDao profileDao = ProfileDao.getInstance();
	private final ConcurrentHashMap<Long, Queue<ProfileSimpleDto>> cache = new ConcurrentHashMap<>();

	public static CharmService getInstance() {
		return INSTANCE;
	}

	public Optional<ProfileSimpleDto> next(CharmDto charmDto) {
		Long userId = charmDto.getFromProfile();
		Action action = charmDto.getAction();
		if (action != Action.SKIP) {
			likeDao.like(userId, charmDto.getToProfile(), action == Action.LIKE);
		}

		Queue<ProfileSimpleDto> queue = cache.get(userId);

		if (queue == null || queue.isEmpty()) {
			queue = profileDao.findSuitableForUser(userId, 5);
			cache.put(userId, queue);
		}

		return Optional.ofNullable(queue.poll());
	}
}
