package cf5.db.datasources;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    @Bean(name = "sqliteDataSource")
    public DataSource sqliteDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        final String url = "jdbc:sqlite:src/main/resources/DBs/CF5.db";
        dataSource.setJdbcUrl(url);
        return dataSource;
    }
    @Bean(name = "sqliteJdbcTemplate")
    public JdbcTemplate sqliteJdbcTemplate(@Qualifier("sqliteDataSource") DataSource sqliteDataSource) {
        return new JdbcTemplate(sqliteDataSource);
    }
}
