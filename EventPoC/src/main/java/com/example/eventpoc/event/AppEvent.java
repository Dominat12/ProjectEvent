package com.example.eventpoc.event;

import com.example.eventpoc.user.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
public class AppEvent {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String venue;
    private LocalDateTime lastUpdatedAt;

    @OneToOne
    private AppUser creator;

    @OneToMany
    private Set<AppUser> participants;

    public AppEvent() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public AppUser getCreator() {
        return creator;
    }

    public void setCreator(AppUser creator) {
        this.creator = creator;
    }

    public Set<AppUser> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<AppUser> participants) {
        this.participants = participants;
    }
}
