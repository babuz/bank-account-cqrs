package com.tv.techbank.cqrs.core.domain;

import com.tv.techbank.cqrs.core.events.BaseEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AggregateRoot {
    protected String id;
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommitedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent) {

        try {
            var method = event.getClass().getDeclaredMethod("apply", event.getClass());
            method.invoke( this, event);

        } catch (NoSuchMethodException e) {
            log.error("method apply doesn't declared in the class {}", event.getClass());
        } catch (Exception ex) {
            log.error("Other exception during apply change {}", ex.getMessage());
        } finally {
            if (isNewEvent) {
                this.changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        this.applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> this.applyChange(event, false));
    }
}
