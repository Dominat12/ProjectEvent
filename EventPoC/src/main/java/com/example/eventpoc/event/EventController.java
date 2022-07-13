package com.example.eventpoc.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    /**
     * Gib alle Events mit allen Infos zurück
     * @return
     */
    @GetMapping("/all")
    public List<AppEvent> all() {
        return eventService.getAll();
    }




     /* ***************
     Test Endpoints
     **************** */

    /**
     * Erstellt einen Beispiel Event
     * und gibt die id des Events zurück
     *
     * @return
     */
    @PostMapping("/sample")
    public Long create() {
        return eventService.create(new AppEvent()).getId();
    }


}
