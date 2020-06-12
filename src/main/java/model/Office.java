package model;

import lombok.Getter;

@Getter
class Office {
    private int officeId;
    private int roomNumber;

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
