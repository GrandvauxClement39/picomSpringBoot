package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.service.AdService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AdRestController {

    private AdService adService;

    @GetMapping("/ads")
    public List<Ad> getAllAd(){
        return adService.findAll();
    }

    @GetMapping("/user/ads")
    public List<Ad> getAllAdByUSer(){
        return adService.findAll();
    }

    @GetMapping("/ad/{id}")
    public Ad getAdById(@PathVariable Long id){
        return adService.findById(id);
    }

    @PostMapping("/ad")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Ad addNewAd(@RequestBody Ad ad){
        return adService.add(ad);
    }

    @DeleteMapping("/ad/{id}")
    public boolean deleteAdById(@PathVariable Long id){
        return adService.deleteById(id);
    }

    @PatchMapping("/ad")
    public Ad updateAd(@RequestBody Ad ad){
        return adService.update(ad);
    }
}

