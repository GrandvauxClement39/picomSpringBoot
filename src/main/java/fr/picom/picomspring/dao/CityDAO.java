package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = true)
public interface CityDAO extends JpaRepository<City, Long> {

    City findByName(String name);
}