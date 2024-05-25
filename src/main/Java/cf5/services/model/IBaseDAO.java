package cf5.services.model;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

sealed interface IBaseDAO<T> permits AbstractService {
    default Optional<T> get(Object... keyValues) throws SQLException { throw new UnsupportedOperationException(); }
    default List<T> getAll() throws SQLException { throw new UnsupportedOperationException(); }
    default void insert(T t) throws SQLException, InvocationTargetException, IllegalAccessException { throw new UnsupportedOperationException(); }
    default void update(T t) throws SQLException, InvocationTargetException, IllegalAccessException { throw new UnsupportedOperationException(); }
    default void delete(T t) throws SQLException, InvocationTargetException, IllegalAccessException { throw new UnsupportedOperationException(); }
}