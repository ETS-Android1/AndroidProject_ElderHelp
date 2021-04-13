package com.example.oldpeoplehelp;

public class Event {
    public String currentUserId, eventName, eventDescription, eventTime;

    public Event() {
    }

    public Event(String currentUserId, String eventName, String eventDescription, String eventTime) {
        this.currentUserId = currentUserId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventTime = eventTime;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
