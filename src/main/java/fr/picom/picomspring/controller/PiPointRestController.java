package fr.picom.picomspring.controller;


import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.Stop;
import fr.picom.picomspring.service.StopService;
import fr.picom.picomspring.service.impl.PiPointServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pi-point")
public class PiPointRestController {

    private PiPointServiceImpl piPointService;

    private StopService stopService;

    @GetMapping(value = "/ads")
    public List<Ad> getAdsByAddressIpAndTimeSlot(@RequestParam String addressIp){
        return piPointService.findAdByIpStopAndTimeInterval(addressIp);
    }

    @GetMapping("/stop")
    public Stop getStopByAddressIp(@RequestParam String addressIp){
        return stopService.findByAddressIp(addressIp);
    }
}
