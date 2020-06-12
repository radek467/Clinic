package model;

import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository {
    final String getVisitByDoctorId = "SELECT * FROM visits WHERE doctor_ID = #{id}";
    final String getVisitByPatientId = "SELECT * FROM visits WHERE patient_ID = #{patientId}";
    final String getVisitById = "SELECT * FROM visits WHERE visit_ID = #{id}";
    final String deleteVisitById = "DELETE FROM visits WHERE visit_ID = #{id}";
    //final String getRatingByDoctorId = "SELECT AVG(rating) FROM visits WHERE doctor_ID = #{id} GROUP BY doctor_id";
    final String save = "INSERT INTO visits (visit_date, visit_description, doctor_ID, patient_ID, service_ID) " +
            "values (#{date}, #{description}, #{doctorId}, #{patientId}, #{serviceId})";
    final String update = "UPDATE visits SET visit_date = #{date} WHERE visit_ID = #{id}";

    @Select(getVisitByDoctorId)
    @Results( value = {
            @Result(property = "id", column = "visit_ID"),
            @Result(property = "date", column = "visit_date"),
            @Result(property = "description", column = "visit_description"),
            @Result(property = "doctorId", column = "doctor_ID"),
            @Result(property = "patientId", column = "patient_ID"),
            @Result(property = "serviceId", column = "service_ID"),
            @Result(property = "rating", column = "rating")
    })
    List<Visit> getVisitByDoctorId(int doctorId);

    @Select(getVisitByPatientId)
    @Results( value = {
            @Result(property = "id", column = "visit_ID"),
            @Result(property = "date", column = "visit_date"),
            @Result(property = "description", column = "visit_description"),
            @Result(property = "doctorId", column = "doctor_ID"),
            @Result(property = "patientId", column = "patient_ID"),
            @Result(property = "serviceId", column = "service_ID"),
            @Result(property = "rating", column = "rating")
    })
    List<Visit> getVisitByPatientId(int patientId);

    @Select(getVisitById)
    @Results(value = {
            @Result(property = "id", column = "visit_ID"),
            @Result(property = "date", column = "visit_date"),
            @Result(property = "description", column = "visit_description"),
            @Result(property = "doctorId", column = "doctor_ID"),
            @Result(property = "patientId", column = "patient_ID"),
            @Result(property = "serviceId", column = "service_ID"),
            @Result(property = "rating", column = "rating")
    })
    Optional<Visit> getVisitById(int id);

    @Insert(save)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean save(Visit visit);

    @Delete(deleteVisitById)
    boolean deleteVisitById(int id);

    @Update(update)
    boolean update(Visit visit);


}
