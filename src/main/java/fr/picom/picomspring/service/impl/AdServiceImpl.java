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

    public Ad findById(Long id){
        return adDAO.findById(id).orElse(null);
    }

    public List<Ad> filterByUserAndTitleContain(User user, String title){
        return adDAO.findAllByTitleContainsAndUser(title, user);
    }

    @Override
    public List<Ad> findAllByUser(User user) {
        return adDAO.findAdByUser(user);
    }

    public boolean deleteById(Long id){
        if (adDAO.existsById(id)){
            adDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Ad update(Ad ad){
        if (adDAO.existsById(ad.getId())){
            return adDAO.save(ad);
        } else {
            return null;
        }
    }
}
