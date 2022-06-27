package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdDAO extends JpaRepository<Ad, Long> {
}