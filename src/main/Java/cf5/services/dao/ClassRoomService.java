package cf5.services.dao;

import cf5.dto.ClassRoomDTO;
import cf5.dto.TeacherDTO;
import cf5.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService extends AbstractService<ClassRoomDTO> {
    private static final String querySelectOne =
            "SELECT CR.ID, CR.NAME, T.USER_ID AS TEACHER_USER_ID, TU.FIRSTNAME AS TEACHER_FIRSTNAME, TU.LASTNAME AS TEACHER_LASTNAME, L.ID AS LESSON_ID, L.NAME AS LESSON_NAME " +
                    "FROM CLASSROOMS CR " +
                    "LEFT JOIN TEACHERS T ON T.ID = CR.TEACHER_ID " +
                    "LEFT JOIN USERS TU ON TU.ID = T.USER_ID " +
                    "LEFT JOIN LESSONS L ON L.ID = CR.LESSON_ID " +
                    "WHERE CR.ID = ?";
    private static final String querySelectAll =
            "SELECT CR.ID, CR.NAME, T.USER_ID AS TEACHER_USER_ID, TU.FIRSTNAME AS TEACHER_FIRSTNAME, TU.LASTNAME AS TEACHER_LASTNAME, L.ID AS LESSON_ID, L.NAME AS LESSON_NAME " +
                    "FROM CLASSROOMS CR " +
                    "LEFT JOIN TEACHERS T ON T.ID = CR.TEACHER_ID " +
                    "LEFT JOIN USERS TU ON TU.ID = T.USER_ID " +
                    "LEFT JOIN LESSONS L ON L.ID = CR.LESSON_ID ";
    private static final String querySelectAllForTeacher =
            "SELECT CR.ID, CR.NAME, T.USER_ID AS TEACHER_USER_ID, TU.FIRSTNAME AS TEACHER_FIRSTNAME, TU.LASTNAME AS TEACHER_LASTNAME, L.ID AS LESSON_ID, L.NAME AS LESSON_NAME " +
                    "FROM CLASSROOMS CR " +
                    "JOIN TEACHERS T ON T.ID = CR.TEACHER_ID " +
                    "JOIN USERS TU ON TU.ID = T.USER_ID " +
                    "JOIN LESSONS L ON L.ID = CR.LESSON_ID " +
                    "WHERE T.USER_ID = ?";
    private static final String queryInsertOne = "INSERT INTO CLASSROOMS (NAME, TEACHER_ID, LESSON_ID) VALUES (?, ?, ?)";
    private static final String queryUpdateOne = "UPDATE CLASSROOMS SET NAME = ?, TEACHER_ID = ?, LESSON_ID = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM CLASSROOMS WHERE ID = ?";

    private @Autowired TeachersService teachersService;

    @Override
    public Optional<ClassRoomDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(ClassRoomDTO.newConverter(), querySelectOne, keyValues);
    }

    @Override
    public List<ClassRoomDTO> getAll() throws SQLException {
        return super.defaultSelectAll(ClassRoomDTO.newConverter(), querySelectAll);
    }
    public List<ClassRoomDTO> getClassesForTeacher(UserDTO userDto) throws SQLException {
        return getJdbcIO().select(getDefaultDataSource(), ClassRoomDTO.newConverter(), querySelectAllForTeacher, userDto.recId());
    }


    @Override
    public void insert(ClassRoomDTO classRoomDTO) throws SQLException {
        TeacherDTO teacherDTO = teachersService.findByUserId(classRoomDTO.teacherUserId()).orElseThrow();
        getJdbcIO().executeQuery(getDefaultDataSource(), queryInsertOne, classRoomDTO.name(), teacherDTO.recId(), classRoomDTO.lessonId());
    }

    @Override
    public void update(ClassRoomDTO classRoomDTO) throws SQLException {
        TeacherDTO teacherDTO = teachersService.findByUserId(classRoomDTO.teacherUserId()).orElseThrow();
        getJdbcIO().executeQuery(getDefaultDataSource(), queryUpdateOne, classRoomDTO.name(), teacherDTO.recId(), classRoomDTO.lessonId(), classRoomDTO.recId());
    }

    @Override
    public void delete(int id) throws SQLException {
        super.defaultDelete(id, queryDeleteOne);
    }
}
