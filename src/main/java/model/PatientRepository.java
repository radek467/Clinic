package model;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    final String insertPatient = "insert into patients (name, surname, pesel, phone_number, email) " +
            "values (#{name}, #{surname}, #{pesel}, #{phoneNumber}, #{email})";
    final String getPersonByPesel = "SELECT * FROM patients WHERE pesel = #{pesel}" ;

    final String getPatientIdByPesel = "SELECT patient_ID FROM patients WHERE pesel = #{pesel}";

    final String getAll = "SELECT * FROM patients";

    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "patient_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "pesel", column = "pesel"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "email", column = "email")
    })
    List<Patient> getAll();


    @Select(getPersonByPesel)
    @Results(value = {
            @Result(property = "id", column = "patient_ID"),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "pesel", column = "pesel"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "email", column = "email")
    })
    public Optional<Patient> getPatientByPesel (String pesel);


    @Select(getPatientIdByPesel)
    @Results(value = {
            @Result(property = "id", column = "patient_ID")
    })
    public Optional<Integer> getPatientIdByPesel(String pesel);


    @Insert(insertPatient)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public boolean save(Patient patient);
}
