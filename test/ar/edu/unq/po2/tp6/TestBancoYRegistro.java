package ar.edu.unq.po2.tp6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBancoYRegistro {
	
	Cliente agustin;
	Cliente martin;
	Banco hsbc;
	Propiedad casa;
	
	@BeforeEach
	public void setUp() {
		agustin = new Cliente("agustin", "lascar", "caseros", 24, 48000);
		martin = new Cliente("martin", "lascar", "caseros", 24, 80000);
		hsbc = new Banco();
		hsbc.agregarCliente(agustin);
		hsbc.agregarCliente(martin);
		casa = new Propiedad("es una casa", "caseros", 500000);
	}
	@Test
	void testSinGarantia() {
		assertThrows(RuntimeException.class, () -> agustin.solicitarCredito("credito hipotecario", 300000, 24));
		
	}
	
	@Test
	void testHipotecarioConGarantia() {
		agustin.agregarGarantia(casa);
		agustin.solicitarCredito("credito hipotecario", 300000, 24);
		hsbc.registrarSolicitud(agustin);
		assertEquals(1, hsbc.solicitudes().size());
	}
	
	@Test
	void testCreditoPersonal() {
		agustin.solicitarCredito("credito personal", 300000, 3);
		hsbc.registrarSolicitud(agustin);
		assertEquals(0, hsbc.solicitudes().size());
	}
	
	@Test
	void testSeRegistroSolicitudEnBanco() {
		agustin.solicitarCredito("credito personal", 300000, 30);
		hsbc.registrarSolicitud(agustin);
		assert(hsbc.solicitudes().contains(agustin.solicitudCredito()));
	}
	
	@Test
	void testMontoTotalDeCreditosAceptables() {
		agustin.agregarGarantia(casa);
		agustin.solicitarCredito("credito hipotecario", 300000, 24);
		hsbc.registrarSolicitud(agustin);
		martin.solicitarCredito("credito personal", 300000, 30);
		hsbc.registrarSolicitud(martin);
		assertEquals(600000, hsbc.montoTotalDePrestamos());
		
	}

}