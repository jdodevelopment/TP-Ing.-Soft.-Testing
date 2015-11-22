package com.ips.ingsoft.tipos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.ips.ingsoft.helper.Costos;

public class Tarjeta {
	
	//                 Una hora              ms * seg * min
	private static int LAPSO_DE_TRANSBORDO = 1000 * 60 * 60;

	
	private ArrayList<Viaje> viajes;
	private float saldo;
	private TipoDeTarjeta tipo_de_tarjeta;
	
	
	
	public Viaje ultimoViaje(){
		Viaje viaje = null;
		if(!viajes.isEmpty()){
			viaje = viajes.get(viajes.size());
		}
		return viaje;
	}
	
	public Tarjeta(TipoDeTarjeta tipo_de_tarjeta) {
		this.tipo_de_tarjeta = tipo_de_tarjeta;
		saldo=0;
		viajes = new ArrayList<>();
	}


	public boolean pagarBoleto(Colectivo colectivo, Date horario){
		boolean es_transbordo = false;
		//Si no es el primer viaje
		if(!viajes.isEmpty()){
			Viaje ultimo_viaje = viajes.get(viajes.size()-1);
			//Si no paso mas de una hora
			if((horario.getTime() - ultimo_viaje.getHorario().getTime() < LAPSO_DE_TRANSBORDO)){
				System.out.println(
						"Hora actual: "+ new Date(System.currentTimeMillis())+ 
						" Hora del ultimo viaje: "+ultimo_viaje.getHorario());
				//Si el colectivo es distinto
				if(!ultimo_viaje.getColectivo().equals(colectivo)){
					//Y si el ultimo viaje no fue transbordo..
					if(!ultimo_viaje.isTransbordo()){
						//Es transbordo :)
						es_transbordo=true;
					}
				}
			}			
		}
		//Obtener el costo del viaje segun el tipo de tarjeta
		float costo_del_viaje = calcularCostoDelViaje(horario, es_transbordo);
		
		if(saldo >= costo_del_viaje){
			//Si tengo saldo, pago el pasaje y registro el viaje en el historial
			saldo-=costo_del_viaje;
			viajes.add(new Viaje(colectivo, horario, es_transbordo, costo_del_viaje));
			return true;
		}
		return false;
	}

	
	private float calcularCostoDelViaje(Date horario, boolean es_transbordo) {
		if(tipo_de_tarjeta == TipoDeTarjeta.TARJETA_MEDIO_BOLETO){
			if(dontroDelHorarioEscolar(horario)){
				if(es_transbordo){
					return Costos.MEDIO_BOLETO_TRANSBORDO;
				}else{
					return Costos.MEDIO_BOLETO;
				}				
			}
		}
		if(es_transbordo){
			return Costos.PASAJE_NORMAL_TRANSBORDO;
		}else{
			return Costos.PASAJE_NORMAL;
		}
	}


	private boolean dontroDelHorarioEscolar(Date horario) {
		Calendar hora_actual = Calendar.getInstance();
		hora_actual.setTime(horario);
		//HORA MINIMA 06:00:00 HS
		Calendar hora_minima = Calendar.getInstance();
		hora_minima.setTime(horario);
		hora_minima.set(Calendar.HOUR_OF_DAY, 6);
		hora_minima.set(Calendar.MINUTE, 0);
		hora_minima.set(Calendar.SECOND, 0);
		//FIN HORA MINIMA
		//HORA MINIMA MAXIMA 23:59:59 HS
		Calendar hora_maxima = Calendar.getInstance();
		hora_maxima.setTime(horario);
		hora_maxima.set(Calendar.HOUR_OF_DAY, 23);
		hora_maxima.set(Calendar.MINUTE, 59);
		hora_maxima.set(Calendar.SECOND, 59);
		//FIN HORA MAXIMA
		//COPROBAR SI ESTA EN EL RANGO DE HORARIO ESCOLAR
		if(hora_actual.after(hora_minima) && hora_actual.before(hora_maxima)){
			return true;
		}
		return false;
	}


	public void recargar(float monto){
		if(monto > 0){
			if(monto < 196){
				saldo+= monto;
			}else if(monto < 368){
				saldo+= monto + 34;
			}else if(monto >= 368){
				saldo+= monto + 92;
			}
		}
	}

	public float saldo(){
		return saldo;
	}
	
	public ArrayList<Viaje> viajes(){
		return viajes;
	}


	public ArrayList<Viaje> getViajes() {
		return viajes;
	}


	public void setViajes(ArrayList<Viaje> viajes) {
		this.viajes = viajes;
	}


	public float getSaldo() {
		return saldo;
	}


	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}


	public TipoDeTarjeta getTipo_de_tarjeta() {
		return tipo_de_tarjeta;
	}


	public void setTipo_de_tarjeta(TipoDeTarjeta tipo_de_tarjeta) {
		this.tipo_de_tarjeta = tipo_de_tarjeta;
	}
	
	
	
	
	
	
	
}
