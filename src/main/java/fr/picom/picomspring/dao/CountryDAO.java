package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryDAO extends JpaRepository<Country, Long> {

    Country findByName(String name);
}