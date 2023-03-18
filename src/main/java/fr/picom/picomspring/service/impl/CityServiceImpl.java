package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.CityDAO;
import fr.picom.picomspring.model.City;
import fr.picom.picomspring.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private CityDAO cityDAO;

    public CityServiceImpl(CityDAO cityDAO) {
        super();
        this.cityDAO = cityDAO;
    }

    public City add(City city) {
        return cityDAO.save(city);
    }

    public List<City> findAll() {
        return cityDAO.findAll();
    }

    public City findById(Long id){
        return cityDAO.findById(id).orElse(null);
    }

    public City findByName(String name){
        return cityDAO.findByName(name);
    }

    public boolean deleteById(Long id){
        if (cityDAO.existsById(id)){
            cityDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public City update(City city){
        if (cityDAO.existsById(city.getId())){
            return cityDAO.save(city);
        } else {
            return null;
        }
    }
}
