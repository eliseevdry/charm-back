package ru.eliseev.charm.pool.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor
@Accessors(chain = true)
public class CustomDataSourceConfig {
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final int poolSize;
    @Setter
    private int fetchSize = 0;
    @Setter
    private int maxRows = 1000;
    @Setter
    private int queryTimeout = 10;
}
