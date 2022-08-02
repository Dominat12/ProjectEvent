package com.example.eventpoc.event;


import com.example.eventpoc.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
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

    @PostMapping("/event")
    public AppEvent participate(@RequestBody AppEvent event){
        return eventService.create(event);
    }


    @PutMapping("/participate/{eventId}/{userId}")
    public AppEvent participate(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId){
        return eventService.participate(eventId, userId);
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
