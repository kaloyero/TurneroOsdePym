package ar.com.osdepym.template.entity;


public class Estadistica {
	
	private String id_usuario;
	private String nomUsuario;
	private String nomSector;	
	private String fecha;
	private String clientesAtendidos;
	private String clientesCancelados;
	private String tiempoPromAtencion;
	private String tiempoTotalAtencion;
	
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String idUsuario) {
		id_usuario = idUsuario;
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
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getClientesAtendidos() {
		return clientesAtendidos;
	}
	public void setClientesAtendidos(String clientesAtendidos) {
		this.clientesAtendidos = clientesAtendidos;
	}
	public String getClientesCancelados() {
		return clientesCancelados;
	}
	public void setClientesCancelados(String clientesCancelados) {
		this.clientesCancelados = clientesCancelados;
	}
	public String getTiempoPromAtencion() {
		return tiempoPromAtencion;
	}
	public void setTiempoPromAtencion(String tiempoPromAtencion) {
		this.tiempoPromAtencion = tiempoPromAtencion;
	}
	public String getTiempoTotalAtencion() {
		return tiempoTotalAtencion;
	}
	public void setTiempoTotalAtencion(String tiempoTotalAtencion) {
		this.tiempoTotalAtencion = tiempoTotalAtencion;
	}
	
	
}



