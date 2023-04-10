package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.AdAreaDao;
import fr.picom.picomspring.dao.AdDAO;
import fr.picom.picomspring.dto.AdAreaDTO;
import fr.picom.picomspring.dto.AdDTO;
import fr.picom.picomspring.exceptions.FileUploadException;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.AdArea;
import fr.picom.picomspring.model.TimeInterval;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    private AdDAO adDAO;

    private AdAreaDao adAreaDao;

    private UserService userService;

    private AreaService areaService;

    private TimeIntervalService timeIntervalService;

    private FilesStorageService filesStorageService;

    public AdServiceImpl(AdDAO adDAO, UserService userService, AreaService areaService, TimeIntervalService timeIntervalService, FilesStorageService filesStorageService, AdAreaDao adAreaDao) {
        super();
        this.adDAO = adDAO;
        this.userService = userService;
        this.timeIntervalService = timeIntervalService;
        this.areaService = areaService;
        this.filesStorageService = filesStorageService;
        this.adAreaDao = adAreaDao;
    }

    public Ad createNewAd(AdDTO adDto, MultipartFile file) {

        Ad ad = new Ad();
        ad.setText(adDto.getText());
        try {
            if (file != null) {
                // Générer un nouvel identifiant unique pour le nouveau nom de fichier
                String newFilename = filesStorageService.generateNewFileName(file);
                filesStorageService.save(file, newFilename);
                filesStorageService.deleteByName("test");
                ad.setImage(newFilename);
            }
        } catch (FileUploadException e){
            throw new FileUploadException("Problème lors du téléchargement du fichier");
        }

        ad.setUser(userService.findById(adDto.getUserId()));
        ad.setTitle(adDto.getTitle());
        ad.setCreatedAt(LocalDate.now());
        ad.setStartAt(adDto.getStartAt());
        ad.setNumDaysOfDiffusion(adDto.getNumDaysOfDiffusion());

        // Save AD in DB
        Ad adSave = adDAO.save(ad);
        // Add AdArea info in DB
        List<AdArea> adAreaList = new ArrayList<>();
        for (AdAreaDTO adAreaDTO : adDto.getAdAreaDTOList()){
            AdArea adArea = new AdArea();
            adArea.setAd(adSave);
            adArea.setArea(areaService.findById(adAreaDTO.getAreaId()));

            List<TimeInterval> timeIntervalList = new ArrayList<>();
            for (Long idTimeInterval : adAreaDTO.getTimeIntervalIdList()){
                timeIntervalList.add(timeIntervalService.findById(idTimeInterval));
            }
            adArea.setTimeIntervalList(timeIntervalList);

            adAreaList.add(adAreaDao.save(adArea));
        }
        adSave.setAdAreaList(adAreaList);
        return adSave;
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
    public List<Ad> findAllByUser(Long id) {
        User user = userService.findById(id);
        return adDAO.findAdByUserOrderByCreatedAtDesc(user);
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
