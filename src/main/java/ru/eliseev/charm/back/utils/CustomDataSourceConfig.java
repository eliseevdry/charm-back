package ru.eliseev.charm.back.utils;

public record CustomDataSourceConfig(
		String jdbcUrl,
		String username,
		String password,
		int poolSize
) {
}
