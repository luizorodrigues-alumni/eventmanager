package com.luiz.eventmanager.controller;

import com.luiz.eventmanager.model.Event;
import com.luiz.eventmanager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event newEvent){
        return eventService.addNewEvent(newEvent);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName){
        Event filteredEvent =  eventService.getByPrettyName(prettyName);
        if (filteredEvent != null){
            return ResponseEntity.ok().body(filteredEvent);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
