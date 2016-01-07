package ar.com.osdepym.template.web.action;

// Llamador

import java.util.ArrayList;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.common.validation.LlamarTurno;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Turno;
import ar.com.osdepym.template.entity.Usuario;

public class AtendiendoAction extends AbstractLoginAction {
	
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + AtendiendoAction.class);

	/**
	 * Llama al siguiente turno
	 */
	public String execute() {
		try {
			setProperties();
			Usuario rolSector = new Usuario();
			rolSector.setCod_error(0);
			this.setContraCambiada("0");
			rolSector.setCod_sectores(new ArrayList<String>());
			rolSector.setNom_sectores(new ArrayList<String>());
			rolSector.setNro_sectores(new ArrayList<String>());
			rolSector.addCod_sectores(getSectorDAO().obtenerSectorPorNombre(
					session.get(SECTOR).toString()).getCodSector());
			rolSector.addNom_sectores(session.get(SECTOR).toString());
			rolSector.addNro_sectores(getSectorDAO().obtenerSectorPorNombre(
					session.get(SECTOR).toString()).getSector());
			rolSector.setPuesto(getPuesto());
			rolSector.setNumero("0");
			rolSector.setId_turno("0");
			if (verifyExistance()) {
				this.setErrorMessage("Debe finalizar la atencion antes de llamar al siguiente");
				return ERROR;
			} else {
				LlamarTurno lt = new LlamarTurno();
				int nro_usuario = new Integer(getUsuarioDao().obtenerUsuario(
						session.get(USER).toString()).getId_usuario());
				rolSector = lt.execute(new Integer(session.get(ID_PUESTO)
						.toString()), new Integer(rolSector.getNro_sectores()
						.get(0)), nro_usuario, rolSector);
				if (!(rolSector.getId_turno().equals("0"))) {
					this.setPuesto(rolSector.getPuesto());
					this.setNomSector(session.get(SECTOR).toString());
					this.setId_user(getUsuarioDao()
							.obtenerUsuario(session.get(USER).toString())
							.getId_usuario().toString());
					this.setLetra_sector(""
							+ rolSector.getCod_sectores().get(0));
					this.setNro_sector(rolSector.getNro_sectores().get(0));
					this.setNumero(rolSector.getNumero());
					this.setId_turno(rolSector.getId_turno());
					this.setNroLlamado(rolSector.getCod_sectores().get(0)
							.toUpperCase()
							+ rolSector.getNumero());
					this.setNomUsuario(session.get(USER).toString());
					setNrosEspera(getTurnoDAO().numerosEnEspera(
							new Integer(this.getNro_sector())));
					setSectores(getUsuarioDao().recuperarSectoresUsuario(
							nro_usuario));
					for (Sector sector : getSectores()) {
						if (!sector.getNomSector().equals(
								session.get(SECTOR).toString())) {
							sector.setNrosEspera(getTurnoDAO().numerosEnEspera(
									new Integer(sector.getSector())).toString());
						}
					}
					session.put(TURNO, this.getId_turno());
					this.setLlamado("SI");
				}
				if (rolSector.getCod_error() > 0) {
					this.setErrorMessage(rolSector.getMensaje_error());
					return "error";

				}
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}
		return SUCCESS;
	}

	/**
	 * setea nros en espera
	 * @return String
	 */
	public String turnosEnEspera() {
		setProperties();
		setNrosEspera(getTurnoDAO().numerosEnEspera(
				new Integer(this.getNro_sector())));

		return SUCCESS;

	}
	/**
	 * Llama al metodo para actualizar contraseña
	 * @return String
	 */
	public String actualizarContrasena() {
		try {
			setProperties();
			Usuario user = new Usuario();
			user = getUsuarioDao().obtenerUsuario(session.get(USER).toString());

			setSectores(getUsuarioDao().recuperarSectoresUsuario(
					user.getId_usuario()));
			for (Sector sector : getSectores()) {
				if (!sector.getNomSector().equals(
						session.get(SECTOR).toString())) {
					sector.setNrosEspera(getTurnoDAO().numerosEnEspera(
							new Integer(sector.getSector())).toString());
				} else {
					sector.setCodSector("");
				}
			}

			if (getContrasenaAnterior().equals(user.getPassword())) {
				if (getContrasenaNueva().equals(getContrasenaRepetida())) {
					getUsuarioDao().cambiarContraseña(user.getId_usuario(),
							getContrasenaNueva());
					this.setNroLlamado(getSectorDAO().obtenerSectorPorNombre(
							session.get(SECTOR).toString()).getCodSector()
							+ "--");
					this.setContraCambiada("1");
					return SUCCESS;
				} else {
					this.setErrorMessage("Error! La nueva contraseña debe ser igual a la repetida");
					return ERROR;
				}
			} else {
				this.setErrorMessage("Error! La contraseña anterior es erronea");
				return ERROR;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}

	}

	/**
	 * Verifica que el usuario pueda cambiar contraseña
	 * @return String
	 */
	public String cambiarContrasena() {
		try {
			setProperties();
			setSectores(getUsuarioDao().recuperarSectoresUsuario(
					this.getUsuarioDao()
							.obtenerUsuario(session.get(USER).toString())
							.getId_usuario()));
			for (Sector sector : getSectores()) {
				if (!sector.getNomSector().equals(
						session.get(SECTOR).toString())) {
					sector.setNrosEspera(getTurnoDAO().numerosEnEspera(
							new Integer(sector.getSector())).toString());
				} else {
					sector.setCodSector("");
				}
			}
			if (verifyExistance()) {
				this.setErrorMessage("Debe finalizar la atencion primero");
				this.setNroLlamado(getSectorDAO().obtenerSectorPorNombre(
						session.get(SECTOR).toString()).getCodSector()
						+ session.get(TURNO));
				return ERROR;
			} else {
				return SUCCESS;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}
	}

	/**
	 * Vuelve a la pagina anterior. Verifica sesiones y setea propiedades
	 * @return String
	 */
	public String volver() {
		try {
			setProperties();

			this.setLetra_sector(getSectorDAO().obtenerSectorPorNombre(
					session.get(SECTOR).toString()).getCodSector());

			setSectores(getUsuarioDao().recuperarSectoresUsuario(
					this.getUsuarioDao()
							.obtenerUsuario(session.get(USER).toString())
							.getId_usuario()));
			for (Sector sector : getSectores()) {
				if (!sector.getNomSector().equals(
						session.get(SECTOR).toString())) {
					sector.setNrosEspera(getTurnoDAO().numerosEnEspera(
							new Integer(sector.getSector())).toString());
				} else {
					sector.setCodSector("");
				}
			}

			if (verifyExistance()) {
				Turno turno = new Turno();
				turno = getTurnoDAO().obtenerTurnoPorId(
						new Integer(this.session.get(TURNO).toString()));
				this.setNroLlamado(turno.getId_cod_sector()
						+ turno.getNumero_turno());

			} else {
				this.setNroLlamado(this.getLetra_sector().toUpperCase() + "--");
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}

		return SUCCESS;
	}

	/**
	 * Cerrar sesion. Limpia la sesion
	 * @return
	 */
	public String desconectar() {
		try {
			if (verifyExistance()) {
				this.setErrorMessage("Debe finalizar la atencion primero");
				return ERROR;
			} else {
				this.setNomUsuario("");
				this.setId_turno("");
				this.setId_user("");
				this.setLetra_sector("");
				this.setNomSector("");
				this.setNro_sector("");
				this.setNumero("");
				this.setPuesto("");
				this.setSectores(new ArrayList<Sector>());
				this.session.remove(USER);
				this.session.remove(SECTOR);
				this.session.remove(PERFIL);
				this.session.remove(PUESTO);
				this.session.remove(DIR_IP);
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}
		return SUCCESS;
	}
}