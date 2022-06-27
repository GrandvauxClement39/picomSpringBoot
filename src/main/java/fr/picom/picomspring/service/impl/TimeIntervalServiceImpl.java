package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.TimeIntervalDAO;
import fr.picom.picomspring.model.TimeInterval;
import fr.picom.picomspring.service.TimeIntervalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeIntervalServiceImpl implements TimeIntervalService {

    private TimeIntervalDAO timeIntervalDAO;

    public TimeIntervalServiceImpl(TimeIntervalDAO timeIntervalDAO) {
        super();
        this.timeIntervalDAO = timeIntervalDAO;
    }

    public TimeInterval add(TimeInterval timeInterval) {
       return timeIntervalDAO.save(timeInterval);
    }

    public List<TimeInterval> findAll() {
        return timeIntervalDAO.findAll();
    }

    public TimeInterval finById(Long id){
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
}
