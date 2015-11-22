package com.ips.ingsoft.test;

import java.util.Date;

import com.ips.ingsoft.tipos.Colectivo;
import com.ips.ingsoft.tipos.Tarjeta;
import com.ips.ingsoft.tipos.TipoDeTarjeta;
import com.ips.ingsoft.tipos.Viaje;

public class Main {
	
	
	private static final long X_HORAS = 16;
	
	public static final long LAPSO_DE_X_HORAS = 1000 * 60 * (60 * X_HORAS);
	//UNA HORA
	public static final long LAPSO_DE_UNA_HORA = 1000 * 60 * 60;
	//UNA HORA Y UN MILISEGUNDO
	public static final long LAPSO_DE_MAS_DE_UNA_HORA_MENOS_UN_SEGUNDO = 1000 * 60 * 60 - 1000;
	//UNA HORA Y UN SEGUNDO
	public static final long LAPSO_DE_MAS_DE_UN_HORA_Y_UN_SEGUNDO = 1000 * 60 * 60 + 1000;

	public static void main(String[] args) {
		Colectivo linea1 = new Colectivo("115 Negra", 1);
		Colectivo linea2 = new Colectivo("122 Verde", 22);
		Colectivo linea3 = new Colectivo("133 Negra", 333);
		
		
		Tarjeta mi_tarjeta = new Tarjeta(TipoDeTarjeta.TARJETA_MEDIO_BOLETO);
		
		mi_tarjeta.recargar(20);
		
		System.out.println("Saldo inicial: " + mi_tarjeta.saldo());
		
		mi_tarjeta.pagarBoleto(linea1, new Date(System.currentTimeMillis()-LAPSO_DE_X_HORAS));
		mi_tarjeta.pagarBoleto(linea3, new Date(System.currentTimeMillis()-LAPSO_DE_X_HORAS));
		mi_tarjeta.pagarBoleto(linea2, new Date(System.currentTimeMillis()+LAPSO_DE_MAS_DE_UNA_HORA_MENOS_UN_SEGUNDO));
		mi_tarjeta.pagarBoleto(linea3, new Date(System.currentTimeMillis()+LAPSO_DE_MAS_DE_UNA_HORA_MENOS_UN_SEGUNDO));
		
		System.out.println("Viajes:");
		int nro_viaje = 1;
		for(Viaje viaje : mi_tarjeta.viajes()){
			System.out.println(
					"Viaje nº: "+(nro_viaje++) +
					" Linea: "+viaje.getColectivo().getLinea()+
					" Horario: "+viaje.getHorario()+
					" Costo: "+viaje.getCosto()
					);
		}
		System.out.println("Saldo final: " + mi_tarjeta.saldo());
		
	}
	
}
