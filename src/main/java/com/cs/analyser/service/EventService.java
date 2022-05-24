package com.cs.analyser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.analyser.model.Event;
import com.cs.analyser.repository.EventRepository;
import com.cs.analyser.util.EventProcessor;
import com.cs.analyser.util.EventReader;

@Service
public class EventService {
	private static final org.apache.log4j.Logger Logger = org.apache.log4j.Logger.getLogger(EventReader.class);
	@Autowired
	private EventRepository eventRepository;

	public void createEvent(String filePath) {
		if (filePath == null || filePath.length() == 0) {
			Logger.warn("Unable to log load log file because its log file path is either null or emptry.");			
			return;
		}
		EventProcessor processor = new EventProcessor(eventRepository);
		processor.process(filePath);
	}

	/*
	 * public void saveEvent(Event event) { eventRepository.save(event); }
	 */

	public List<Event> getEventByAlert(boolean alert) {
		return eventRepository.findByAlert(alert);
	}

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	public void deleteAll() {
		eventRepository.deleteAll();
	}

}
