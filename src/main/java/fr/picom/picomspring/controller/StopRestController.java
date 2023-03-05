package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.Stop;
import fr.picom.picomspring.service.StopService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class StopRestController {

    private StopService stopService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/stop")
    public List<Stop> getAllStop(){
        return stopService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.CREATED)
    public Stop addNewStop(@RequestBody Stop stop){
        return stopService.add(stop);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/stop/{id}")
    public Stop getStopById(@PathVariable Long id){
        return stopService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/stop/{id}")
    public boolean deleteStopById(@PathVariable Long id){
        return stopService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/stop")
    public Stop updateStop(@RequestBody Stop stop){
        return stopService.update(stop);
    }
}
