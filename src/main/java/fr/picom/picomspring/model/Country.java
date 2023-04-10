package fr.picom.picomspring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneIndicative;

    @JsonManagedReference
    @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
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

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneIndicative='" + phoneIndicative + '\'' +
                '}';
    }
}
