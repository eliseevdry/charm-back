package ru.eliseev.charm.back.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.eliseev.charm.utils.ConfigFileUtils;

@Configuration
public class PostgresConfig {
    @Bean
    public HikariDataSource dataSource() {
        String poolSize = ConfigFileUtils.get("app.datasource.pool.size");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ConfigFileUtils.get("app.datasource.url"));
        config.setUsername(ConfigFileUtils.get("app.datasource.username"));
        config.setPassword(ConfigFileUtils.get("app.datasource.password"));
        config.setMaximumPoolSize(Integer.parseInt(poolSize != null ? poolSize : "10"));
        config.setDriverClassName(ConfigFileUtils.get("app.datasource.driver"));
        config.setMinimumIdle(5);
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(1800000);
        config.setConnectionTestQuery("SELECT 1");
        return new HikariDataSource(config);
    }
}
