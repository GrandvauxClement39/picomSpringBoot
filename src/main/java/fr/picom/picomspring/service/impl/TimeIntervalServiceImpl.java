package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.AdAreaDao;
import fr.picom.picomspring.dao.AreaDAO;
import fr.picom.picomspring.dao.TimeIntervalDAO;
import fr.picom.picomspring.model.AdArea;
import fr.picom.picomspring.model.Area;
import fr.picom.picomspring.model.TimeInterval;
import fr.picom.picomspring.service.TimeIntervalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeIntervalServiceImpl implements TimeIntervalService {

    private TimeIntervalDAO timeIntervalDAO;

    private AdAreaDao adAreaDao;

    private AreaDAO areaDAO;

    public TimeIntervalServiceImpl(TimeIntervalDAO timeIntervalDAO, AdAreaDao adAreaDao, AreaDAO areaDAO) {
        super();
        this.timeIntervalDAO = timeIntervalDAO;
        this.adAreaDao = adAreaDao;
        this.areaDAO = areaDAO;
    }

    public TimeInterval add(TimeInterval timeInterval) {
       return timeIntervalDAO.save(timeInterval);
    }

    public List<TimeInterval> findAll() {
        return timeIntervalDAO.findAll();
    }

    public TimeInterval findById(Long id){
        return timeIntervalDAO.findById(id).orElse(null);
    }

    public boolean deleteById(Long id){
        if (timeIntervalDAO.existsById(id)){
             timeIntervalDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public TimeInterval update(TimeInterval timeInterval){
        if (timeIntervalDAO.existsById(timeInterval.getId())){
           return timeIntervalDAO.save(timeInterval);
        } else {
            return null;
        }
    }

    public List<TimeInterval> getTimeIntervalAvailableForArea(Long areaId){
        List<TimeInterval> timeIntervalList = timeIntervalDAO.findAll();
        Area areaSelected = areaDAO.findById(areaId).orElse(null);
        if (areaSelected == null){
            return new ArrayList<>();
        } else {
            List<TimeInterval> timeIntervalAvailable = new ArrayList<>();
            for (TimeInterval timeInterval : timeIntervalList) {
                List<AdArea> adAreaList = adAreaDao.findAllByAreaAndTimeIntervalListContains(areaSelected, timeInterval);
                if (adAreaList.size() < 6){
                    timeIntervalAvailable.add(timeInterval);
                } else {
                    System.out.println("************************* ERROR MORe 6 in time interval " + timeInterval.getTimeSlot() + "for area " + areaSelected.getName());
                }
            }
            return timeIntervalAvailable;
        }
    }
}
