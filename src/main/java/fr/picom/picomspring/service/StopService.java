package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Stop;

import java.util.List;

public interface StopService extends GlobalService<Stop, Long> {

    Stop findByName(String name);
}
