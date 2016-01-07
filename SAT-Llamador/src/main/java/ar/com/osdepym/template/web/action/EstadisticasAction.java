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
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.TableroEntity;
import ar.com.osdepym.template.service.TurnoDto;

public class EstadisticasAction extends AbstractLoginAction {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + EstadisticasAction.class);

	private InputStream fileInputStream;

	public InputStream getFileInputStream() {

		return fileInputStream;

	}

	private Date fechaDesde;
	private String noAtendidos = "1";
	private Date fechaHasta;
	private String reporte;
	private String nomArchivoExp;

	public String getNomArchivoExp() {
		return nomArchivoExp;
	}

	public void setNomArchivoExp(String nomArchivoExp) {
		this.nomArchivoExp = nomArchivoExp;
	}

	private String fecha;
	private String ruta;
	private List<TableroEntity> datos = new ArrayList<TableroEntity>();
	private TableroEntity entity;

	/**
	 * Va a la pagina de estadisticas
	 */
	public String execute() {
		try {
			setProperties();
			if (verifyExistance()) {
				this.setErrorMessage("Debe finalizar la atención primero");
				return ERROR;
			} else {
				return SUCCESS;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			return ERROR + "null";
		}

	}
	/**
	 * Metodo para generar el tablero
	 * @return String
	 */
	public String generarTablero() {
		try {
			if (this.getSession().get(PERFIL).toString().equals("1")
					|| this.getSession().get(PERFIL).toString().equals("3")) {
				setProperties();
				List<Sector> sects = getSectorDAO().obtenerTodo();
				for (Sector sector : sects) {
					entity = new TableroEntity();
					String[] result = getEstadisticasDao().generarEstadisticas(
							new Integer(sector.getSector()));
					entity.setSector(sector.getNomSector());

					entity.setClientesEspera(result[0]);
					entity.setTiempoEspera(result[1]);
					entity.setTiempoAtencion(result[2]);

					datos.add(entity);

				}
			}else {
				this.setErrorMessage("No tiene los privilegios necesarios");
				return ERROR;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());
			return ERROR + "null";
		}
		System.out.println("aca estoy");

		return SUCCESS;
	}

	/**
	 * Vuelve a la pagina de estadisticas
	 * @return String
	 */
	public String volverEstadisticas() {
		try {
			if (this.getSession().get(USER) != null) {
				setProperties();
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
 * Llama al metodo para generar el archivo a exportar y lo guarda en la pc del user
 * @return String
 */
	public String exportar() {
		try {
			setProperties();
			this.setLetra_sector(getSectorDAO().obtenerSectorPorNombre(
					session.get(SECTOR).toString()).getCodSector());
			this.setPerfil(session.get(PERFIL).toString());
			this.setNroLlamado(this.getLetra_sector().toUpperCase() + "--");

			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"dd-MM-yyyy_HH-mm");
			Date fechaHoy = new Date();
			String fecha = sdf.format(fechaHoy);

			setNomArchivoExp(session.get(NOM_EXPO).toString() + "_" + fecha
					+ ".xls");
			String ruta = ExportarEstadisticas.exportar(
					(List<TurnoDto>) session.get(TURNOS), getNomArchivoExp(),
					session.get("reporte").toString());
			setRutaArchivo(ruta);
			fileInputStream = new FileInputStream(new File(getRutaArchivo()));

		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}
		return SUCCESS;
	}

	/**
	 * Reporte de clientes atendidos por usuario
	 * @return
	 */
	public String clientesAtendidosPuesto() {
		try {
			setProperties();
			this.setLetra_sector(getSectorDAO().obtenerSectorPorNombre(
					session.get(SECTOR).toString()).getCodSector());
			this.setNroLlamado(this.getLetra_sector().toUpperCase() + "--");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			int idUser = new Integer(getUsuarioDao().obtenerUsuario(
					session.get(USER).toString()).getId_usuario());
			if (fechaDesde != null && fechaHasta != null
					&& compararFechas(fechaDesde, fechaHasta)) {
				setTurnos(getEstadisticasDao().clientesAtendidosPuesto(idUser,
						fechaDesde, fechaHasta));
				if (!getTurnos().isEmpty()) {
					session.put(TURNOS, getTurnos());
					session.put(NOM_EXPO, "SAT-Reporte-" + getNomUsuario());
					this.setReporte("Reporte de turnos");
					this.setFecha("Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					session.put("reporte", getReporte() + " " + fecha);
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
	 * Reporte de turnos no llamados
	 * @return String
	 */
	public String clientesNoLlamados() {
		try {
			setProperties();
			this.setLetra_sector(getSectorDAO().obtenerSectorPorNombre(
					session.get(SECTOR).toString()).getCodSector());
			this.setNroLlamado(this.getLetra_sector().toUpperCase() + "--");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			if (fechaDesde != null && fechaHasta != null
					&& compararFechas(fechaDesde, fechaHasta)) {
				setTurnos(getEstadisticasDao().turnosNoLlamados(fechaDesde,
						fechaHasta));
				if (!getTurnos().isEmpty()) {
					session.put(TURNOS, getTurnos());
					session.put(NOM_EXPO, "SAT-Reporte-" + getNomUsuario());
					this.setReporte("Turnos no llamados");
					this.setFecha("Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					session.put("reporte", getReporte() + " " + fecha);
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
	public String clientesAtendidosPorSucursal() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			setProperties();
			if (session.get(TURNOS) != null) {
				session.remove(TURNOS);
				session.remove(NOM_EXPO);
			}
			Sector sect = getSectorDAO().obtenerSectorPorNombre(
					session.get(SECTOR).toString());
			if (fechaDesde != null && fechaHasta != null
					&& compararFechas(fechaDesde, fechaHasta)) {
				setTurnos(getEstadisticasDao().clientesAtendidosPorSucursal(fechaDesde, fechaHasta));

				if (!getTurnos().isEmpty()) {
					session.put(TURNOS, getTurnos());
					session.put(NOM_EXPO, "SAT-Reporte-" + getNomUsuario());
					this.setReporte("Turnos por sucursal");
					this.setFecha("Desde: " + sdf.format(fechaDesde)
							+ " Hasta: " + sdf.format(fechaHasta));
					session.put("reporte", getReporte() + " " + fecha);
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

	public List<TableroEntity> getDatos() {
		return datos;
	}

	public void setDatos(List<TableroEntity> datos) {
		this.datos = datos;
	}

	public TableroEntity getEntity() {
		return entity;
	}

	public void setEntity(TableroEntity entity) {
		this.entity = entity;
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

}
