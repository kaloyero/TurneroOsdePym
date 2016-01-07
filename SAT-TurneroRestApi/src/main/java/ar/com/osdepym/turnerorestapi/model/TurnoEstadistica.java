package ar.com.osdepym.turnerorestapi.model;

import java.io.Serializable;
import java.util.Date;


/**
 * Objeto relacional que representa la tabla Turno Estadistica
 * 
 * @author Alejandro Masciotra
 *
 */
public class TurnoEstadistica implements Serializable  {
	private static final long serialVersionUID = 1L;

	private int id_sat;	
	private String cod_sucursal;	
	private String nom_sucursal;	
	private int id_turno;	
	private String id_cod_sector;	
	private String nom_sector;	
	private int numero_turno;	
	private Date fecha_ticket;
	private String fecha_ticketTest;
	private String llamado;	
	private Date fecha_atencion;
	private String fecha_atencionTest;	
	private String fecha_finTest;	

	private int nro_puesto;	
	private String nom_usuario;	
	private String atendido;	
	private Date fecha_fin;
	public int getId_sat() {
		return id_sat;
	}
	public void setId_sat(int id_sat) {
		this.id_sat = id_sat;
	}
	public String getCod_sucursal() {
		return cod_sucursal;
	}
	public void setCod_sucursal(String cod_sucursal) {
		this.cod_sucursal = cod_sucursal;
	}
	public String getNom_sucursal() {
		return nom_sucursal;
	}
	public void setNom_sucursal(String nom_sucursal) {
		this.nom_sucursal = nom_sucursal;
	}
	public int getId_turno() {
		return id_turno;
	}
	public void setId_turno(int id_turno) {
		this.id_turno = id_turno;
	}
	public String getId_cod_sector() {
		return id_cod_sector;
	}
	public void setId_cod_sector(String id_cod_sector) {
		this.id_cod_sector = id_cod_sector;
	}
	public String getNom_sector() {
		return nom_sector;
	}
	public void setNom_sector(String nom_sector) {
		this.nom_sector = nom_sector;
	}
	public int getNumero_turno() {
		return numero_turno;
	}
	public void setNumero_turno(int numero_turno) {
		this.numero_turno = numero_turno;
	}
	public String getLlamado() {
		return llamado;
	}
	public void setLlamado(String llamado) {
		this.llamado = llamado;
	}
	public Date getFecha_atencion() {
		return fecha_atencion;
	}
	public void setFecha_atencion(Date fecha_atencion) {
		this.fecha_atencion = fecha_atencion;
	}
	public int getNro_puesto() {
		return nro_puesto;
	}
	public void setNro_puesto(int nro_puesto) {
		this.nro_puesto = nro_puesto;
	}
	public String getNom_usuario() {
		return nom_usuario;
	}
	public void setNom_usuario(String nom_usuario) {
		this.nom_usuario = nom_usuario;
	}
	public String getAtendido() {
		return atendido;
	}
	public void setAtendido(String atendido) {
		this.atendido = atendido;
	}
	public Date getFecha_fin() {
		return fecha_fin;
	}
	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	public String getFecha_atencionTest() {
		return fecha_atencionTest;
	}
	public void setFecha_atencionTest(String fecha_atencionTest) {
		this.fecha_atencionTest = fecha_atencionTest;
	}
	public String getFecha_finTest() {
		return fecha_finTest;
	}
	public void setFecha_finTest(String fecha_finTest) {
		this.fecha_finTest = fecha_finTest;
	}
	public Date getFecha_ticket() {
		return fecha_ticket;
	}
	public void setFecha_ticket(Date fecha_ticket) {
		this.fecha_ticket = fecha_ticket;
	}
	public String getFecha_ticketTest() {
		return fecha_ticketTest;
	}
	public void setFecha_ticketTest(String fecha_ticketTest) {
		this.fecha_ticketTest = fecha_ticketTest;
	}

	
}

