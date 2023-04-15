package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.Country;
import fr.picom.picomspring.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/country")
public class CountryRestController {

    private CountryService countryService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("")
    public List<Country> getAllCountry(){
        return countryService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/{name}")
    public Country findCountryByName(@PathVariable String name){
        return countryService.findByName(name);
    }

    /*@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Country addNewCountry(@RequestBody Country country){
        return countryService.add(country);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteCountryById(@PathVariable Long id){
        return countryService.deleteById(id);
    }

    /*@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PatchMapping("")
    public Country updateCountry(@RequestBody Country country){
        return countryService.update(country);
    }*/


}
