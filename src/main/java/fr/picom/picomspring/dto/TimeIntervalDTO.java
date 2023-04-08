package fr.picom.picomspring.dto;

public class TimeIntervalDTO {

    private Long id;

    private String timeSlot;

    private Double coefMulti;

    public TimeIntervalDTO(Long id, String timeSlot, Double coefMulti) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.coefMulti = coefMulti;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Double getCoefMulti() {
        return coefMulti;
    }

    public void setCoefMulti(Double coefMulti) {
        this.coefMulti = coefMulti;
    }
}
