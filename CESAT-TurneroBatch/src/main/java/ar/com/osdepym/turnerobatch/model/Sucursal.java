package ar.com.osdepym.turnerobatch.model;

import java.util.Date;

/**
 * Objeto relacional que representa la tabla Sucursal
 * 
 * @author Alejandro Masciotra
 *
 */
public class Sucursal {

	int id_sat;	
	String cod_sucursal;
	String nom_sucursal;
	String versionSAT;	
	Date fech_act;	
	String IP;
	Date fech_ult_conex;	
	int ult_id_turno;
	String habilitado;
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
	public String getVersionSAT() {
		return versionSAT;
	}
	public void setVersionSAT(String versionSAT) {
		this.versionSAT = versionSAT;
	}
	public Date getFech_act() {
		return fech_act;
	}
	public void setFech_act(Date fech_act) {
		this.fech_act = fech_act;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public Date getFech_ult_conex() {
		return fech_ult_conex;
	}
	public void setFech_ult_conex(Date fech_ult_conex) {
		this.fech_ult_conex = fech_ult_conex;
	}
	public int getUlt_id_turno() {
		return ult_id_turno;
	}
	public void setUlt_id_turno(int ult_id_turno) {
		this.ult_id_turno = ult_id_turno;
	}
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}


	
}
