package ar.com.osdepym.template.entity;

import java.util.List;

public class Usuario {
	
	private Integer id_usuario ;
	private String nomUsuario;
	private String rol ;
	private List<String> cod_sectores ;
	private List<String> nom_sectores ;
	private List<String> nro_sectores ;
	private String mensaje_error ;
	private String password;
	private String perfil;
	private String puesto ;
	private int cod_error ;
	private List<String> letra_sectores;
	private String id_turno;
	private String numero;

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
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

	public void setNom_sectores(List<String> nomSectores) {
		nom_sectores = nomSectores;
	}

	public List<String> getNro_sectores() {
		return nro_sectores;
	}

	public void setNro_sectores(List<String> nroSectores) {
		nro_sectores = nroSectores;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public int getCod_error() {
		return cod_error;
	}

	public void setCod_error(int codError) {
		cod_error = codError;
	}

	public List<String> getLetra_sectores() {
		return letra_sectores;
	}

	public void setLetra_sectores(List<String> letraSectores) {
		letra_sectores = letraSectores;
	}

	
	public void addCod_sectores(String cod_sector) {
		this.cod_sectores.add(cod_sector);
	}
	public void addNom_sectores(String nom_sector) {
		this.nom_sectores.add(nom_sector);
	}
	public void addNro_sectores(String nro_sector) {
		this.nro_sectores.add(nro_sector);
	}

	public List<String> getCod_sectores() {
		return cod_sectores;
	}

	public void setCod_sectores(List<String> codSectores) {
		cod_sectores = codSectores;
	}

	public String getMensaje_error() {
		return mensaje_error;
	}

	public void setMensaje_error(String mensajeError) {
		mensaje_error = mensajeError;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId_turno() {
		return id_turno;
	}

	public void setId_turno(String id_turno) {
		this.id_turno = id_turno;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
