package ru.eliseev.charm.back.service;

public class LikeService {

    private static final LikeService INSTANCE = new LikeService();
    private LikeService() {
    }

    public static LikeService getInstance() {
        return INSTANCE;
    }

    public int getLikesByProfileId(long profileId) {
        return 10;
    }
}
