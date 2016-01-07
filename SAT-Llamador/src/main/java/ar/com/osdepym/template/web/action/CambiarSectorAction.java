package ar.com.osdepym.template.web.action;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.LoggerVariables;

public class CambiarSectorAction extends AbstractLoginAction {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + CambiarSectorAction.class);
	
	/**
	 * Llama al metodo para recuperar sectores del usuario
	 * @return String
	 */
	public String cambiarSector() {
		try {
			setProperties();
			if (verifyExistance()) {
				this.setErrorMessage("Debe finalizar la atención antes de cambiar el turno.");
				return ERROR;
			} else {
					setSectores(getUsuarioDao().recuperarSectoresUsuario(
							getUsuarioDao().obtenerUsuario(
									session.get(USER).toString())
									.getId_usuario()));
				return SUCCESS;
			}
		} catch (Exception ex) {
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			this.setErrorMessage("Ha ocurrido un error inesperado.");
			return ERROR + "null";
		}

	}

	/**
	 * Cambia de sector
	 * @return String
	 */
	public String cambioSector() {
		try {
			setSector(getSectorDAO()
					.obtenerSectorPorNombre(this.getNomSector()));

			this.setNro_sector(this.getSector().getSector());
			this.setNomSector(this.getSector().getNomSector());
			setNrosEspera(getTurnoDAO().numerosEnEspera(
					new Integer(this.getNro_sector())));
			setLetra_sector(this.getSector().getCodSector());
			session.put(SECTOR, this.getNomSector());
			this.setNroLlamado(this.getLetra_sector().toUpperCase() + "--");
			setProperties();
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}
		return SUCCESS;
	}
}
