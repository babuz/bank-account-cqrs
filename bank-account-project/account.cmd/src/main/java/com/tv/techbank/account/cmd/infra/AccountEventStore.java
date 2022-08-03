package com.tv.techbank.account.cmd.infra;

import com.tv.techbank.account.cmd.domain.AccountAggregate;
import com.tv.techbank.account.cmd.domain.EventStoreRepository;
import com.tv.techbank.cqrs.core.events.BaseEvent;
import com.tv.techbank.cqrs.core.events.EventModel;
import com.tv.techbank.cqrs.core.exceptions.AggregatorNotFoundException;
import com.tv.techbank.cqrs.core.exceptions.ConcurrencyException;
import com.tv.techbank.cqrs.core.infra.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    private EventStoreRepository eventStoreRepository;

    @Autowired
    public AccountEventStore(EventStoreRepository eventStoreRepository){
        this.eventStoreRepository = eventStoreRepository;
    }
    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion != -1 && eventStream.get(eventStream.size()-1).getVersion() != expectedVersion ){
            throw new ConcurrencyException();
        }
        var version = expectedVersion;

        for (var event: events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .eventData(event)
                    .aggregateIdentifier(aggregateId)
                    .timeStamp(new Date())
                    .eventType(event.getClass().getTypeName())
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .build();
            var presistedEvent = eventStoreRepository.save(eventModel);

            if(presistedEvent != null){
                // produce events to kafka
            }
        }

    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
       var eventStream =  eventStoreRepository.findByAggregateIdentifier(aggregateId);
       if(eventStream == null || eventStream.isEmpty()){
           throw new AggregatorNotFoundException("aggregator not found in the database");
       }
       return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }
}
