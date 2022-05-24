package com.cs.analyser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.analyser.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	public List<Event> findByAlert(boolean alert);

}
