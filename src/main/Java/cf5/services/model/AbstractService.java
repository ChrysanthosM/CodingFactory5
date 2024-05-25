package cf5.services.model;

import cf5.AppConfig;
import cf5.Utils.RecordUtils;
import cf5.db.JdbcIO;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public abstract non-sealed class AbstractService<T> implements IBaseDAO<T> {
    private @Autowired ApplicationContext context;
    @Getter private DataSource defaultDataSource;
    @Autowired @Getter private JdbcIO jdbcIO;

    @PostConstruct
    private void init() {
        defaultDataSource = context.getBean(AppConfig.class).getDefaultDataSource().getDS();
    }

    public <T> void defaultInsert(T record, String query) throws InvocationTargetException, IllegalAccessException, SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), query, Lists.newArrayList(RecordUtils.getRecordValues(record).stream().skip(1).collect(Collectors.toList())).toArray());
    }
    public <T> void defaultUpdate(T record, String query) throws InvocationTargetException, IllegalAccessException, SQLException {
        List<Object> listValues = RecordUtils.getRecordValues(record);
        getJdbcIO().executeQuery(getDefaultDataSource(), query, Lists.newArrayList(listValues.stream().skip(1).collect(Collectors.toList())).toArray(), listValues.getFirst());
    }
    public <T> void defaultDelete(T record, String query) throws InvocationTargetException, IllegalAccessException, SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), query, RecordUtils.getRecordValues(record).getFirst());
    }



}
