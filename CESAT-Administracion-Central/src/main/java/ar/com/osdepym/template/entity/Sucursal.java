package ar.com.osdepym.template.entity;


public class Sucursal {
	
	private int DT_RowId= 0;
	private String codSucursal ;
	private int codigoTotem ;
	private String ip;
	private String nombreSucursal;
	private String habilitado;
	private String fechaActualizacion;
	private String fechaConexion;

	private String error;

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getFechaConexion() {
		return fechaConexion;
	}
	public void setFechaConexion(String fechaConexion) {
		this.fechaConexion = fechaConexion;
	}
	public void setDT_RowId(int dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public void setCodSucursal(String codSucursal) {
		this.codSucursal = codSucursal;
	}
	public void setCodigoTotem(int codigoTotem) {
		this.codigoTotem = codigoTotem;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	public int getDT_RowId() {
		return DT_RowId;
	}
	public String getCodSucursal() {
		return codSucursal;
	}
	public int getCodigoTotem() {
		return codigoTotem;
	}
	public String getIp() {
		return ip;
	}
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}
	
	
	
	
	
}
