package service;

import model.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.print.Doc;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DoctorService {

    final String resource = "mybatis-config.xml";
    Reader reader;
    SqlSessionFactory sqlSessionFactory;
    SqlSession session;
    DoctorRepository mapper;
    VisitRepository visitMapper;

    public List<Doctor> getDoctorBySpecializationId(int doctorId)
    {
        createSession();
        return mapper.getBySpecializationId(doctorId);
    }

    public Doctor getDoctorById(int doctorId)
    {
        createSession();
        return mapper.getById(doctorId);
    }

    public List<Doctor> findAll()
    {
        createSession();
        return mapper.getAll();
    }

    public List<Doctor> findAllAboveRating(float rating)
    {
        createSession();
        return mapper.getAllAboveEnteredRating(rating);
    }

    private void createSession(){
        try {
            reader = Resources.getResourceAsReader(resource);
        }catch (IOException ex){
            System.err.println("resource didn't find");
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(DoctorRepository.class);
        mapper = session.getMapper(DoctorRepository.class);
    }

}
