package com.luiz.eventmanager.service;

import com.luiz.eventmanager.model.Event;
import com.luiz.eventmanager.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    public Event addNewEvent(Event event){
        //Generating prettyname
        event.setPrettyName(
                event.getTitle().toLowerCase().replaceAll(" ","-"));
        return eventRepo.save(event);
    }

    public List<Event> getAllEvents(){
        // Return all events
        return (List<Event>) eventRepo.findAll();
    }

    public Event getByPrettyName(String prettyName){
        return eventRepo.findByPrettyName(prettyName);
    }
}
