package fr.picom.picomspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @JsonManagedReference
    private List<AdArea> adAreaList;

    public Ad() {
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

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", startAt=" + startAt +
                ", numDaysOfDiffusion=" + numDaysOfDiffusion +
                ", user=" + user +
                ", adAreaList=" + adAreaList +
                '}';
    }
}
