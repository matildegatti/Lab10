package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Informazioni {

	private LocalDate mindata;
	private LocalDate maxdata;
	private int tot;
	private float media;
	
	public Informazioni(LocalDate mindata, LocalDate maxdata, int tot, float media) {
		super();
		this.mindata = mindata;
		this.maxdata = maxdata;
		this.tot = tot;
		this.media = media;
	}

	public LocalDate getMindata() {
		return mindata;
	}

	public void setMindata(LocalDate mindata) {
		this.mindata = mindata;
	}

	public LocalDate getMaxdata() {
		return maxdata;
	}

	public void setMaxdata(LocalDate maxdata) {
		this.maxdata = maxdata;
	}

	public int getTot() {
		return tot;
	}

	public void setTot(int tot) {
		this.tot = tot;
	}

	public float getMedia() {
		return media;
	}

	public void setMedia(float media) {
		this.media = media;
	}
	
	
}
