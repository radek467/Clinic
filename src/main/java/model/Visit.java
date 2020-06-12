package model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Visit {
    private int id;
    private LocalDateTime date;
    private String description;
    private int doctorId;
    private int patientId;
    private int serviceId;
    private float rating;

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "Visit{" +
                id +
                ". date = " + date +
                ", description = '" + description;
    }
}
