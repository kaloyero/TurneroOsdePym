package ar.com.osdepym.template.entity;


public class Sucursal {
	
	private int DT_RowId= 0;
	private String codSucursal ;
	private int codigoTotem ;
	private String ip;
	private String nombreSucursal;
	
	private String error;

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
	
	
	
	
	
}
