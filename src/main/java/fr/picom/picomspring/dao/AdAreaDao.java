package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.AdArea;
import fr.picom.picomspring.model.Area;
import fr.picom.picomspring.model.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdAreaDao extends JpaRepository<AdArea, Long> {

    List<AdArea> findAllByAreaAndTimeIntervalListContains(Area area, TimeInterval timeInterval);
}
