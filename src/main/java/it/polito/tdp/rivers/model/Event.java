package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{

	public enum EventType{
		NUOVO_FLUSSO,
		CAPIENZA_MINORE,
		TRACIMAZIONE
		//IRRIGAZIONE
	}
	
	LocalDate giorno;
	private EventType type;
	
	public Event(LocalDate giorno, EventType type) {
		super();
		this.giorno = giorno;
		this.type = type;
	}

	@Override
	public int compareTo(Event other) {
		return this.giorno.compareTo(other.giorno);
	}

	public LocalDate getGiorno() {
		return giorno;
	}

	public void setGiorno(LocalDate giorno) {
		this.giorno = giorno;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}
	
}
