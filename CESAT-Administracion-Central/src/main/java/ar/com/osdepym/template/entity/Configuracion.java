package ar.com.osdepym.template.entity;

import java.util.ArrayList;
import java.util.List;

												// Login

public class Configuracion {
	
	private int DT_RowId= 0;
	private int tiempo ;
	private String error;
	public int getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(int dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
