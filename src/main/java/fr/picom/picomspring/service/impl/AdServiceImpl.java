package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.AdDAO;
import fr.picom.picomspring.dto.AdAreaDTO;
import fr.picom.picomspring.dto.AdDTO;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.AdArea;
import fr.picom.picomspring.model.TimeInterval;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.AdService;
import fr.picom.picomspring.service.AreaService;
import fr.picom.picomspring.service.TimeIntervalService;
import fr.picom.picomspring.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    private AdDAO adDAO;

    private UserService userService;

    private AreaService areaService;

    private TimeIntervalService timeIntervalService;

    public AdServiceImpl(AdDAO adDAO, UserService userService, AreaService areaService, TimeIntervalService timeIntervalService) {
        super();
        this.adDAO = adDAO;
        this.userService = userService;
        this.timeIntervalService = timeIntervalService;
        this.areaService = areaService;
    }

    public Ad createNewAd(AdDTO adDto) {
        Ad ad = new Ad();
        ad.setText(adDto.getText());

        ad.setImage(adDto.getImage());
        ad.setUser(userService.finById(adDto.getUserId()));
        ad.setTitle(adDto.getTitle());
        ad.setCreatedAt(LocalDate.now());
        ad.setStartAt(adDto.getStartAt());
        ad.setNumDaysOfDiffusion(adDto.getNumDaysOfDiffusion());


        List<AdArea> adAreaList = new ArrayList<>();
        for (AdAreaDTO adAreaDTO : adDto.getAdAreaDTOList()){
            AdArea adArea = new AdArea();
            adArea.setAd(ad);
            adArea.setArea(areaService.findById(adAreaDTO.getAreaId()));

            List<TimeInterval> timeIntervalList = new ArrayList<>();
            for (Long idTimeInterval : adAreaDTO.getTimeIntervalIdList()){
                timeIntervalList.add(timeIntervalService.findById(idTimeInterval));
            }
            adArea.setTimeIntervalList(timeIntervalList);

            adAreaList.add(adArea);
        }

        ad.setAdAreaList(adAreaList);

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

    public Ad add(Ad entity) {
        return null;
    }
}
