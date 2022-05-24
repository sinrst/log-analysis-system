package com.cs.analyser.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.cs.analyser.model.Event;
import com.cs.analyser.repository.EventRepository;

public class EventWriter implements Callable<String>{
	private static final org.apache.log4j.Logger Logger = org.apache.log4j.Logger.getLogger(EventReader.class);
	private BlockingQueue<EventRow> queue;
	private EventRepository eventRepository;

	public EventWriter(BlockingQueue<EventRow> queue, EventRepository eventRepository) {
		this.queue = queue;
		this.eventRepository = eventRepository;
	}

	@Override
	public String call() throws Exception {
		EventRow eventRow = queue.poll();
		while (true) {
			if (eventRow != null) {
				if (eventRow.getId() != null) {
					saveEvent(eventRow);					
				} else {
					break;
				}
			}
			eventRow = queue.poll();

		}
		return "EventWriter process";
	}
	public void saveEvent(EventRow eventRow) {
		Event event = new Event();
		event.setEventId(eventRow.getId());
		event.setDuration(eventRow.getEndTime() - eventRow.getStartTime());
		event.setAlert(event.getDuration() > 4);
		event.setType(eventRow.getType());
		Logger.debug("Saving Event in the database: "+eventRow);
		eventRepository.save(event);
		Logger.debug("Event saved in the database: "+eventRow);
		
	}

}
