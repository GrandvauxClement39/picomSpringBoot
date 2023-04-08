package fr.picom.picomspring.controller;

import fr.picom.picomspring.dto.StopDTO;
import fr.picom.picomspring.model.Stop;
import fr.picom.picomspring.service.AreaService;
import fr.picom.picomspring.service.StopService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stop")
public class StopRestController {

    private StopService stopService;

    private AreaService areaService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("")
    public List<Stop> getAllStop(){
        return stopService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Stop addNewStop(@RequestBody StopDTO stopDTO){
        Stop stop = new Stop();
        stop.setArea(areaService.findById(stopDTO.getAreaId()));
        stop.setLongitude(stopDTO.getLongitude());
        stop.setLatitude(stopDTO.getLatitude());
        stop.setName(stopDTO.getName());
        stop.setAddressIp(stopDTO.getAddressIp());

        return stopService.add(stop);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public Stop getStopById(@PathVariable Long id){
        return stopService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteStopById(@PathVariable Long id){
        return stopService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public Stop updateStop(@RequestBody StopDTO stopDTO){
        Stop stop = new Stop();
        stop.setArea(areaService.findById(stopDTO.getAreaId()));
        stop.setLongitude(stopDTO.getLongitude());
        stop.setLatitude(stopDTO.getLatitude());
        stop.setName(stopDTO.getName());
        stop.setAddressIp(stopDTO.getAddressIp());
        stop.setId(stopDTO.getId());

        return stopService.update(stop);
    }
}
