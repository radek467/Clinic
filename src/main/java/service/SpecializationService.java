package service;

import model.PatientRepository;
import model.Specialization;
import model.SpecializationRepository;
import model.VisitRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class SpecializationService {

    final String resource = "mybatis-config.xml";
    Reader reader;
    SqlSessionFactory sqlSessionFactory;
    SqlSession session;
    SpecializationRepository mapper;

    public List<Specialization> findAllSpecializations()
    {
        createSession();
        return mapper.findAll();
    }

    private void createSession(){
        try {
            reader = Resources.getResourceAsReader(resource);
        }catch (IOException ex){
            System.err.println("resource didn't find");
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(SpecializationRepository.class);
        mapper = session.getMapper(SpecializationRepository.class);
    }
}
