package com.example.eventpoc.event;

import com.example.eventpoc.common.SpecificException;
import com.example.eventpoc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {


    private final AppEventRepository eventRepository;

    private final UserService userService;

    @Autowired
    public EventService(AppEventRepository appEventRepository, UserService userRepository) {
        this.eventRepository = appEventRepository;
        this.userService = userRepository;
    }

    public AppEvent create(AppEvent defaultEvent) {
        return eventRepository.save(defaultEvent);
    }

    public Optional<AppEvent> getById(long id) {
        return eventRepository.findById(id);
    }

    public Optional<AppEvent> getByObj(AppEvent event) {
        if(event == null){
            return Optional.empty();
        }
        return eventRepository.findById(event.getId());
    }

    public void update(AppEvent event) {
        if(event.getId() == 0){
            throw new RuntimeException("Exception while updating an event. Trying to update an event that is not possible without id.");
        }
        eventRepository.save(event);
    }

    public void delete(long eventId) {
        eventRepository.deleteById(eventId);
    }

    public void delete(AppEvent event) {
        if(event.getId() == 0){
            throw new RuntimeException("Exception while deleting an event. Trying to delete an event that is not possible without id.");
        }
        eventRepository.delete(event);
    }

    public List<AppEvent> getAll() {
        return eventRepository.findAll();
    }

    public List<AppEvent> getById(Long... ids) {
        return eventRepository.findAllById(Arrays.asList(ids));
    }

    public List<AppEvent> get(List<AppEvent> events) {
        List<Long> ids = events.stream().map(e -> e.getId()).collect(Collectors.toList());
        return eventRepository.findAllById(ids);
    }

    public List<AppEvent> get(AppEvent... events) {
        List<Long> ids = Arrays.asList(events).stream().map(e -> e.getId()).collect(Collectors.toList());
        return eventRepository.findAllById(ids);
    }

    public void delete(List<AppEvent> events){
        eventRepository.deleteAll(events);
    }

    /* ***************
     * BUSINESS LOGIC
     ****************/
    //TODO Tets für Methode
    //TODO ist das hier korrekt? alles oder nichts für die Methode
    @Transactional
    public AppEvent participate(Long eventId, Long userId) {
        var event = this.getById(eventId);
        var user = userService.getUserById(userId);

        //TODO bidirectional!(? best practice book schauen)
        if(event.isPresent() && user.isPresent()){
            event.get().getParticipants().add(user.get());
            this.update(event.get());
            return this.getById(eventId).get();

        }

        if(user.isEmpty()) {
            throw new SpecificException("Teilnahme-Versuch eines Users an einem Event. Event mit dieser Id ("+eventId+") wurde nicht gefunden");
        }

        if(user.isEmpty()) {
            throw new SpecificException.UserNotFoundException("Teilnahme-Versuch eines Users an einem Event. User mit dieser Id ("+userId+") wurde nicht gefunden");
        }

        return null;
    }

    //TODO Flexible Suche mit criteria or specification api
}
