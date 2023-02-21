package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.Stop;
import fr.picom.picomspring.service.StopService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class StopRestController {

    private StopService stopService;

    @GetMapping("/stop")
    public List<Stop> getAllStop(){
        return stopService.findAll();
    }

    @GetMapping("/stop/{id}")
    public Stop getStopById(@PathVariable Long id){
        return stopService.finById(id);
    }

    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.CREATED)
    public Stop addNewStop(@RequestBody Stop stop){
        return stopService.add(stop);
    }

    @DeleteMapping("/stop/{id}")
    public boolean deleteStopById(@PathVariable Long id){
        return stopService.deleteById(id);
    }

    @PatchMapping("/stop")
    public Stop updateStop(@RequestBody Stop stop){
        return stopService.update(stop);
    }
}
