package ar.com.osdepym.template.entity;

public class Ticket {
 private String fechaEmision;
 private String numeroMostrar;
 private int turnosDelante;
 private String error ="";
public String getFechaEmision() {
	return fechaEmision;
}
public void setFechaEmision(String fechaEmision) {
	this.fechaEmision = fechaEmision;
}
public String getNumeroMostrar() {
	return numeroMostrar;
}
public void setNumeroMostrar(String numeroMostrar) {
	this.numeroMostrar = numeroMostrar;
}
public int getTurnosDelante() {
	return turnosDelante;
}
public void setTurnosDelante(int turnosDelante) {
	this.turnosDelante = turnosDelante;
}
public String getError() {
	return error;
}
public void setError(String error) {
	this.error = error;
}
 
 
 
}
