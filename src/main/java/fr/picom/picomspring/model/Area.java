package fr.picom.picomspring.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float price;

    @OneToMany(mappedBy = "area")
    private List<Stop> stopList;

    @OneToMany(mappedBy = "area")
    private List<AdArea> adAreaList;
}
