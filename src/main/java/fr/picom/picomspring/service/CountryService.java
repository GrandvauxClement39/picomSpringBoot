package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Country;

public interface CountryService extends GlobalService<Country, Long>{

    Country findByName(String name);
}
