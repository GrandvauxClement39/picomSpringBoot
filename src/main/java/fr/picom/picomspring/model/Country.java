package fr.picom.picomspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneIndicative;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<City> cityList;

    public Country() {
    }

    public Country(String name, String phoneIndicative) {
        this.name = name;
        this.phoneIndicative = phoneIndicative;
    }

    public Country(Long id, String name, String phoneIndicative) {
        this.id = id;
        this.name = name;
        this.phoneIndicative = phoneIndicative;
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

    public String getPhoneIndicative() {
        return phoneIndicative;
    }

    public void setPhoneIndicative(String phoneIndicative) {
        this.phoneIndicative = phoneIndicative;
    }

}
