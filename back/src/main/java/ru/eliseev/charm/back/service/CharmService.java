package ru.eliseev.charm.back.service;

import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eliseev.charm.back.dao.LikeDao;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.Action;
import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;

import java.util.Optional;
import java.util.Queue;

@Setter
@Service
public class CharmService {
    @Autowired
    private LikeDao likeDao;
    @Autowired
    private ProfileDao profileDao;
    @Autowired
    private CacheService cacheService;

	@SneakyThrows
	public Optional<ProfileSimpleDto> next(CharmDto charmDto) {
		Long userId = charmDto.getFromProfile();
		Action action = charmDto.getAction();
		if (action != Action.SKIP) {
			likeDao.like(userId, charmDto.getToProfile(), action == Action.LIKE);
		}

		ProfileSimpleDto next = cacheService.poll(userId.toString(), ProfileSimpleDto.class);

		if (next == null) {
			Queue<ProfileSimpleDto> queue = profileDao.findSuitableForUser(userId, 5);
			next = queue.poll();
			cacheService.setQueue(userId.toString(), queue);
		}

		return Optional.ofNullable(next);
	}
}
