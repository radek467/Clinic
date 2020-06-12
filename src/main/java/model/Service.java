package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public class Service {
    private int id;
    private String title;
    private float price;
    private int duration;
    private int specializationId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    @Override
    public String toString() {
        return "" +
                id +
                ". price = " + price +
                ", duration = " + duration;
    }
}
