package service;

import model.DoctorRepository;
import model.Patient;
import model.PatientRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.h2.engine.Session;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class PatientService {
    final String resource = "mybatis-config.xml";
    Reader reader;
    SqlSessionFactory sqlSessionFactory;
    SqlSession session;
    PatientRepository mapper;


    public boolean createPatient(Patient patient){

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        String onlyNumberRegex = "^[0-9]*$";

        if(patient.getPesel().length() != 11 || !patient.getPesel().matches(onlyNumberRegex))
            throw new IllegalArgumentException("Invalid pesel number, please try again");
        if(patient.getPhoneNumber().length() != 9 || !patient.getPhoneNumber().matches(onlyNumberRegex))
            throw new IllegalArgumentException("Invalid phone number, please try again");
        if(!patient.getEmail().matches(emailRegex))
            throw new IllegalArgumentException("Invalid email, please try again");

        createSession();


        if(!mapper.getPatientByPesel(patient.getPesel()).isEmpty())
            throw new IllegalArgumentException("Patient with this pesel already exists");

        if(mapper.save(patient) == true) {
                session.commit();
                session.close();
                return true;
        }
        else{
            session.rollback();
            session.close();
            return false;
        }

    }

    public List<Patient> findAll()
    {
        createSession();
        return mapper.getAll();
    }

    void createSession(){
        try {
            reader = Resources.getResourceAsReader(resource);
        }catch (IOException ex){
            System.err.println("resource didn't find");
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(PatientRepository.class);
        mapper = session.getMapper(PatientRepository.class);
    }
}
