package com.example.eventpoc;

import com.example.eventpoc.event.AppEvent;
import com.example.eventpoc.event.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Test
    public void testCreate(){
        /* Persistieren eines Default Events */
        AppEvent defaultEvent = new AppEvent();
        eventService.create(defaultEvent);
        assertTrue(defaultEvent.getId() != 0);

        /* CreatedAt liegt in der Vergangenheit */
        assertNotNull(defaultEvent.getCreatedAt());
        assertTrue(defaultEvent.getCreatedAt().isBefore(LocalDateTime.now()));

        /* Persistieren eines Events mit Werten */
        String eventName = "EventName";
        AppEvent eventWithName = new AppEvent();
        eventWithName.setName(eventName);
        eventService.create(eventWithName);
        assertTrue(eventWithName.getId() != 0);
        assertEquals(eventName, eventWithName.getName());


    }

    @Test
    public void testGetById(){
        /* Nicht vorhandener User wird nicht gefunden */
        AppEvent event = new AppEvent();
        var foundEvent = eventService.getById(event.getId());
        assertTrue(foundEvent.isEmpty());

        /* Persistiertes Event stimmt mit dem gefundenen Event überein */
        /* Name Property wird mitgeladen */
        String eventName="EventName";
        event.setName(eventName);
        eventService.create(event);
        foundEvent = eventService.getById(event.getId());
        assertTrue(foundEvent.isPresent());
        assertEquals(event.getId(), foundEvent.get().getId());
        assertEquals(eventName, foundEvent.get().getName());

    }

    @Test
    public void testGetByObj(){
        /* Nicht vorhandener User wird nicht gefunden */
        AppEvent event = new AppEvent();
        var foundEvent = eventService.getByObj(event);
        assertTrue(foundEvent.isEmpty());

        /* Persistiertes Event stimmt mit dem gefundenen Event überein */
        /* Name Property wird mitgeladen */
        String eventName="EventName";
        event.setName(eventName);
        eventService.create(event);
        foundEvent = eventService.getByObj(event);
        assertTrue(foundEvent.isPresent());
        assertEquals(event.getId(), foundEvent.get().getId());
        assertEquals(eventName, foundEvent.get().getName());
    }

    @Test
    public void testUpdate(){
        /* Updaten eines nicht existenten Users führt zu einem Fehler */
        String eventName="eventName";
        AppEvent event = new AppEvent();
        event.setName(eventName);
        assertThrows(RuntimeException.class, ()-> eventService.update(event));

        /* Updaten eines persistierten Users */
        eventService.create(event);
        var foundEvent = eventService.getByObj(event);
        assertEquals(eventName,foundEvent.get().getName());
        String newName = "NewName";
        event.setName(newName);
        eventService.update(event);


    }

    @Test
    public void testDeleteById(){
        /* Löschen eines nicht persistierten Users wirft eine Fehlermeldung */
        AppEvent event = new AppEvent();
        assertThrows(RuntimeException.class, () -> eventService.delete(event.getId()));

        eventService.create(event);
        var foundEvent = eventService.getByObj(event);
        assertTrue(foundEvent.isPresent());

        /* Erfolgreiches löschen eines persistierten Events */
        eventService.delete(event.getId());
        foundEvent = eventService.getByObj(event);
        assertTrue(foundEvent.isEmpty());

    }

    @Test
    public void testDeleteByObj(){
        /* Löschen eines nicht persistierten Users wirft eine Fehlermeldung */
        AppEvent event = new AppEvent();
        assertThrows(RuntimeException.class, () -> eventService.delete(event));

        eventService.create(event);
        var foundEvent = eventService.getByObj(event);
        assertTrue(foundEvent.isPresent());

        /* Erfolgreiches löschen eines persistierten Events */
        eventService.delete(event);
        foundEvent = eventService.getByObj(event);
        assertTrue(foundEvent.isEmpty());
    }

    @Test
    public void testGetAll(){

        AppEvent defaultEvent1 = new AppEvent();
        AppEvent defaultEvent2 = new AppEvent();
        AppEvent defaultEvent3 = new AppEvent();

        eventService.create(defaultEvent1);
        eventService.create(defaultEvent2);

        /* Liefert alle persistierte Events */
        List<AppEvent> foundEvents = eventService.getAll();
        assertTrue(foundEvents.contains(defaultEvent1));
        assertTrue(foundEvents.contains(defaultEvent2));

        /* Liefert alle persistierte Events */
        eventService.create(defaultEvent3);
        foundEvents = eventService.getAll();
        assertTrue(foundEvents.contains(defaultEvent1));
        assertTrue(foundEvents.contains(defaultEvent2));
        assertTrue(foundEvents.contains(defaultEvent3));
    }

    @Test
    public void testGetByIdMult(){
        /* Nicht vorhandener User wird nicht gefunden */
        AppEvent defaultEvent1 = new AppEvent();
        AppEvent defaultEvent2 = new AppEvent();
        AppEvent defaultEvent3 = new AppEvent();
        List<AppEvent> foundEvents = eventService.getById(defaultEvent1.getId(),defaultEvent2.getId(),defaultEvent3.getId() );
        assertEquals(0, foundEvents.size());

        /* Persistiertes Event stimmt mit dem gefundenen Event überein */
        /* Name Property wird mitgeladen */
        String eventName="EventName";
        defaultEvent1.setName(eventName);
        defaultEvent2.setName(eventName);
        defaultEvent3.setName(eventName);
        eventService.create(defaultEvent1);
        eventService.create(defaultEvent2);
        eventService.create(defaultEvent3);
        foundEvents = eventService.getById(defaultEvent1.getId(), defaultEvent2.getId(), defaultEvent3.getId());
        assertEquals(3, foundEvents.size());
        assertEquals(defaultEvent1.getId(), foundEvents.get(0).getId());
        assertEquals(eventName, foundEvents.get(0).getName());
    }

    @Test
    public void testGetByObjMult(){
        /* Nicht vorhandener User wird nicht gefunden */
        AppEvent defaultEvent1 = new AppEvent();
        AppEvent defaultEvent2 = new AppEvent();
        AppEvent defaultEvent3 = new AppEvent();
        List<AppEvent> foundEvents = eventService.get(defaultEvent1,defaultEvent2,defaultEvent3 );
        assertEquals(0, foundEvents.size());

        /* Persistiertes Event stimmt mit dem gefundenen Event überein */
        /* Name Property wird mitgeladen */
        String eventName="EventName";
        defaultEvent1.setName(eventName);
        defaultEvent2.setName(eventName);
        defaultEvent3.setName(eventName);
        eventService.create(defaultEvent1);
        eventService.create(defaultEvent2);
        eventService.create(defaultEvent3);
        foundEvents = eventService.get(defaultEvent1, defaultEvent2, defaultEvent3);
        assertEquals(3, foundEvents.size());
        assertEquals(defaultEvent1.getId(), foundEvents.get(0).getId());
        assertEquals(eventName, foundEvents.get(0).getName());
    }

    @Test
    public void testGetByObjMult2(){
        /* Nicht vorhandener User wird nicht gefunden */
        AppEvent defaultEvent1 = new AppEvent();
        AppEvent defaultEvent2 = new AppEvent();
        AppEvent defaultEvent3 = new AppEvent();
        List<AppEvent> foundEvents = eventService.get(List.of(defaultEvent1,defaultEvent2,defaultEvent3));
        assertEquals(0, foundEvents.size());

        /* Persistiertes Event stimmt mit dem gefundenen Event überein */
        /* Name Property wird mitgeladen */
        String eventName="EventName";
        defaultEvent1.setName(eventName);
        defaultEvent2.setName(eventName);
        defaultEvent3.setName(eventName);
        eventService.create(defaultEvent1);
        eventService.create(defaultEvent2);
        eventService.create(defaultEvent3);
        foundEvents = eventService.get(List.of(defaultEvent1, defaultEvent2, defaultEvent3));
        assertEquals(3, foundEvents.size());
        assertEquals(defaultEvent1.getId(), foundEvents.get(0).getId());
        assertEquals(eventName, foundEvents.get(0).getName());
    }

    @Test
    public void testDeleteByIdMult(){
        AppEvent defaultEvent1 = new AppEvent();
        AppEvent defaultEvent2 = new AppEvent();
        AppEvent defaultEvent3 = new AppEvent();

        eventService.create(defaultEvent1);
        eventService.create(defaultEvent2);
        eventService.create(defaultEvent3);

        /* Löscht alle angegebenen Events, und nur diese */
        eventService.delete(List.of(defaultEvent1));

        var foundEvents = eventService.getAll();
        assertEquals(2, foundEvents.size());
        assertTrue(foundEvents.contains(defaultEvent2));
        assertTrue(foundEvents.contains(defaultEvent3));

        /* Löscht alle angegebenen Events, und nur diese */
        eventService.delete(List.of(defaultEvent2, defaultEvent3));

        foundEvents = eventService.getAll();
        assertEquals(0, foundEvents.size());
        assertFalse(foundEvents.contains(defaultEvent2));
        assertFalse(foundEvents.contains(defaultEvent3));
    }

    @Test
    public void testDeleteByCreator(){

    }

    //TODO
    /* Persistieren eines Events mit Creator */


    /* Persistieren eins Events mit Participants */


}
