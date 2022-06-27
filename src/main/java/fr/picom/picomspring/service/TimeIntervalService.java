package fr.picom.picomspring.service;


import fr.picom.picomspring.model.TimeInterval;

import java.util.List;

public interface TimeIntervalService {

    TimeInterval add(TimeInterval timeInterval);

    List<TimeInterval> findAll();

    TimeInterval finById(Long id);

    boolean deleteById(Long id);

    TimeInterval update(TimeInterval timeInterval);
}
