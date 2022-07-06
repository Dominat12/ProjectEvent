package com.example.eventpoc;

import com.example.eventpoc.event.AppEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<AppEvent, Long> {

    Optional<AppEvent> findById(long id);
}
