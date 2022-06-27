package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDAO extends JpaRepository<City, Long> {
}