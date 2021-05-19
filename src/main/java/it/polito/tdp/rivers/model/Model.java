package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	private int ngiornifailure;
	private float csomma;
	
	public Model() {
		dao=new RiversDAO();
		ngiornifailure=0;
		float csomma=0;
	}
	
	public List<River> getFiumi(){
		return dao.getAllRivers();
	}
	
	public Informazioni getInformazioni(int id) {
		return dao.getAllInformazioni(id);
	}

	public void getSimulation(float k, int id, float fmedia, LocalDate datainizio,
			LocalDate datafine) {
		Simulator s=new Simulator();
		s.setDatamax(datainizio);
		s.setDatamin(datainizio);
		s.setK(k);
		s.setFmed(fmedia*3600*24);
		s.setFlussi(this.dao.getFlows(id));
		s.run();
		ngiornifailure=s.getnGiorniFailure();
		csomma=s.getCSomma();
	}

	public int getNgiornifailure() {
		return ngiornifailure;
	}

	public float getCsomma() {
		return csomma;
	}
}
