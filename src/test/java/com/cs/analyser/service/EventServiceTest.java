package com.cs.analyser.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cs.analyser.model.Event;
@SpringBootTest
class EventServiceTest {
	@Autowired
	private EventService eventService;
	@Test
	void createEventWithLogConatinsAllFinishedEventAsInput() {
		eventService.deleteAll();
		eventService.createEvent("src/test/resources/logfile.txt");
		List<Event> list = eventService.getAllEvents();
		assertEquals(6, list.size());
		list =  eventService.getEventByAlert(true);
		assertEquals(4, list.size(),"process single lof file");
	}	
	@Test
	void createEventWithLogConatinsNonFinishedEventAsInput() {
		eventService.deleteAll();
		eventService.createEvent("src/test/resources//data/invalid_log_file.txt");
		List<Event> list = eventService.getAllEvents();
		assertEquals(2, list.size());		
		
	}
	@Test
	void createEventWithEmptyLogFileAsInput() {
		eventService.deleteAll();
		eventService.createEvent("src/test/resources//data/empty_file.txt");
		List<Event> list = eventService.getAllEvents();
		assertEquals(0, list.size());		
		
	}
	@Test
	void createEventWithNullAsFileInput() {
		eventService.deleteAll();
		eventService.createEvent(null);
		List<Event> list = eventService.getAllEvents();
		assertEquals(0, list.size());		
		
	}
	@Test
	void createEventWithEmplyStringAsFileInput() {
		eventService.deleteAll();
		eventService.createEvent("");
		List<Event> list = eventService.getAllEvents();
		assertEquals(0, list.size());		
		
	}
	@Test
	void createEventWithInvalidFileNameAsInput() {
		eventService.deleteAll();
		eventService.createEvent("@.&^%*3!");
		List<Event> list = eventService.getAllEvents();
		assertEquals(0, list.size());		
		
	}
}
