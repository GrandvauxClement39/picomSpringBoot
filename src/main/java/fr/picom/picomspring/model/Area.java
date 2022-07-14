package fr.picom.picomspring.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private Double price;

    @OneToMany(mappedBy = "area")
    private List<Stop> stopList;

    @OneToMany(mappedBy = "area")
    private List<AdArea> adAreaList;

    public Area() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Stop> getStopList() {
        return stopList;
    }

    public void setStopList(List<Stop> stopList) {
        this.stopList = stopList;
    }

    public List<AdArea> getAdAreaList() {
        return adAreaList;
    }

    public void setAdAreaList(List<AdArea> adAreaList) {
        this.adAreaList = adAreaList;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stopList=" + stopList +
                ", adAreaList=" + adAreaList +
                '}';
    }
}
