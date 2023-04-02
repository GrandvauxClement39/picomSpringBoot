package fr.picom.picomspring.controller;

import fr.picom.picomspring.dto.AdDTO;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.service.AdService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AdRestController {

    private AdService adService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/ads")
    public List<Ad> getAllAds(){
        return adService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/user/ads")
    public List<Ad> getAllAdsByUser(){
        return adService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/ad/{id}")
    public Ad getAdById(@PathVariable Long id){
        return adService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PostMapping("/ad")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Ad addNewAd(@RequestBody AdDTO adDTO){
        return adService.createNewAd(adDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @DeleteMapping("/ad/{id}")
    public boolean deleteAdById(@PathVariable Long id){
        return adService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PatchMapping("/ad")
    public Ad updateAd(@RequestBody Ad ad){
        return adService.update(ad);
    }
}

