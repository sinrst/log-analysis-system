package com.cs.analyser.util;

public class EventRow {

	private String id;

	private String type;
	private String host;
	private long startTime;
	private long endTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "EventRow [id=" + id + ", type=" + type + ", host=" + host + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

}
