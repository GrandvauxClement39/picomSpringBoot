package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.TimeInterval;
import fr.picom.picomspring.service.TimeIntervalService;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
public class TimeIntervalRestController {

    private TimeIntervalService timeIntervalService;

    @GetMapping("timeInterval")
    public List<TimeInterval> getAllTimeInterval(){
        return timeIntervalService.findAll();
    }

    @GetMapping("timeInterval/{id}")
    public TimeInterval getTimeIntervalById(@PathVariable Long id){
        return timeIntervalService.finById(id);
    }

    @PostMapping("timeInterval")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TimeInterval addNewTimeInterval(@RequestBody TimeInterval timeInterval) {
        // TODO METHOD TO GET DATA FROM BODY
        System.out.println("--------------- "+timeInterval);
       /* TimeInterval timeInterval = new TimeInterval();
        timeInterval.setTimeSlot(timeSlot);
        timeInterval.setCoefMulti(coefMulti);*/
        return timeIntervalService.add(timeInterval);
    }

    @DeleteMapping("timeInterval/{id}")
    public boolean deleteTimeIntervalById(@PathVariable Long id){
        return timeIntervalService.deleteById(id);
    }

}
