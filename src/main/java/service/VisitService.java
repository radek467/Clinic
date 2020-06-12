package service;

import model.*;
import oracle.ucp.util.Pair;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class VisitService {

    final String resource = "mybatis-config.xml";
    Reader reader;
    SqlSessionFactory sqlSessionFactory;
    SqlSession session;
    VisitRepository mapper;
    ServiceRepository serviceMapper;
    PatientRepository patientMapper;


    public Pair<List<LocalDateTime>, List<LocalDateTime>> createTermsSchema(int doctorId)
    {
        createSession();
        List<Visit> visits = mapper.getVisitByDoctorId(doctorId);
        session.close();

        List<LocalDateTime> begins = getBeginOfVisits(visits);
        List<LocalDateTime> ends = getEndOfVisits(visits);

        return new Pair<>(begins, ends);
    }

    public List<Visit> showPatientVisits(String pesel)
    {
        createPatientSession();
        Integer id = patientMapper.getPatientIdByPesel(pesel)
                .orElseThrow(() -> new IllegalArgumentException("Patient with this pesel ins't exists"));
        session.close();

        createSession();
        return mapper.getVisitByPatientId(id);
    }

    public boolean cancelVisit(int visitId)
    {
        createSession();
        if(mapper.deleteVisitById(visitId) == true) {
            session.commit();
            session.close();
            return true;
        }
        session.rollback();
        session.close();
        return false;
    }

    public Visit getVisitById(int id)
    {
        createSession();
        Visit toReturn = mapper.getVisitById(id).orElseThrow(() -> new IllegalArgumentException("Visit with this id isn't exists"));
        session.close();
        return toReturn;
    }


    public boolean createVisit(LocalDateTime visitDateTimeBegin, Service service, int doctorId, String pesel)
    {
        createSession();
        List<Visit> visits = mapper.getVisitByDoctorId(doctorId);
        session.close();

        LocalDateTime visitDateTimeEnd = visitDateTimeBegin.plusMinutes(service.getDuration());

        List<LocalDateTime> begins = getBeginOfVisits(visits);
        List<LocalDateTime> ends = getEndOfVisits(visits);

        LocalDateTime closeTime = LocalDateTime.of(visitDateTimeBegin.toLocalDate(), LocalTime.of(18, 00));

        for(int i = 0 ; i < begins.size(); i++){

            if(visitDateTimeBegin.isAfter(ends.get(i)) && visitDateTimeEnd.isBefore(closeTime)
                    || visitDateTimeBegin.isBefore(ends.get(i)) && visitDateTimeEnd.isBefore(begins.get(i))) {
                continue;
            }
            else {
                System.err.println("No free date");
                return false;
            }
        }
        createPatientSession();
        Patient patient;
        patient = patientMapper.getPatientByPesel(pesel)
                    .orElseThrow(() -> new IllegalArgumentException("Patient with this pesel isnt't exists"));

        createSession();
        if(mapper.save(Visit.builder()
                .date(visitDateTimeBegin)
                .patientId(patient.getId())
                .doctorId(doctorId)
                .serviceId(service.getId())
                .build()) == false)
        {
            session.rollback();
            return false;
        }

        session.commit();
        session.close();
        return true;

    }

    public boolean changeTerm(LocalDateTime visitDateTimeBegin, int visitId)
    {
        createSession();
        Visit visit = getVisitById(visitId);
        session.close();

        createServiceSession();
        Service service = serviceMapper.getById(visit.getServiceId()).get();
        session.close();

        createSession();
        List<Visit> visits = mapper.getVisitByDoctorId(visit.getDoctorId());
        session.close();

        LocalDateTime visitDateTimeEnd = visitDateTimeBegin.plusMinutes(service.getDuration());

        List<LocalDateTime> begins = getBeginOfVisits(visits);
        List<LocalDateTime> ends = getEndOfVisits(visits);

        LocalDateTime closeTime = LocalDateTime.of(visitDateTimeBegin.toLocalDate(), LocalTime.of(18, 00));

        for(int i = 0 ; i < begins.size(); i++) {

            if (visitDateTimeBegin.isAfter(ends.get(i)) && visitDateTimeEnd.isBefore(closeTime)
                    || visitDateTimeBegin.isBefore(ends.get(i)) && visitDateTimeEnd.isBefore(begins.get(i))) {
                continue;
            } else {
                System.err.println("No free date");
                session.rollback();
                session.close();
                return false;
            }
        }


        Visit toUpdate = Visit.builder()
                .date(visitDateTimeBegin)
                .doctorId(visit.getDoctorId())
                .patientId(visit.getPatientId())
                .serviceId(visit.getServiceId())
                .description(visit.getDescription())
                .rating(visit.getRating())
                .id(visitId).build();
        createSession();
        if(mapper.update(toUpdate) == true){
            session.commit();
            session.close();
            return true;
        }
        session.rollback();
        session.close();
        return false;
    }

    private List<LocalDateTime> getBeginOfVisits(List<Visit> visits)
    {
        return visits.stream()
                .map(v -> v.getDate())
                .collect(Collectors.toList());
    }


    private List<LocalDateTime> getEndOfVisits(List<Visit> begins)
    {
        createServiceSession();
        return begins.stream()
                .map(v -> v.getDate()
                        .plusMinutes(serviceMapper.getById(v.getServiceId())
                                .get()
                                .getDuration()))
                .collect(Collectors.toList());
    }


    private void createSession(){
        try {
            reader = Resources.getResourceAsReader(resource);
        }catch (IOException ex){
            System.err.println("resource didn't find");
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(VisitRepository.class);
        mapper = session.getMapper(VisitRepository.class);
    }

    private void createServiceSession()
    {
        try {
            reader = Resources.getResourceAsReader(resource);
        }catch (IOException ex){
            System.err.println("resource didn't find");
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(ServiceRepository.class);
        serviceMapper = session.getMapper(ServiceRepository.class);
    }

    private void createPatientSession()
    {
        try {
            reader = Resources.getResourceAsReader(resource);
        }catch (IOException ex){
            System.err.println("resource didn't find");
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(PatientRepository.class);
        patientMapper = session.getMapper(PatientRepository.class);
    }


}
