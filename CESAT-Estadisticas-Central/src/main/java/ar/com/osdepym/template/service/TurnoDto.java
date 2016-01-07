package ar.com.osdepym.template.service;


public class TurnoDto {
	
	private String numero_turno;	
	private String id_cod_sector;
	private String codSucursal;
	private String fecha_ticket;
	private String atendido;	
	private String llamado;
	private String nomUsuario;
	private String nomSector;
	private String horaTurno;
	private String horaAtencion;
	private String horaFin;
	private Integer idSucursal;
	private Integer cantidadTurnos;
	private Integer cantidadAtendidos;
	private Integer cantidadLlamados;
	private String nombreSucursal;
    private Integer cantidadCancelados;
	


	public Integer getCantidadLlamados() {
		return cantidadLlamados;
	}
	public void setCantidadLlamados(Integer cantidadLlamados) {
		this.cantidadLlamados = cantidadLlamados;
	}
	public String getCodSucursal() {
		return codSucursal;
	}
	public void setCodSucursal(String codSucursal) {
		this.codSucursal = codSucursal;
	}
	public Integer getCantidadCancelados() {
		return cantidadCancelados;
	}
	public void setCantidadCancelados(Integer cantidadCancelados) {
		this.cantidadCancelados = cantidadCancelados;
	}
	
	public Integer getCantidadTurnos() {
		return cantidadTurnos;
	}
	public void setCantidadTurnos(Integer cantidadTurnos) {
		this.cantidadTurnos = cantidadTurnos;
	}
	public Integer getCantidadAtendidos() {
		return cantidadAtendidos;
	}
	public void setCantidadAtendidos(Integer cantidadAtendidos) {
		this.cantidadAtendidos = cantidadAtendidos;
	}

	private Integer tiempMaximoEspera;
	private String tiempMaximoEsperaTexto;
	private Integer tiempPromedioEspera;
	private String tiempPromedioEsperaTexto;

	private Integer tiempMaximoAtencion;
	private String tiempMaximoAtencionTexto;

	private Integer tiempPromedioAtencion;

	public String getTiempMaximoEsperaTexto() {
		return tiempMaximoEsperaTexto;
	}
	public void setTiempMaximoEsperaTexto(String tiempMaximoEsperaTexto) {
		this.tiempMaximoEsperaTexto = tiempMaximoEsperaTexto;
	}
	public String getTiempPromedioEsperaTexto() {
		return tiempPromedioEsperaTexto;
	}
	public void setTiempPromedioEsperaTexto(String tiempPromedioEsperaTexto) {
		this.tiempPromedioEsperaTexto = tiempPromedioEsperaTexto;
	}

	private String tiempPromedioAtencionTexto;

	private Integer cantidadDistintosUsuarios;
		
	
	public String getTiempPromedioAtencionTexto() {
		return tiempPromedioAtencionTexto;
	}
	public void setTiempPromedioAtencionTexto(String tiempPromedioAtencionTexto) {
		this.tiempPromedioAtencionTexto = tiempPromedioAtencionTexto;
	}
	public String getTiempMaximoAtencionTexto() {
		return tiempMaximoAtencionTexto;
	}
	public void setTiempMaximoAtencionTexto(String tiempMaximoAtencionTexto) {
		this.tiempMaximoAtencionTexto = tiempMaximoAtencionTexto;
	}
	public Integer getIdSucursal() {
		return idSucursal;
	}
	public void setIdSucursal(Integer idSucursal) {
		this.idSucursal = idSucursal;
	}
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	public Integer getTiempMaximoEspera() {
		return tiempMaximoEspera;
	}
	public void setTiempMaximoEspera(Integer tiempMaximoEspera) {
		this.tiempMaximoEspera = tiempMaximoEspera;
	}
	public Integer getTiempPromedioEspera() {
		return tiempPromedioEspera;
	}
	public void setTiempPromedioEspera(Integer tiempPromedioEspera) {
		this.tiempPromedioEspera = tiempPromedioEspera;
	}
	public Integer getTiempMaximoAtencion() {
		return tiempMaximoAtencion;
	}
	public void setTiempMaximoAtencion(Integer tiempMaximoAtencion) {
		this.tiempMaximoAtencion = tiempMaximoAtencion;
	}
	public Integer getTiempPromedioAtencion() {
		return tiempPromedioAtencion;
	}
	public void setTiempPromedioAtencion(Integer tiempPromedioAtencion) {
		this.tiempPromedioAtencion = tiempPromedioAtencion;
	}
	public String getNumero_turno() {
		return numero_turno;
	}
	public void setNumero_turno(String numero_turno) {
		this.numero_turno = numero_turno;
	}
	public String getId_cod_sector() {
		return id_cod_sector;
	}
	public void setId_cod_sector(String id_cod_sector) {
		this.id_cod_sector = id_cod_sector;
	}
	public String getFecha_ticket() {
		return fecha_ticket;
	}
	public void setFecha_ticket(String fecha_ticket) {
		this.fecha_ticket = fecha_ticket;
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
	public String getHoraTurno() {
		return horaTurno;
	}
	public void setHoraTurno(String horaTurno) {
		this.horaTurno = horaTurno;
	}
	public String getHoraAtencion() {
		return horaAtencion;
	}
	public void setHoraAtencion(String horaAtencion) {
		this.horaAtencion = horaAtencion;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public Integer getCantidadDistintosUsuarios() {
		return cantidadDistintosUsuarios;
	}
	public void setCantidadDistintosUsuarios(Integer cantidadDistintosUsuarios) {
		this.cantidadDistintosUsuarios = cantidadDistintosUsuarios;
	}
	

}
