package ar.com.osdepym.template.entity;

import java.util.ArrayList;


public class UsuarioSector {

	private int DT_RowId= 0;
	private int usuarioId ;
	private int sectorId ;
	private String sectorNombre ;
	private String nombreUsuario ;
	private String codigoSector ;
	public int getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(int dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public int getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
	public int getSectorId() {
		return sectorId;
	}
	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}
	public String getSectorNombre() {
		return sectorNombre;
	}
	public void setSectorNombre(String sectorNombre) {
		this.sectorNombre = sectorNombre;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getCodigoSector() {
		return codigoSector;
	}
	public void setCodigoSector(String codigoSector) {
		this.codigoSector = codigoSector;
	}
	
	
	
}
