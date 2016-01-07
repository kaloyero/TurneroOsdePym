package ar.com.osdepym.template.entity;

import java.util.Date;

public class Turno {
	private Integer id_pantall;
	private Integer numero_turno;	
	private String puesto;	
	private String llamar;
	private Integer	id_turno;
	private Integer id_sector;
	private String id_cod_sector;
	private Date fecha_ticket;
	private Date fecha_atencion;
	private Date fecha_fin;
	private Integer id_usuario;
	private Integer id_puesto;
	private String atendido;	
	private String llamado;
	private String nomUsuario;
	private String nomSector;
	private String hora;
	
	@Override
	public String toString() {
		return "Turno [id_pantall=" + id_pantall + ", id_sector=" + id_sector
				+ ", numero_turno=" + numero_turno + ", puesto=" + puesto
				+ ", llamar=" + llamar + "]";
	}
	public Turno() {
		super();
	}
	public Integer getId_pantall() {
		return id_pantall;
	}
	public void setId_pantall(Integer idPantall) {
		id_pantall = idPantall;
	}
	public Integer getNumero_turno() {
		return numero_turno;
	}
	public void setNumero_turno(Integer numeroTurno) {
		numero_turno = numeroTurno;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public String getLlamar() {
		return llamar;
	}
	public void setLlamar(String llamar) {
		this.llamar = llamar;
	}
	public Integer getId_turno() {
		return id_turno;
	}
	public void setId_turno(Integer idTurno) {
		id_turno = idTurno;
	}
	public Integer getId_sector() {
		return id_sector;
	}
	public void setId_sector(Integer idSector) {
		id_sector = idSector;
	}
	public String getId_cod_sector() {
		return id_cod_sector;
	}
	public void setId_cod_sector(String idCodSector) {
		id_cod_sector = idCodSector;
	}
	
	public Integer getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Integer idUsuario) {
		id_usuario = idUsuario;
	}
	public Integer getId_puesto() {
		return id_puesto;
	}
	public void setId_puesto(Integer idPuesto) {
		id_puesto = idPuesto;
	}
	
	public String getAtendido() {
		return atendido;
	}
	public void setAtendido(String atendido) {
		this.atendido = atendido;
	}
	public String getLlamado() {
		return llamado;
	}
	public void setLlamado(String llamado) {
		this.llamado = llamado;
	}
	public Date getFecha_ticket() {
		return fecha_ticket;
	}
	public void setFecha_ticket(Date fechaTicket) {
		fecha_ticket = fechaTicket;
	}
	public Date getFecha_atencion() {
		return fecha_atencion;
	}
	public void setFecha_atencion(Date fechaAtencion) {
		fecha_atencion = fechaAtencion;
	}
	public Date getFecha_fin() {
		return fecha_fin;
	}
	public void setFecha_fin(Date fechaFin) {
		fecha_fin = fechaFin;
	}
	public String getNomUsuario() {
		return nomUsuario;
	}
	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	public String getNomSector() {
		return nomSector;
	}
	public void setNomSector(String nomSector) {
		this.nomSector = nomSector;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}

}

 