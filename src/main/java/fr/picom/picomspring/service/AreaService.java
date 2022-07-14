package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Area;

import java.util.List;

public interface AreaService {

    Area add(Area area);

    List<Area> findAll();

    Area finById(Long id);

    Area findByName(String name);

    boolean deleteById(Long id);

    Area update(Area area);
}
