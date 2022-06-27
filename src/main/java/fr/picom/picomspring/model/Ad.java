package fr.picom.picomspring.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String image;

    @Lob
    private String text;

    private LocalDate createdAt;

    private LocalDate startAt;

    private Integer numDaysOfDiffusion;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "ad")
    private List<AdArea> adAreaList;

}
