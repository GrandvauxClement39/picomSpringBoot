package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopDAO extends JpaRepository<Stop, Long> {
}