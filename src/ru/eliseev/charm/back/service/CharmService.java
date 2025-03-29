package ru.eliseev.charm.back.service;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.LikeDao;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.Action;
import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharmService {

	private static final CharmService INSTANCE = new CharmService();
	private final LikeDao likeDao = LikeDao.getInstance();
	private final ProfileDao profileDao = ProfileDao.getInstance();

	public static CharmService getInstance() {
		return INSTANCE;
	}

	public Optional<ProfileSimpleDto> next(CharmDto charmDto) {
		Long userId = charmDto.getFromProfile();
		Action action = charmDto.getAction();
		if (action != Action.SKIP) {
			likeDao.like(userId, charmDto.getToProfile(), action == Action.LIKE);
		}
		List<ProfileSimpleDto> suitableProfiles = profileDao.findSuitableForUser(userId, 1);
		return suitableProfiles.stream().findFirst();
	}
}
