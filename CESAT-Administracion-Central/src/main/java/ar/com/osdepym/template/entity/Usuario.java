package ar.com.osdepym.template.entity;

import java.util.ArrayList;


public class Usuario {

	private int DT_RowId= 0;
	//private int puestoId ;
	private int perfilId ;
	private String nombrePerfil ;
	//private String nombrePuesto ;
	private String nombreUsuario ;
	private String password ;
	private String sectorId ;
	private String habilitado ;
	private String idUsuario;
	private String error ;
	private ArrayList<Puesto> puestos= new ArrayList<Puesto>();

	
	public int getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(int dT_RowId) {
		DT_RowId = dT_RowId;
	}
	//public int getPuestoId() {
	//	return puestoId;
	//}
	//public void setPuestoId(int puestoId) {
	//	this.puestoId = puestoId;
	//}
	public int getPerfilId() {
		return perfilId;
	}
	public void setPerfilId(int perfilId) {
		this.perfilId = perfilId;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSectorId() {
		return sectorId;
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	public String getNombrePerfil() {
		return nombrePerfil;
	}
	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}
	////public String getNombrePuesto() {
		//return nombrePuesto;
	//}
	//public void setNombrePuesto(String nombrePuesto) {
	//	this.nombrePuesto = nombrePuesto;
	//}
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}
	public ArrayList<Puesto> getPuestos() {
		return puestos;
	}
	public void setPuestos(ArrayList<Puesto> puestos) {
		this.puestos = puestos;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
}
