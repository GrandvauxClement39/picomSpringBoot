package fr.picom.picomspring.service;

import fr.picom.picomspring.model.City;

public interface CityService extends GlobalService<City, Long> {

    City findByName(String name);
}
