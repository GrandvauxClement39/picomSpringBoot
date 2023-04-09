package fr.picom.picomspring.service;

import fr.picom.picomspring.dto.AdDTO;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdService extends GlobalService<Ad, Long> {

    List<Ad> filterByUserAndTitleContain(User user, String title);

    List<Ad> findAllByUser(Long id);

    Ad createNewAd(AdDTO adDTO, MultipartFile file);
}
