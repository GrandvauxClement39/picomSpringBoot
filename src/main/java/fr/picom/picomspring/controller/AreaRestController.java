package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.Area;
import fr.picom.picomspring.service.AreaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/area")
public class AreaRestController {

    private AreaService areaService;

    @GetMapping("/")
    public List<Area> getAllArea(){

        List<Area> allArea = areaService.findAll();
        System.out.println(allArea);
        return allArea;
    }

    @GetMapping("/{id}")
    public Area getAreaById(@PathVariable Long id){
        return areaService.finById(id);
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Area addNewArea(@RequestBody Area area){
        return areaService.add(area);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAreaById(@PathVariable Long id){
        return areaService.deleteById(id);
    }

    @PatchMapping("/")
    public Area updateArea(@RequestBody Area area){
        return areaService.update(area);
    }
}
