package cf5.db.loader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public sealed interface IRowLoader<T> permits RowLoader {
    T convertResultSet(ResultSet resultSet) throws SQLException;
    T convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException;
}
