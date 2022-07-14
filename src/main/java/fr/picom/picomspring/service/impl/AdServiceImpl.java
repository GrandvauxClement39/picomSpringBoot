package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.AdDAO;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.AdService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    private AdDAO adDAO;

    public AdServiceImpl(AdDAO adDAO) {
        super();
        this.adDAO = adDAO;
    }

    public Ad add(Ad ad) {
        return adDAO.save(ad);
    }

    public List<Ad> findAll() {
        return adDAO.findAll();
    }

    public Ad finById(Long id){
        return adDAO.findById(id).orElse(null);
    }

    public List<Ad> filterByUserAndTitleContain(User user, String title){
        return adDAO.findAllByTitleContainsAndUser(title, user);
    }

    public boolean deleteById(Long id){
        if (adDAO.existsById(id)){
            adDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Ad update(Ad Ad){
        if (adDAO.existsById(Ad.getId())){
            return adDAO.save(Ad);
        } else {
            return null;
        }
    }
}
