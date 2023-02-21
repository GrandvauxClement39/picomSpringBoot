package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdDAO extends JpaRepository<Ad, Long> {

    List<Ad> findAllByTitleContainsAndUser(String title, User user);

    List<Ad> findAdByUser(User user);
}