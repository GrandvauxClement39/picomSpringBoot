package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.AreaDAO;
import fr.picom.picomspring.model.Area;
import fr.picom.picomspring.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    private AreaDAO areaDAO;

    public AreaServiceImpl(AreaDAO areaDAO) {
        super();
        this.areaDAO = areaDAO;
    }

    public Area add(Area area) {
        return areaDAO.save(area);
    }

    public List<Area> findAll() {
        return areaDAO.findAll();
    }

    public Area finById(Long id){
        return areaDAO.findById(id).orElse(null);
    }

    public Area findByName(String name){
        return areaDAO.findByName(name);
    }

    public boolean deleteById(Long id){
        if (areaDAO.existsById(id)){
            areaDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Area update(Area area){
        if (areaDAO.existsById(area.getId())){
            return areaDAO.save(area);
        } else {
            return null;
        }
    }
}
