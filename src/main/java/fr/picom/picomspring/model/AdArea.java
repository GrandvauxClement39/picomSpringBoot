package fr.picom.picomspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class AdArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Ad ad;

    @ManyToOne
    @JsonBackReference
    private Area area;

    @ManyToMany
    @JsonManagedReference
    private List<TimeInterval> timeIntervalList;

    public AdArea() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<TimeInterval> getTimeIntervalList() {
        return timeIntervalList;
    }

    public void setTimeIntervalList(List<TimeInterval> timeIntervalList) {
        this.timeIntervalList = timeIntervalList;
    }

    @Override
    public String toString() {
        return "AdArea{" +
                "id=" + id +
                ", ad=" + ad +
                ", area=" + area +
                ", timeIntervalList=" + timeIntervalList +
                '}';
    }
}
