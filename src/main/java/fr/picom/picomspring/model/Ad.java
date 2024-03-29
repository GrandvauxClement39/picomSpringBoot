package fr.picom.picomspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String title;

    private String image;

    @Lob
    private String text;

    private LocalDate createdAt;

    @NotNull
    private LocalDate startAt;

    @NotNull
    private Integer numDaysOfDiffusion;

    @NotNull
    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "ad")
    private List<AdArea> adAreaList;

    public Ad() {
        // This method is intentionally left blank as it will be implemented later.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDate startAt) {
        this.startAt = startAt;
    }

    public Integer getNumDaysOfDiffusion() {
        return numDaysOfDiffusion;
    }

    public void setNumDaysOfDiffusion(Integer numDaysOfDiffusion) {
        this.numDaysOfDiffusion = numDaysOfDiffusion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AdArea> getAdAreaList() {
        return adAreaList;
    }

    public void setAdAreaList(List<AdArea> adAreaList) {
        this.adAreaList = adAreaList;
    }

}
