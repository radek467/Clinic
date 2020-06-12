package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    private int id;
    private String name;
    private String surname;
    private int specializationId;
    private int officeId;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", specializationId=" + specializationId +
                ", officeId=" + officeId +
                '}';
    }
}
