package ar.com.osdepym.template.entity;


public class Turno {
	private String numeroTurno ;
	private String sectorAtencion ;
	private String error;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getNumeroTurno() {
		return numeroTurno;
	}
	public void setNumeroTurno(String numeroTurno) {
		this.numeroTurno = numeroTurno;
	}
	public String getSectorAtencion() {
		return sectorAtencion;
	}
	public void setSectorAtencion(String sectorAtencion) {
		this.sectorAtencion = sectorAtencion;
	}
	
	
	
}
