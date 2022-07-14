package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.Stop;
import fr.picom.picomspring.service.StopService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stop")
public class StopRestController {

    private StopService stopService;

    @GetMapping("/")
    public List<Stop> getAllStop(){
        return stopService.findAll();
    }

    @GetMapping("/{id}")
    public Stop getStopById(@PathVariable Long id){
        return stopService.finById(id);
    }

    @PostMapping("/")
    public Stop addNewStop(@RequestBody Stop stop){
        return stopService.add(stop);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStopById(@PathVariable Long id){
        return stopService.deleteById(id);
    }

    @PatchMapping("/")
    public Stop updateStop(@RequestBody Stop stop){
        return stopService.update(stop);
    }
}
