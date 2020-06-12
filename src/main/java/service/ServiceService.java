package service;

import model.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;


public class ServiceService {
    final String resource = "mybatis-config.xml";
    Reader reader;
    SqlSessionFactory sqlSessionFactory;
    SqlSession session;
    ServiceRepository mapper;

    public List<Service> readAllServices()
    {
        createSession();
        return mapper.findAll();
    }

    public List<Service> findServiceBySpecializationId(int id)
    {
        createSession();
        return mapper.findBySpecializationId(id);
    }

    public Service findServiceById(int id)
    {
        createSession();
        Service service = mapper.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service with this id ins't exists"));
        return service;

    }
    private void createSession(){
        try {
            reader = Resources.getResourceAsReader(resource);
        }catch (IOException ex){
            System.err.println("resource didn't find");
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(ServiceRepository.class);
        mapper = session.getMapper(ServiceRepository.class);
    }
}
