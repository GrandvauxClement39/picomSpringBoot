package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeIntervalDAO extends JpaRepository<TimeInterval, Long> {
}