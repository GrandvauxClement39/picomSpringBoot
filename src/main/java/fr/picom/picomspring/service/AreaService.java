package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Area;

public interface AreaService extends GlobalService<Area, Long> {

    Area findByName(String name);
}
