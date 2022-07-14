package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Country;

import java.util.List;

public interface CountryService {

    Country add(Country country);

    List<Country> findAll();

    Country finById(Long id);

    Country findByName(String name);

    boolean deleteById(Long id);

    Country update(Country country);
}
