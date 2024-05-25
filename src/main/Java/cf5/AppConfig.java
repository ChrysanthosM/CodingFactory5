package cf5;

import cf5.db.datasources.DataSourceForSQLite;
import cf5.db.datasources.IDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private @Autowired DataSourceForSQLite defaultDataSource;
    public IDataSource getDefaultDataSource() { return defaultDataSource; }

    @Getter
    @AllArgsConstructor
    public enum ApplicationPages {
        INDEX_PAGE("IndexPage"),
        LOGIN_PAGE("LoginPage"),
        SIGN_UP_PAGE("SignUpPage"),
        WELCOME_PAGE("WelcomePage");

        final String page;
    }
}
