package cf5.db.datasources;

import javax.sql.DataSource;

public sealed interface IDataSource permits DataSourceForSQLite {
    DataSource getDS();
}
