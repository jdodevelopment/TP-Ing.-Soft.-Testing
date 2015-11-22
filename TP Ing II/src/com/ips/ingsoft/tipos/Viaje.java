package com.ips.ingsoft.tipos;

import java.util.Date;

public class Viaje {

	private Date horario;
	private boolean transbordo;
	private Colectivo colectivo;
	private float costo;


	
	public Viaje(Colectivo colectivo, Date horario, boolean transbordo, float costo) {
		super();
		this.colectivo = colectivo;
		this.horario = horario;
		this.transbordo = transbordo;
		this.costo=costo;
	}

	public Date getHorario() {
		return horario;
	}

	public boolean isTransbordo() {
		return transbordo;
	}


	public Colectivo getColectivo() {
		return colectivo;
	}

	public float getCosto() {
		return costo;
	}

	

}
