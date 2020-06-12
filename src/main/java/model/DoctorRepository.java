package model;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.print.Doc;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository {

    final String getAll = "SELECT * FROM doctors";
    final String getBySpecializationId = "SELECT * FROM doctors WHERE specialization_ID = #{ID}";
    final String getById = "SELECT * FROM doctors WHERE doctor_ID = #{ID}";
    //final String getAllByRatingAscending = "SELECT * FROM doctors WHERE doctor_ID IN (SELECT doctor_ID, AVG(rating), FROM visits, GROUP BY doctor_ID);";
    final String getAllAboveEnteredRating = "SELECT * FROM doctors WHERE doctor_ID IN " +
                                            "(SELECT doctor_ID from visits HAVING AVG(rating) > #{rating} order by AVG(rating));";

    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "doctor_ID"),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "specializationId", column = "specialization_ID"),
            @Result(property = "officeId", column = "office_ID")
    })
    List<Doctor> getAll();

    @Select(getBySpecializationId)
    @Results(value = {
            @Result(property = "id", column = "doctor_ID"),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "specializationId", column = "specialization_ID"),
            @Result(property = "officeId", column = "office_ID")
    })
    List<Doctor> getBySpecializationId(int id);

    @Select(getById)
    @Results(value = {
            @Result(property = "id", column = "doctor_ID"),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "specializationId", column = "specialization_ID"),
            @Result(property = "officeId", column = "office_ID")
    })
    Doctor getById(int id);

    @Select(getAllAboveEnteredRating)
    @Results(value = {
            @Result(property = "id", column = "doctor_ID"),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "specializationId", column = "specialization_ID"),
            @Result(property = "officeId", column = "office_ID")
    })
    List<Doctor> getAllAboveEnteredRating(float rating);



}
