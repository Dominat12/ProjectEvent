package com.example.eventpoc.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {


    private final AppEventRepository eventRepository;

    @Autowired
    public EventService(AppEventRepository appEventRepository) {
        this.eventRepository = appEventRepository;
    }

    public AppEvent create(AppEvent defaultEvent) {
        return eventRepository.save(defaultEvent);
    }

    public Optional<AppEvent> getById(long id) {
        return eventRepository.findById(id);
    }

    public Optional<AppEvent> getByObj(AppEvent eventWithName) {
        if(eventWithName == null){
            return Optional.empty();
        }
        return eventRepository.findById(eventWithName.getId());
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

    //TODO Flexible Suche mit criteria or specification api
}
