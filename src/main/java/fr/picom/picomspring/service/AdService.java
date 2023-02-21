package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.User;

import java.util.List;

public interface AdService {
    Ad add(Ad ad);

    List<Ad> findAll();

    Ad finById(Long id);

    List<Ad> filterByUserAndTitleContain(User user, String title);

    List<Ad> findAllByUser(User user);

    boolean deleteById(Long id);

    Ad update(Ad ad);
}
