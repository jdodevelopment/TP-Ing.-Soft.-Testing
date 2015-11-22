package com.ips.ingsoft.tipos;

public class Colectivo {
	
	private int numero_interno;
	private String linea;
	
	
	
	public Colectivo(String linea, int numero_interno) {
		this.linea = linea;
		this.numero_interno = numero_interno;
	}


	public String getLinea() {
		return linea;
	}

	public int getNumero_interno() {
		return numero_interno;
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Colectivo){
			Colectivo other = (Colectivo) obj;
			return linea.equals(other.linea);
		}
		return false;
	}
	
}
