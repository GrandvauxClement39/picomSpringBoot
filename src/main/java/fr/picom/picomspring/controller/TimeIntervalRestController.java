package fr.picom.picomspring.controller;

import fr.picom.picomspring.dto.TimeIntervalDTO;
import fr.picom.picomspring.model.TimeInterval;
import fr.picom.picomspring.service.TimeIntervalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/timeInterval")
public class TimeIntervalRestController {

    private TimeIntervalService timeIntervalService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("")
    public List<TimeInterval> getAllTimeInterval(){
        return timeIntervalService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/available-by-area/{idArea}")
    public List<TimeInterval> getTimeIntervalAvailableByArea(@PathVariable Long idArea){
        return timeIntervalService.getTimeIntervalAvailableForArea(idArea);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public TimeInterval getTimeIntervalById(@PathVariable Long id){
        return timeIntervalService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TimeInterval addNewTimeInterval(@RequestBody TimeIntervalDTO timeIntervalDTO) {

        TimeInterval timeInterval = new TimeInterval();
        timeInterval.setCoefMulti(timeIntervalDTO.getCoefMulti());
        timeInterval.setTimeSlot(timeIntervalDTO.getTimeSlot());
        return timeIntervalService.add(timeInterval);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteTimeIntervalById(@PathVariable Long id){
        return timeIntervalService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public TimeInterval updateTimeInterval(@RequestBody TimeIntervalDTO timeIntervalDTO){
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.setCoefMulti(timeIntervalDTO.getCoefMulti());
        timeInterval.setTimeSlot(timeIntervalDTO.getTimeSlot());
        timeInterval.setId(timeIntervalDTO.getId());

        return timeIntervalService.update(timeInterval);
    }

}
