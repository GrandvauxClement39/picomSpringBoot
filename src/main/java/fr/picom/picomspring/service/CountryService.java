package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Country;

import java.util.List;

public interface CountryService extends GlobalService<Country, Long>{

    Country findByName(String name);
}
