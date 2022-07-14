package fr.picom.picomspring.service;

import fr.picom.picomspring.model.City;

import java.util.List;

public interface CityService {

    City add(City city);

    List<City> findAll();

    City finById(Long id);

    City findByName(String name);

    boolean deleteById(Long id);

    City update(City City);
}
