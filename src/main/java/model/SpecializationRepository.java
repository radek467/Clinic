package model;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SpecializationRepository {
    final String findAll = "SELECT * FROM SPECIALIZATIONS";


    @Select(findAll)
    @Results( value = {
            @Result(property = "id", column = "specialization_ID"),
            @Result(property = "type", column = "specialization_type")
    })
    List<Specialization> findAll();


}
