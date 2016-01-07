package ar.com.osdepym.template.entity;

import java.util.List;

												// Login

public class RolSectores {
	

	private String id_usuario ;
	private String rol ;
	private String letra ;
	private String nombre ;
	private int numeroSector ;

	private List<String> nom_sectores ;
	private List<String> nro_sectores ;
	
	private String puesto ;
	
	private int cod_error ;

	private List<String> letra_sectores;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getId_turno() {
		return id_turno;
	}
	public void setId_turno(String id_turno) {
		this.id_turno = id_turno;
	}
	private String numero ;
	private String id_turno ;
	
	// -----------------------------------------------------------------------
 	
	public List<String> getLetra_sectores() {
		return letra_sectores;
	}
	public void setLetra_sectores(List<String> letra_sectores) {
		this.letra_sectores = letra_sectores;
	}
	public List<String> getNro_sectores() {
		return nro_sectores;
	}
	public void setNro_sectores(List<String> nro_sectores) {
		this.nro_sectores = nro_sectores;
	}
	public int getCod_error() {
		return cod_error;
	}
	public void setCod_error(int cod_error) {
		this.cod_error = cod_error;
	}
	public String getMensaje_error() {
		return mensaje_error;
	}
	public void setMensaje_error(String mensaje_error) {
		this.mensaje_error = mensaje_error;
	}
	private String mensaje_error ;
	
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
 
	public List<String> getNom_sectores() {
		return nom_sectores;
	}
	public void setNom_sectores(List<String> nom_sectores) {
		this.nom_sectores = nom_sectores;
	}
	public List<String> getCod_sectores() {
		return cod_sectores;
	}
	public void setCod_sectores(List<String> cod_sectores) {
		this.cod_sectores = cod_sectores;
	}
	private List<String> cod_sectores ;

	public void addCod_sectores(String cod_sector) {
		this.cod_sectores.add(cod_sector);
	}
	public void addNom_sectores(String nom_sector) {
		this.nom_sectores.add(nom_sector);
	}
	public void addNro_sectores(String nro_sector) {
		this.nro_sectores.add(nro_sector);
	}
	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumeroSector() {
		return numeroSector;
	}
	public void setNumeroSector(int numeroSector) {
		this.numeroSector = numeroSector;
	}
	
}
