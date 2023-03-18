package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.StopDAO;
import fr.picom.picomspring.model.Stop;
import fr.picom.picomspring.service.StopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopServiceImpl implements StopService {

    private StopDAO stopDAO;

    public StopServiceImpl(StopDAO stopDAO) {
        super();
        this.stopDAO = stopDAO;
    }

    public Stop add(Stop stop) {
        return stopDAO.save(stop);
    }

    public List<Stop> findAll() {
        return stopDAO.findAll();
    }

    public Stop findById(Long id){
        return stopDAO.findById(id).orElse(null);
    }

    public Stop findByName(String name){
        return stopDAO.findByName(name);
    }

    public boolean deleteById(Long id){
        if (stopDAO.existsById(id)){
            stopDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Stop update(Stop stop){
        if (stopDAO.existsById(stop.getId())){
            return stopDAO.save(stop);
        } else {
            return null;
        }
    }
}
