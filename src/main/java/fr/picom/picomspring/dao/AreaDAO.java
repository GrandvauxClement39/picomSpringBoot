package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaDAO extends JpaRepository<Area, Long> {

    Area findByName(String name);
}