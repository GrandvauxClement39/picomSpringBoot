package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Area;
import fr.picom.picomspring.model.TimeInterval;

import java.util.List;

public interface TimeIntervalService extends GlobalService<TimeInterval, Long> {
    List<TimeInterval> getTimeIntervalAvailableForArea(Long areaId);
}
