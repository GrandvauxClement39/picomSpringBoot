package fr.picom.picomspring.controller;

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

        List<Area> allArea = areaService.findAll();
        return allArea;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public Area getAreaById(@PathVariable Long id){
        return areaService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Area addNewArea(@RequestBody Area area){
        return areaService.add(area);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteAreaById(@PathVariable Long id){
        return areaService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public Area updateArea(@RequestBody Area area){
        return areaService.update(area);
    }
}
