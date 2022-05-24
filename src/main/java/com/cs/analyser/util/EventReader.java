package com.cs.analyser.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EventReader implements Callable<String>{
	private static final org.apache.log4j.Logger Logger = org.apache.log4j.Logger.getLogger(EventReader.class);   
	private BlockingQueue<EventRow> queue;
	private String filePath;

	public EventReader(BlockingQueue<EventRow> queue, String filePath) {
		this.queue = queue;
		this.filePath = filePath;
	}
	@Override
	public String call() throws Exception {
		Map<String, EventRow> eventRowMap = new HashMap<>();
		JSONParser parser = new JSONParser();
		BufferedReader reader;

		try {			
			reader = new BufferedReader((new FileReader(filePath)));
			String line = reader.readLine();
			while (line != null) {
				JSONObject rowObject = (JSONObject) parser.parse(line);
				if (rowObject != null) {
					this.updateEventRow(eventRowMap, rowObject);
				}				
				line = reader.readLine();
			}
			Logger.info("Adding dummy EventRow object to indicate end of file reading");	
			queue.add(new EventRow());			
		} catch (FileNotFoundException e) {
			queue.add(new EventRow());// Adding dummy EventRow object to indicate end of file.
			Logger.error("Cannot load log file. Invalid file name: " + filePath);
			Logger.error(e);
		} catch (IOException e) {
			Logger.error("Cannot read file " + filePath );
	        Logger.error(e);			
		} catch (ParseException e) {
			Logger.error("Cannot parse file " + filePath );
	        Logger.error(e);
		}
		return "EventReader process";
	}
	private void updateEventRow(Map<String, EventRow> eventRowMap, JSONObject rowObject) {
		String eventId = (String) rowObject.get("id");
		if (eventId != null) {
			EventRow eventRow = eventRowMap.get(eventId);
			if (eventRow == null) {
				eventRow = new EventRow();
				eventRow.setId(eventId);
				eventRow.setType((String) rowObject.get("type"));
				eventRow.setHost((String) rowObject.get("host"));
				this.updateEventTiming(rowObject, eventRow);
				eventRowMap.put(eventId, eventRow);
			} else {
				this.updateEventTiming(rowObject, eventRow);
				synchronized (this) {
					Logger.debug("Adding EventRow object in the queue for processing : "+eventRow);					
					queue.add(eventRow);					
					eventRowMap.remove(eventId);
				}

			}
		}
	}
	private void updateEventTiming(JSONObject rowObject, EventRow eventRow) {
		Long timeStamp = (Long) rowObject.get("timestamp");
		String state = (String) rowObject.get("state");
		if (timeStamp != null && state != null) {
			if (state.equals("STARTED")) {
				eventRow.setStartTime(timeStamp);
			} else if (state.equals("FINISHED")) {
				eventRow.setEndTime(timeStamp);
			}
		}
	}
}
