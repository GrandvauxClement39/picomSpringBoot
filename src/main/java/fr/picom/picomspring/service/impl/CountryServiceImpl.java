package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.CountryDAO;
import fr.picom.picomspring.model.Country;
import fr.picom.picomspring.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryDAO countryDAO;

    public CountryServiceImpl(CountryDAO countryDAO) {
        super();
        this.countryDAO = countryDAO;
    }

    public Country add(Country country) {
        return countryDAO.save(country);
    }

    public List<Country> findAll() {
        return countryDAO.findAll();
    }

    public Country finById(Long id){
        return countryDAO.findById(id).orElse(null);
    }

    public Country findByName(String name){
        return countryDAO.findByName(name);
    }

    public boolean deleteById(Long id){
        if (countryDAO.existsById(id)){
            countryDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Country update(Country country){
        if (countryDAO.existsById(country.getId())){
            return countryDAO.save(country);
        } else {
            return null;
        }
    }
}
