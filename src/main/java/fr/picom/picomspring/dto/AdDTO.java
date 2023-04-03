package fr.picom.picomspring.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.picom.picomspring.model.AdArea;
import fr.picom.picomspring.model.User;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class AdDTO {

    @NotEmpty
    private String title;

    private String image;

    private String text;

    private LocalDate createdAt;

    private LocalDate startAt;

    @NotNull
    private Integer numDaysOfDiffusion;

    @NotNull
    private Long userId;

    private List<AdAreaDTO> adAreaDTOList;

    public AdDTO() {
    }

    public AdDTO(String title, String image, String text, LocalDate createdAt, LocalDate startAt, Integer numDaysOfDiffusion, Long userId, List<AdAreaDTO> adAreaDTOList) {
        this.title = title;
        this.image = image;
        this.text = text;
        this.createdAt = createdAt;
        this.startAt = startAt;
        this.numDaysOfDiffusion = numDaysOfDiffusion;
        this.userId = userId;
        this.adAreaDTOList = adAreaDTOList;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<AdAreaDTO> getAdAreaDTOList() {
        return adAreaDTOList;
    }

    public void setAdAreaDTOList(List<AdAreaDTO> adAreaDTOList) {
        this.adAreaDTOList = adAreaDTOList;
    }
}
