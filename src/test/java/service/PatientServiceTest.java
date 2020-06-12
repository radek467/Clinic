package service;


import model.Patient;
import model.PatientRepository;
import model.Service;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Test
    public void createPatient_shouldThrowIllegalArgumentExceptionWhenPeselLengthIsDifferentThen11()
    {
        //given
        PatientService service = new PatientService();
        Patient patient = Patient.builder().pesel("123456789").build();

        //when
        Throwable result = catchThrowable(() -> service.createPatient(patient));

        //then
        assertThat(result).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("pesel");
    }

    @Test
    public void createPatient_shouldThrowIllegalArgumentExceptionWhenPeselContainsNonNumericValue()
    {
        //given
        PatientService service = new PatientService();
        Patient patient = Patient.builder().pesel("a1234567890").build();

        //when
        Throwable result = catchThrowable(() -> service.createPatient(patient));

        //then
        assertThat(result).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("pesel");
    }

    @Test
    public void createPatient_shouldThrowIllegalArgumentExceptionWhenNumberLengthIsDifferentThan9()
    {
        PatientService service = new PatientService();
        Patient patient = Patient.builder().phoneNumber("123456789123").pesel("12345678912").build();

        //when
        Throwable result = catchThrowable(() -> service.createPatient(patient));

        //then
        assertThat(result).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("number");
    }

    @Test
    public void createPatient_shouldThrowIllegalArgumentExceptionWhenNumberContainsNonNumericValue()
    {
        PatientService service = new PatientService();
        Patient patient = Patient.builder().phoneNumber("12345678a").pesel("12345678912").build();

        //when
        Throwable result = catchThrowable(() -> service.createPatient(patient));

        //then
        assertThat(result).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("number");
    }

    @Test
    public void createPatient_shouldThrowIllegalArgumentExceptionWhenEmailNotSuitToRegex()
    {
        //given
        String email1 = "!aaa@gmail.com";
        String email2 = "  @22mail.com";
        String email3 = "aaa@gmail.com1";
        String email4 = ".aaa@.mail.3om";
        PatientService service = new PatientService();


        //when
        Throwable exception1 = catchThrowable(() -> service.createPatient(createPatient(email1)));
        Throwable exception2 = catchThrowable(() -> service.createPatient(createPatient(email2)));
        Throwable exception3 = catchThrowable(() -> service.createPatient(createPatient(email3)));
        Throwable exception4 = catchThrowable(() -> service.createPatient(createPatient(email4)));

        //then
        assertThat(exception1).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("email");
        assertThat(exception2).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("email");
        assertThat(exception3).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("email");
        assertThat(exception4).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("email");
    }

    @Test
    public void createPatient_patientCreatedSuccessfully()
    {
        //given
        Patient patient = createPatient("aaa@gmail.com");
        PatientRepository repository = mock(PatientRepository.class);
        PatientService service = mock(PatientService.class);
        //when
        doAnswer(invocationOnMock -> {
            service.mapper = repository;
            return null;
        }).when(service).createSession();
        when(service.mapper.save(any())).thenReturn(true);

        //then
        assertThat(service.createPatient(patient)).isFalse();
    }


    public Patient createPatient(String email)
    {
        Patient toReturn = Patient.builder()
                .phoneNumber("123456789")
                .pesel("12345678912")
                .email(email)
                .build();
        return toReturn;
    }
}
