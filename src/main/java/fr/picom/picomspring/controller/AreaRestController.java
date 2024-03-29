package fr.picom.picomspring.controller;

import fr.picom.picomspring.dto.AreaDTO;
import fr.picom.picomspring.model.Area;
import fr.picom.picomspring.service.AreaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/area")
public class AreaRestController {

    private AreaService areaService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("")
    public List<Area> getAllArea(){

        return areaService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public Area getAreaById(@PathVariable Long id){
        return areaService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Area addNewArea(@RequestBody AreaDTO areaDTO){

        Area area = new Area();
        area.setName(areaDTO.getName());
        area.setPrice(areaDTO.getPrice());

        return areaService.add(area);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteAreaById(@PathVariable Long id){
        return areaService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public Area updateArea(@RequestBody AreaDTO areaDTO){
        Area area = areaService.findById(areaDTO.getId());
        area.setPrice(areaDTO.getPrice());
        area.setName(areaDTO.getName());

        return areaService.update(area);
    }
}
