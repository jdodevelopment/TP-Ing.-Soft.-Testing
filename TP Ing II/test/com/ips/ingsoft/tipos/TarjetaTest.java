package com.ips.ingsoft.tipos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ips.ingsoft.helper.Costos;


public class TarjetaTest {
	
	public static final long LAPSO_DE_UNA_HORA = 1000 * 60 * 60;
	public static final long LAPSO_DE_MAS_DE_UNA_HORA_MENOS_UN_SEGUNDO = 1000 * 60 * 60 - 1000;
	public static final long LAPSO_DE_MAS_DE_UN_HORA_Y_UN_SEGUNDO = 1000 * 60 * 60 + 1000;
	
	
	private static Tarjeta tarjeta_para_recargar;
	private static Tarjeta tarjeta_con_saldo;
	private static Tarjeta tarjeta_con_dos_viajes;
	private static Tarjeta tarjeta_comun_para_pagar, tarjeta_medio_boleto_para_pagar, tarjeta_sin_saldo;
	
	private static Colectivo colectivo;
	private static Colectivo colectivo2;
	
	private Calendar diez_de_la_noche;
	private Calendar tres_de_la_manana;
	
	
	@BeforeClass
	public static void onTimeBeforeClass() {
		colectivo= new Colectivo("144", 12);
		colectivo2= new Colectivo("K", 666);
		
		tarjeta_para_recargar = new Tarjeta(TipoDeTarjeta.TARJETA_COMUN);
		tarjeta_para_recargar.recargar(6.75f);
		
		tarjeta_con_saldo = new Tarjeta(TipoDeTarjeta.TARJETA_COMUN);
		tarjeta_con_saldo.recargar(1.0f);

		tarjeta_sin_saldo= new Tarjeta(TipoDeTarjeta.TARJETA_COMUN);
		
		tarjeta_comun_para_pagar= new Tarjeta(TipoDeTarjeta.TARJETA_COMUN);
		tarjeta_comun_para_pagar.setSaldo(200f);
		
		tarjeta_medio_boleto_para_pagar= new Tarjeta(TipoDeTarjeta.TARJETA_MEDIO_BOLETO);
		tarjeta_medio_boleto_para_pagar.setSaldo(200f);
		
		Viaje v1= new Viaje(colectivo, new Date(), false, 5.75f);
		Viaje v2= new Viaje(colectivo, new Date(), false, 5.75f);
		
		ArrayList<Viaje> viajes= new ArrayList<>();
		viajes.add(v1);
		viajes.add(v2);
		tarjeta_con_dos_viajes = new Tarjeta(TipoDeTarjeta.TARJETA_COMUN);
		tarjeta_con_dos_viajes.setViajes(viajes);
	}
	
	
	@Test
	public void testPagarBoleto() {
		diez_de_la_noche= Calendar.getInstance();
		diez_de_la_noche.set(Calendar.HOUR_OF_DAY, 22);
		tres_de_la_manana= Calendar.getInstance();		
		tres_de_la_manana.set(Calendar.HOUR_OF_DAY, 3);
		
		/*
		 *      TIPO DE TARJETA      TRANSBORDO        HOR. DE ESTUDIANTE
		 *			Nornal				  No					Si
		 */
		tarjeta_comun_para_pagar.pagarBoleto(colectivo, diez_de_la_noche.getTime());
		boolean transbordo=tarjeta_comun_para_pagar.ultimoViaje().isTransbordo();
		assertFalse(transbordo);
		
		tarjeta_comun_para_pagar.pagarBoleto(colectivo, new Date(diez_de_la_noche.getTimeInMillis()+LAPSO_DE_MAS_DE_UN_HORA_Y_UN_SEGUNDO));
		transbordo=tarjeta_comun_para_pagar.ultimoViaje().isTransbordo();
		assertFalse(transbordo);
		
		/*
		 *      TIPO DE TARJETA      TRANSBORDO        HOR. DE ESTUDIANTE
		 *			Nornal				  Si					Si
		 */
		tarjeta_comun_para_pagar.pagarBoleto(colectivo, diez_de_la_noche.getTime());
		transbordo=tarjeta_comun_para_pagar.ultimoViaje().isTransbordo();
		assertFalse(transbordo);
		
		tarjeta_comun_para_pagar.pagarBoleto(colectivo2, new Date(diez_de_la_noche.getTimeInMillis()+LAPSO_DE_MAS_DE_UNA_HORA_MENOS_UN_SEGUNDO));
		transbordo=tarjeta_comun_para_pagar.ultimoViaje().isTransbordo();
		assertTrue(transbordo);
		
		/*
		 *      TIPO DE TARJETA      TRANSBORDO        HOR. DE ESTUDIANTE
		 *			Medio				  No					Si
		 */
		tarjeta_medio_boleto_para_pagar.pagarBoleto(colectivo, diez_de_la_noche.getTime());
		transbordo=tarjeta_medio_boleto_para_pagar.ultimoViaje().isTransbordo();
		assertFalse(transbordo);
		Viaje viaje = tarjeta_medio_boleto_para_pagar.ultimoViaje(); //pregunto si es medio boleto
		assertEquals(Costos.MEDIO_BOLETO, viaje.getCosto(), 0);
		
		/*
		 *      TIPO DE TARJETA      TRANSBORDO        HOR. DE ESTUDIANTE
		 *			Medio				  No					No
		 */
		tarjeta_medio_boleto_para_pagar.pagarBoleto(colectivo, tres_de_la_manana.getTime());
		viaje = tarjeta_medio_boleto_para_pagar.ultimoViaje();
		assertEquals(Costos.PASAJE_NORMAL, viaje.getCosto(), 0);
		
		/*
		 *      TIPO DE TARJETA      TRANSBORDO        HOR. DE ESTUDIANTE
		 *			Medio				  Si					Si
		 */
		tarjeta_medio_boleto_para_pagar.pagarBoleto(colectivo, diez_de_la_noche.getTime());
		transbordo=tarjeta_medio_boleto_para_pagar.ultimoViaje().isTransbordo();
		assertFalse(transbordo);
	
		tarjeta_medio_boleto_para_pagar.pagarBoleto(colectivo2, new Date(diez_de_la_noche.getTimeInMillis()+LAPSO_DE_MAS_DE_UNA_HORA_MENOS_UN_SEGUNDO));
		transbordo=tarjeta_medio_boleto_para_pagar.ultimoViaje().isTransbordo();
		viaje = tarjeta_medio_boleto_para_pagar.ultimoViaje();
		assertEquals(Costos.MEDIO_BOLETO_TRANSBORDO, viaje.getCosto(), 0);
		
		/*
		 *      TIPO DE TARJETA      TRANSBORDO        HOR. DE ESTUDIANTE
		 *			Medio				  Si					No
		 */
		tarjeta_medio_boleto_para_pagar.pagarBoleto(colectivo, tres_de_la_manana.getTime());
		transbordo=tarjeta_medio_boleto_para_pagar.ultimoViaje().isTransbordo();
		assertFalse(transbordo);
	
		tarjeta_medio_boleto_para_pagar.pagarBoleto(colectivo2, new Date(tres_de_la_manana.getTimeInMillis()+LAPSO_DE_MAS_DE_UNA_HORA_MENOS_UN_SEGUNDO));
		transbordo=tarjeta_medio_boleto_para_pagar.ultimoViaje().isTransbordo();
		viaje = tarjeta_medio_boleto_para_pagar.ultimoViaje(); 
		assertEquals(Costos.PASAJE_NORMAL_TRANSBORDO, viaje.getCosto(), 0);
		
		/*
		 * Sin saldo
		 */
		boolean pasaje = tarjeta_sin_saldo.pagarBoleto(colectivo, diez_de_la_noche.getTime());
		assertFalse(pasaje);
		
	}

	@Test
	public void testRecarga() {
		tarjeta_para_recargar.recargar(10f);
		assertEquals(16.75f, tarjeta_para_recargar.saldo(), 0);
	}

	@Test
	public void testSaldo() {
		boolean tiene_saldo = tarjeta_con_saldo.saldo() > 0;
		assertTrue(tiene_saldo);
	}

	@Test
	public void testViajesRealizados() {
		boolean dos_viajes = tarjeta_con_dos_viajes.getViajes().size() == 4;
		assertTrue(dos_viajes);
	}
}
