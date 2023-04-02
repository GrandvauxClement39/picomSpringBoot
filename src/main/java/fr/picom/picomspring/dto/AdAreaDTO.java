package fr.picom.picomspring.dto;

import java.util.List;

public class AdAreaDTO {

    private Long id;

    private Long areaId;

    private List<Long> timeIntervalIdList;

    public AdAreaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public List<Long> getTimeIntervalIdList() {
        return timeIntervalIdList;
    }

    public void setTimeIntervalIdList(List<Long> timeIntervalIdList) {
        this.timeIntervalIdList = timeIntervalIdList;
    }
}
