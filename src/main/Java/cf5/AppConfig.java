package cf5;

import cf5.db.datasources.DataSourceForSQLite;
import cf5.db.datasources.IDataSource;
import org.springframework.beans.factory.annotation.Autowired;

public class AppConfig {
    private @Autowired DataSourceForSQLite defaultDataSource;
    public IDataSource getDefaultDataSource() { return defaultDataSource; }
}
