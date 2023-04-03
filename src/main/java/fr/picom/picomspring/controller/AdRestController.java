package fr.picom.picomspring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.dto.AdAreaDTO;
import fr.picom.picomspring.dto.AdDTO;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.AdArea;
import fr.picom.picomspring.model.TimeInterval;
import fr.picom.picomspring.service.AdService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AdRestController {

    private AdService adService;

    private ObjectMapper objectMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PostMapping("/ad/test/upload")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Ad testImage(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long id,
            @RequestParam("text") String text,
            @RequestParam("startAt") String localDate,
            @RequestParam("numDaysOfDiffusion") Integer numDaysOfDiffusion,
            @RequestParam("adAreaDTOList") String adAreaDTOString
    ) throws JsonProcessingException {

        List<AdAreaDTO> adAreaDTOList = objectMapper.readValue(adAreaDTOString, new TypeReference<List<AdAreaDTO>>() {});

        LocalDate startAt = LocalDate.parse(localDate);
        AdDTO adDTO = new AdDTO(title, null, text, LocalDate.now(), startAt, numDaysOfDiffusion, id, adAreaDTOList);
        /*List<AdAreaDTO> adAreaDTOList = new ArrayList<>();
        AdAreaDTO adAreaDTO = new AdAreaDTO();
        adAreaDTO.setAreaId(1L);
        List<Long> timeIntervalList = new ArrayList<>();
        timeIntervalList.add(2L);
        timeIntervalList.add(5L);
        adAreaDTO.setTimeIntervalIdList(timeIntervalList);
        adAreaDTOList.add(adAreaDTO);

        System.out.println("Print adArea --> " + objectMapper.writeValueAsString(adAreaDTOList));*/
        return adService.createNewAd(adDTO, file);
    }

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
    public Ad addNewAd(@RequestParam("file") MultipartFile file, @RequestBody AdDTO adDTO){

        return adService.createNewAd(adDTO, file);
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

