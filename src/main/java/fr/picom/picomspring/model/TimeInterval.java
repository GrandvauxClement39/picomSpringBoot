package fr.picom.picomspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class TimeInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String timeSlot;

    private Double coefMulti;

    @JsonIgnore
    @ManyToMany(mappedBy = "timeIntervalList")
    private List<AdArea> adList;

    public TimeInterval() {
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

    public List<AdArea> getAdList() {
        return adList;
    }

    public void setAdList(List<AdArea> adList) {
        this.adList = adList;
    }

    @Override
    public String toString() {
        return "TimeInterval{" +
                "id=" + id +
                ", timeSlot='" + timeSlot + '\'' +
                ", coefMulti=" + coefMulti +
                ", adList=" + adList +
                '}';
    }
}
