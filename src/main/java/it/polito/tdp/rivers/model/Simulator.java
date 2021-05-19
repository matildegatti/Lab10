package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {

	//eventi
	private PriorityQueue<Event> queue;
	
	//parametri di simulazione
	private float k;
	private float Q;
	private LocalDate datamin;
	private LocalDate datamax;
	private float fmed;
	private float foutmin;
	private HashMap<LocalDate,Float> flussi;
	
	//stato del sistema
	private float C;
	
	//misure in uscita
	private int nGiorniFailure;
	private float cSomma;
	
	public void setK(float k) {
		this.k = k;
	}
	
	public void setDatamin(LocalDate datamin) {
		this.datamin = datamin;
	}
	
	public void setDatamax(LocalDate datamax) {
		this.datamax = datamax;
	}
	
	public void setFlussi(HashMap<LocalDate, Float> flusso) {
		this.flussi = flusso;
	}

	public void setFmed(float fmed) {
		this.fmed = fmed;
	}

	public void run() {
		this.queue=new PriorityQueue<Event>();
		
		//stato iniziale
		this.nGiorniFailure=0;
		this.foutmin=(float) (0.8*fmed);
		this.Q=this.k*this.fmed*30;
		this.C=Q/2;
		this.cSomma=C;
		
		//eventi iniziali
		LocalDate giorno=this.datamin;
		LocalDate fine=this.datamax;
		while(giorno.isBefore(fine)) {
			this.queue.add(new Event(giorno, EventType.NUOVO_FLUSSO));
			giorno.plusDays(1);
		}
		
		//ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e=this.queue.poll(); 
			double fout=this.foutmin;
			double random=Math.random()*100;
			if(random<=5)
				fout=fout*10;
			float flusso=this.flussi.get(e.getGiorno());  //prendo il flusso del fiume in quel giorno
			this.C=this.C+flusso;

			if(this.C>this.Q)
				e.setType(EventType.TRACIMAZIONE);
			
			if(fout>C)
				e.setType(EventType.CAPIENZA_MINORE);
			
			processEvent(e,fout);  
		}
	}

	private void processEvent(it.polito.tdp.rivers.model.Event e, double fout) {
		switch(e.getType()) {
		case NUOVO_FLUSSO:
			this.C-=fout;
			this.cSomma+=this.C;
			break;
		case TRACIMAZIONE:
			this.C=this.Q;
			this.cSomma+=this.C;
			break;
		case CAPIENZA_MINORE:
			this.nGiorniFailure++;
			this.C=0;
			break;
		}
	}

	public int getnGiorniFailure() {
		return nGiorniFailure;
	}

	public float getCSomma() {
		return cSomma;
	}
	
}
