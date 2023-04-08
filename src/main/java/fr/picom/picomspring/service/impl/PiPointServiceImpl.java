package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.AdAreaDao;
import fr.picom.picomspring.dao.StopDAO;
import fr.picom.picomspring.dao.TimeIntervalDAO;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.AdArea;
import fr.picom.picomspring.model.Stop;
import fr.picom.picomspring.model.TimeInterval;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PiPointServiceImpl {

    private AdAreaDao adAreaDao;

    private StopDAO stopDAO;

    private TimeIntervalDAO timeIntervalDAO;

    public PiPointServiceImpl(AdAreaDao adAreaDao, StopDAO stopDAO, TimeIntervalDAO timeIntervalDAO) {
        this.adAreaDao = adAreaDao;
        this.stopDAO = stopDAO;
        this.timeIntervalDAO = timeIntervalDAO;
    }

    public List<Ad> findAdByIpStopAndTimeInterval(String addressIp){
        Stop stop = stopDAO.findByAddressIp(addressIp);

        LocalDateTime localDateTime = LocalDateTime.now();
        String actualTimeInterval = localDateTime.getHour() + "-" + localDateTime.plusHours(1L).getHour();

        TimeInterval timeInterval = timeIntervalDAO.findByTimeSlot(actualTimeInterval);
        List<AdArea> adAreaList = adAreaDao.findAllByAreaAndTimeIntervalListContains(stop.getArea(), timeInterval);

        List<Ad> adListActive = new ArrayList<>();
        LocalDate dateNow = LocalDate.now();

        for (AdArea adArea: adAreaList){
            LocalDate dateEnd = adArea.getAd().getStartAt().plusDays(adArea.getAd().getNumDaysOfDiffusion());

            if (adArea.getAd().getStartAt().compareTo(dateNow) <= 0 && dateEnd.compareTo(dateNow) > 0){
                adListActive.add(adArea.getAd());
            }
        }
        return adListActive;
    }
}
