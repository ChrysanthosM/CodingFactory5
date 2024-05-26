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
        INDEX_PAGE("IndexPage", "redirect:/index"),
        LOGIN_PAGE("LoginPage", "redirect:/login"),
        SIGN_UP_PAGE("SignUpPage", "redirect:/signup"),
        WELCOME_PAGE("WelcomePage", "redirect:/welcome");

        final String page;
        final String redirect;
    }
}
