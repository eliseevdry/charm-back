package ru.eliseev.charm.back.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.eliseev.charm.back.dao.LikeDao;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.Action;
import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;

import java.util.Optional;
import java.util.Queue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharmService {

	private static final CharmService INSTANCE = new CharmService();
	private final LikeDao likeDao = LikeDao.getInstance();
	private final ProfileDao profileDao = ProfileDao.getInstance();
	private final ProfileCacheService profileCacheService = ProfileCacheService.getInstance();

	public static CharmService getInstance() {
		return INSTANCE;
	}

	@SneakyThrows
	public Optional<ProfileSimpleDto> next(CharmDto charmDto) {
		Long userId = charmDto.getFromProfile();
		Action action = charmDto.getAction();
		if (action != Action.SKIP) {
			likeDao.like(userId, charmDto.getToProfile(), action == Action.LIKE);
		}

		Optional<ProfileSimpleDto> nextOpt = profileCacheService.lPop(userId.toString());

		if (nextOpt.isPresent() && profileCacheService.isNullObject(nextOpt.get())) {
			return Optional.empty();
		}

		if (nextOpt.isEmpty()) {
			Queue<ProfileSimpleDto> queue = profileDao.findSuitableForUser(userId, 5);
			nextOpt = Optional.ofNullable(queue.poll());
			profileCacheService.setQueue(userId.toString(), queue);
		}

		return nextOpt;
	}
}
