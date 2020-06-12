package model;

import lombok.Getter;

@Getter
public class Specialization {
    private int id;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return  id +
                ". " + type;
    }
}
