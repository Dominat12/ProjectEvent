package com.example.eventpoc.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppEventRepository extends JpaRepository<AppEvent, Long> {
}