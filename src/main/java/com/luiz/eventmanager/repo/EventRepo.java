package com.luiz.eventmanager.repo;

import com.luiz.eventmanager.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepo extends CrudRepository<Event, Integer> {
   public Event findByPrettyName(String prettyName);
}
