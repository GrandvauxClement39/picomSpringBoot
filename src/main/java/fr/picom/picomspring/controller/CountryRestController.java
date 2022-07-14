package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.Country;
import fr.picom.picomspring.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/country")
public class CountryRestController {

    private CountryService countryService;

    @GetMapping("/")
    public List<Country> getAllCountry(){
        return countryService.findAll();
    }

    @GetMapping("/{name}")
    public Country findCountryByName(@PathVariable String name){
        return countryService.findByName(name);
    }

    @PostMapping("/")
    public Country addNewCountry(@RequestBody Country country){
        return countryService.add(country);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCountryById(@PathVariable Long id){
        return countryService.deleteById(id);
    }

    @PatchMapping("/")
    public Country updateCountry(@RequestBody Country country){
        return countryService.update(country);
    }


}
