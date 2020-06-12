package model;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;


public interface ServiceRepository {
    final String findAll = "SELECT * FROM SERVICES";
    final String findBySpecialization_ID = "SELECT * FROM services WHERE specialization_ID = #{id}";
    final String getById = "SELECT * FROM services WHERE service_ID = #{id}";

//    FIELDS
//    int id;
//    float price;
//    int duration;
//    int specializationId;

    @Select(getById)
    @Results( value = {
            @Result(property = "id", column = "service_ID"),
            @Result(property = "price", column = "price"),
            @Result(property = "title", column = "title"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "specializationId", column = "specialization_ID")
    })
    Optional<Service> getById(int id);



    @Select(findAll)
    @Results( value = {
            @Result(property = "id", column = "service_ID"),
            @Result(property = "price", column = "price"),
            @Result(property = "title", column = "title"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "specializationId", column = "specialization_ID")
    })
    List<Service> findAll();

    @Select(findBySpecialization_ID)
    @Results(value = {
            @Result(property = "id", column = "service_ID"),
            @Result(property = "price", column = "price"),
            @Result(property = "title", column = "title"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "specializationId", column = "specialization_ID")
    })
    List<Service> findBySpecializationId(int id);
}

