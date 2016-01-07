package ar.com.osdepym.template.web.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.LoggerVariables;


import ar.com.osdepym.template.common.validation.ExportarEstadisticas;
import ar.com.osdepym.template.entity.Sucursal;
import ar.com.osdepym.template.service.SucursalDao;
import ar.com.osdepym.template.service.TurnoDto;

public class EstadisticasAction extends AbstractLoginAction {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + EstadisticasAction.class);

	private InputStream fileInputStream;
	private String ultimaFechaCompobacion;


	public InputStream getFileInputStream() {

		return fileInputStream;

	}

	private Date fechaDesde;
	private String noAtendidos = "1";
	private Date fechaHasta;
	private String reporte;
	private String nomArchivoExp;
	private String sucursalElegida;
	private String chequeoSucursales;

	private SucursalDao surcursalDao = new SucursalDao();


	public String getNomArchivoExp() {
		//return nomArchivoExp;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"dd-MM-yyyy_HH-mm");
		Date fechaHoy = new Date();
		String fechaHoyString = sdf.format(fechaHoy);
		if (getTipoReporte().equalsIgnoreCase("Turnos Todas Sucursales")|| getTipoReporte().equalsIgnoreCase("Resumen Sucursales")){
			return "CESAT-"+(String)session.get("TipoReporte")+"_"+"_"+getNomUsuario()+"_"+fechaHoyString+ ".xls";

		}else{
		return "CESAT-"+(String)session.get("TipoReporte")+"_"+(String)session.get("Sucursal")+"_"+"_"+getNomUsuario()+"_"+fechaHoyString+ ".xls";
		}
		}

	public void setNomArchivoExp(String nomArchivoExp) {
		this.nomArchivoExp = nomArchivoExp;
	}

	private String fecha;
	private String tipoReporte;

	private String ruta;
	private String sucursalSeleccionada;
	private List<Sucursal> sucursales = new ArrayList<Sucursal>();



	/**
	 * Va a la pagina de estadisticas
	 */
	public String execute() {
		System.out.println("Execute");

		return SUCCESS;
	}
	public String getTipoReporte() {
		return (String)session.get("TipoReporte");
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	/**
	 * Llama al metodo para generar el archivo a exportar y lo guarda en la pc del user
	 * @return String
	 */
		public String exportar() {
			try {
				
				System.out.print("DAD"+(String)session.get("Fechas"));
				setProperties();
				if (getNomUsuario()!=null){
				//this.setLetra_sector(getSectorDAO().obtenerSectorPorNombre(session.get(SECTOR).toString()).getCodSector());
				//this.setPerfil(session.get(PERFIL).toString());
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
						"dd-MM-yyyy_HH-mm");
				Date fechaHoy = new Date();
				String fecha = sdf.format(fechaHoy);

				setNomArchivoExp("Reporte" + "_" + fecha
						+ ".xls");
				String nombreArchivo="CESAT-"+(String)session.get("TipoReporte")+"_"+(String)session.get("Sucursal")+"_"+getNomUsuario()+"_"+fecha;
				if (getTipoReporte().equalsIgnoreCase("Turnos Todas Sucursales")){
					String ruta = ExportarEstadisticas.exportarTodas(
							(List<TurnoDto>) session.get(TURNOS), nombreArchivo,
							getTipoReporte(),(String)session.get("Fechas"));
					setRutaArchivo(ruta);
				}else{
					String ruta = ExportarEstadisticas.exportar(
							(List<TurnoDto>) session.get(TURNOS), nombreArchivo,
							getTipoReporte(),(String)session.get("Fechas"),(String) session.get("Sucursal"));
					setRutaArchivo(ruta);
				}
				
				fileInputStream = new FileInputStream(new File(getRutaArchivo()));
				}else{
					return "inicio";
				}

			} catch (Exception ex) {
				this.setErrorMessage("Ha ocurrido un error inesperado.");
				LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

				return ERROR + "null";
			}
			return SUCCESS;
		}
		
		/**
		 * Llama al metodo para generar el archivo a exportarEspecial y lo guarda en la pc del user
		 * @return String
		 */
			public String exportarEspecial() {
				try {
					setProperties();
					if (getNomUsuario()!=null){
					//this.setLetra_sector(getSectorDAO().obtenerSectorPorNombre(session.get(SECTOR).toString()).getCodSector());
					//this.setPerfil(session.get(PERFIL).toString());
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
							"dd-MM-yyyy_HH-mm");
					Date fechaHoy = new Date();
					String fecha = sdf.format(fechaHoy);

					setNomArchivoExp("Reporte" + "_" + fecha
							+ ".xls");
					String nombreArchivo="CESAT-"+(String)session.get("TipoReporte")+"_"+getNomUsuario()+"_"+fecha;

					String ruta = ExportarEstadisticas.exportarEspecial(
							(List<TurnoDto>) session.get(TURNOS), nombreArchivo,
							getTipoReporte(),(String) session.get("Fechas"));
					setRutaArchivo(ruta);
					fileInputStream = new FileInputStream(new File(getRutaArchivo()));
					}else{
						return "inicio";
					}


				} catch (Exception ex) {
					this.setErrorMessage("Ha ocurrido un error inesperado.");
					LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

					return ERROR + "null";
				}
				return SUCCESS;
			}
	/**
	 * Comprobacion fecha actualizacion
	 */
	public String getComprobacion(int idSucursal) {
		System.out.println("Execute");
		return surcursalDao.getUltimaComprobacionById(idSucursal);
	}
	
	/**
	 * Vuelve a la pagina de estadisticas
	 * @return String
	 */
	public String volverEstadisticas() {
		System.out.println("Volvers");

		try {
			if (this.getSession().get(USER) != null) {
				//setProperties();
				sucursales=surcursalDao.listaSucursal();
				chequeoSucursales=surcursalDao.getHorarioUltimaActualizacion();
				this.setChequeoSucursales(chequeoSucursales);
				this.setSucursales(sucursales);
				this.setNomUsuario(this.getSession().get(USER).toString());

				return SUCCESS;

			} else {
				this.setErrorMessage("Ha ocurrido un error inesperado.");
				return ERROR + "null";
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}

	}



	/**
	 * Reporte de turnos no llamados
	 * @return String
	 */
	public String clientesNoLlamados() {
		try {
			//setProperties();
			session.put("TipoReporte","Turnos NO llamados");
			this.setTipoReporte("");

			this.setSucursalElegida(surcursalDao.getNombreSucursal(getSucursalSeleccionada()));
			session.put("Sucursal", this.getSucursalElegida());
			this.setUltimaFechaCompobacion(this.getComprobacion(Integer.parseInt(getSucursalSeleccionada())));
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			if (fechaDesde != null && fechaHasta != null
					&& compararFechas(fechaDesde, fechaHasta)) {
				setTurnos(getEstadisticasDao().turnosNoLlamados(fechaDesde,
						fechaHasta,getSucursalSeleccionada()));
				if (!getTurnos().isEmpty()) {
					session.put(TURNOS, getTurnos());
					session.put(NOM_EXPO, "SAT-Reporte-" + getNomUsuario());
					this.setReporte("Turnos no llamados");
					this.setFecha("Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					session.put("Fechas", "Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					//session.put("reporte", getReporte() + " " + fecha);
					setNoAtendidos("1");
					return SUCCESS;
				} else {
					this.setErrorMessage("No hay datos para mostrar");
					return ERROR;
				}
			} else {
				this.setErrorMessage("Debe ingresar un rango de fechas válido");
				return ERROR;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}

	}
	/**
	 * Resumen de Sucursales
	 * @return String
	 */
	public String resumenSucursales() {
		try {
			//setProperties();
			//this.setUltimaFechaCompobacion(this.getComprobacion(Integer.parseInt(getSucursalSeleccionada())));
			this.setUltimaFechaCompobacion(this.getComprobacion(Integer.parseInt(getSucursalSeleccionada())));
			session.put("TipoReporte","Resumen Sucursales");

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			if (fechaDesde != null && fechaHasta != null
					&& compararFechas(fechaDesde, fechaHasta)) {
				setTurnos(getEstadisticasDao().resumenSucursales(fechaDesde,
						fechaHasta));
				if (!getTurnos().isEmpty()) {
					session.put(TURNOS, getTurnos());
					session.put(NOM_EXPO, "SAT-Reporte-" + getNomUsuario());
					this.setReporte("Turnos no llamados");
					this.setFecha("Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					session.put("Fechas", "Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					//session.put("reporte", getReporte() + " " + fecha);
					setNoAtendidos("1");
					return SUCCESS;
				} else {
					this.setErrorMessage("No hay datos para mostrar");
					return ERROR;
				}
			} else {
				this.setErrorMessage("Debe ingresar un rango de fechas válido");
				return ERROR;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}

	}


	/**
	 * Compara las fechas y verifica que sean correctas
	 * @param fechaD
	 * @param fechaH
	 * @return boolean
	 */
	private boolean compararFechas(Date fechaD, Date fechaH) {
		try {
			if (fechaH.after(fechaD) || fechaH.equals(fechaD)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());

			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Reporte de turnos atendidos por sucursal
	 * @return String
	 */
	public String todasSucursales() {
		try {
			
			session.put("TipoReporte","Turnos Todas Sucursales");
			//this.setSucursalElegida(surcursalDao.getNombreSucursal(getSucursalSeleccionada()));
			//session.put("Sucursal", this.getSucursalElegida());
			this.setTipoReporte("todas");
			this.setUltimaFechaCompobacion("");

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			setProperties();
			//if (session.get(TURNOS) != null) {
				//session.remove(TURNOS);
				//session.remove(NOM_EXPO);
			//}
			//System.out.println("SucursalSeleccionada"+sucursalSeleccionada);
			this.setFecha("Desde: " + sdf.format(fechaDesde)
					+ " Hasta: " + sdf.format(fechaHasta));
			session.put("Fechas", "Desde: " + sdf.format(fechaDesde)
					+ " Hasta: " + sdf.format(fechaHasta));
			if (fechaDesde != null && fechaHasta != null
					&& compararFechas(fechaDesde, fechaHasta)) {
				setTurnos(getEstadisticasDao().turnosSucursales(fechaDesde, fechaHasta));

				if (!getTurnos().isEmpty()) {
					session.put(TURNOS, getTurnos());
					session.put(NOM_EXPO, "SAT-Reporte-" + getNomUsuario());
					this.setReporte("Turnos Todas Sucursales");
					this.setFecha("Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					//session.put("reporte", getReporte() + " " + fecha);
					setNoAtendidos("0");
					return SUCCESS;
				} else {
					this.setErrorMessage("No hay datos para mostrar");
					return ERROR;
				}
			} else {
				this.setErrorMessage("Debe ingresar un rango de fechas válido");
				return ERROR;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}

	}
	/**
	 * Reporte de turnos atendidos por sucursal
	 * @return String
	 */
	public String clientesAtendidosPorSucursal() {
		try {
			session.put("TipoReporte","Turnos Por Sucursal");
			this.setSucursalElegida(surcursalDao.getNombreSucursal(getSucursalSeleccionada()));
			session.put("Sucursal", this.getSucursalElegida());
			this.setTipoReporte("todas");


			this.setUltimaFechaCompobacion(this.getComprobacion(Integer.parseInt(getSucursalSeleccionada())));

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			setProperties();
			//if (session.get(TURNOS) != null) {
				//session.remove(TURNOS);
				//session.remove(NOM_EXPO);
			//}
			System.out.println("SucursalSeleccionada"+sucursalSeleccionada);
			this.setFecha("Desde: " + sdf.format(fechaDesde)
					+ " Hasta: " + sdf.format(fechaHasta));
			session.put("Fechas", "Desde: " + sdf.format(fechaDesde)
					+ " Hasta: " + sdf.format(fechaHasta));
			if (fechaDesde != null && fechaHasta != null
					&& compararFechas(fechaDesde, fechaHasta)) {
				setTurnos(getEstadisticasDao().clientesAtendidosPorSucursal(fechaDesde, fechaHasta,getSucursalSeleccionada()));

				if (!getTurnos().isEmpty()) {
					session.put(TURNOS, getTurnos());
					session.put(NOM_EXPO, "SAT-Reporte-" + getNomUsuario());
					this.setReporte("Turnos por sucursal");
					this.setFecha("Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					//session.put("reporte", getReporte() + " " + fecha);
					setNoAtendidos("0");
					return SUCCESS;
				} else {
					this.setErrorMessage("No hay datos para mostrar");
					return ERROR;
				}
			} else {
				this.setErrorMessage("Debe ingresar un rango de fechas válido");
				return ERROR;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}

	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}



	public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getNoAtendidos() {
		return noAtendidos;
	}

	public void setNoAtendidos(String noAtendidos) {
		this.noAtendidos = noAtendidos;
	}

	
	public String getSucursalSeleccionada() {
		return sucursalSeleccionada;
	}

	public void setSucursalSeleccionada(String sucursalSeleccionada) {
		this.sucursalSeleccionada = sucursalSeleccionada;
	}

	public SucursalDao getSurcursalDao() {
		return surcursalDao;
	}

	public void setSurcursalDao(SucursalDao surcursalDao) {
		this.surcursalDao = surcursalDao;
	}

	public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

	public String getUltimaFechaCompobacion() {
		return ultimaFechaCompobacion;
	}

	public void setUltimaFechaCompobacion(String ultimaFechaCompobacion) {
		this.ultimaFechaCompobacion = ultimaFechaCompobacion;
	}

	public String getSucursalElegida() {
		return sucursalElegida;
	}

	public void setSucursalElegida(String sucursalElegida) {
		this.sucursalElegida = sucursalElegida;
	}

	public String getChequeoSucursales() {
		return chequeoSucursales;
	}



	public void setChequeoSucursales(String chequeoSucursales) {
		this.chequeoSucursales = chequeoSucursales;
	}


}
