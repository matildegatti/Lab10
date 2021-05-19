package it.polito.tdp.rivers.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.Informazioni;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public Informazioni getAllInformazioni(int idRiver){
		String sql="SELECT MAX(f.day) AS maxdata, MIN(f.day) AS mindata, COUNT(*) AS tot, AVG(f.flow) AS media "
				+ "FROM flow f "
				+ "WHERE f.river=?";
		
		Informazioni info = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idRiver);
			ResultSet res = st.executeQuery();
			
			if(res.next())
				info=new Informazioni(res.getDate("mindata").toLocalDate(), res.getDate("maxdata").toLocalDate(), res.getInt("tot"), res.getFloat("media"));
			
			conn.close();
			return info;
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL Error");
		}
	}
	
	public HashMap<LocalDate,Float> getFlows(int id){

		final String sql = "SELECT * FROM flow f WHERE f.river=?";

		HashMap<LocalDate,Float> result = new HashMap<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.put(res.getDate("day").toLocalDate(), res.getFloat("flow"));
			}

			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
}
