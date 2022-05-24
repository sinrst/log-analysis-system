package com.cs.analyser.util;

import java.io.File;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.cs.analyser.repository.EventRepository;

public class EventProcessor {
	private static final org.apache.log4j.Logger Logger = org.apache.log4j.Logger.getLogger(EventReader.class);
	private EventRepository eventRepository;

	public EventProcessor(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	public void process(String filePath) {
		File file = new File(filePath);
		BlockingQueue<EventRow> queue = new ArrayBlockingQueue<>(10);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		List<Callable<String>> processes = List.of(new EventReader(queue, file.getAbsolutePath())
				, new EventWriter(queue, eventRepository));
		
		 
		try {
			executor.invokeAll(processes).stream().map(future -> {
			        try {
			            return future.get();
			        }
			        catch (Exception e) {
			        	Logger.error(e.getStackTrace());
			            throw new IllegalStateException(e);
			        }
			    })
			    .forEach(process -> Logger.debug(process+" is complete"));
		} catch (InterruptedException e) {			
			Logger.error("EventProcessor received InterruptedException while processing  the task", e);
		}
		executor.shutdown();
		try {
		    if (!executor.awaitTermination(10, TimeUnit.MINUTES)) {
		    	executor.shutdownNow();
		    } 
		} catch (InterruptedException e) {
			Logger.error("EventProcessor received InterruptedException while shuting down the executor service", e);
		}
	}
}
