package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Stop;

import java.util.List;

public interface StopService {

    Stop add(Stop stop);

    List<Stop> findAll();

    Stop finById(Long id);

    Stop findByName(String name);

    boolean deleteById(Long id);

    Stop update(Stop stop);
}
