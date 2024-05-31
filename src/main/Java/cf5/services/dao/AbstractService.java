package cf5.services.dao;

import cf5.AppConfig;
import cf5.utils.RecordUtils;
import cf5.db.JdbcIO;
import cf5.db.loader.RowLoader;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract non-sealed class AbstractService<T> implements IBaseDAO<T> {
    private @Autowired ApplicationContext context;
    @Getter private DataSource defaultDataSource;
    @Getter private @Autowired JdbcIO jdbcIO;

    @PostConstruct
    private void init() {
        defaultDataSource = context.getBean(AppConfig.class).getDefaultDataSource().getDS();
    }

    protected <T> Optional<T> defaultSelectOne(RowLoader<T> dtoConverter, String querySelectOne, Object... keyValues) throws SQLException {
        return getJdbcIO().selectOne(getDefaultDataSource(), dtoConverter, querySelectOne, keyValues);
    }
    protected <T> List<T> defaultSelectAll(RowLoader<T> dtoConverter, String querySelectOne) throws SQLException {
        return getJdbcIO().select(getDefaultDataSource(), dtoConverter, querySelectOne);
    }
    protected <T> void defaultInsert(T record, String query) throws InvocationTargetException, IllegalAccessException, SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), query, Lists.newArrayList(RecordUtils.getRecordValues(record).stream().skip(1).collect(Collectors.toList())).toArray());
    }
    protected <T> void defaultUpdate(T record, String query) throws InvocationTargetException, IllegalAccessException, SQLException {
        List<Object> listValues = RecordUtils.getRecordValues(record);
        List<Object> paramValues = Lists.newArrayList();
        paramValues.addAll(listValues.stream().skip(1).toList());
        paramValues.add(listValues.getFirst());
        getJdbcIO().executeQuery(getDefaultDataSource(), query, paramValues.toArray());
    }
    protected void defaultDelete(int id, String query) throws SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), query, id);
    }



}
