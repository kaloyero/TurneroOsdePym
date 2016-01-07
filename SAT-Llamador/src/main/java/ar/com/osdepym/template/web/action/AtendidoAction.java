package ar.com.osdepym.template.web.action;

// Llamador

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.common.validation.LlamarTurno;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Usuario;

public class AtendidoAction extends AbstractLoginAction {
	
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + AtendidoAction.class);
	
	/**
	 * Llama al metodo para setear el turno como atendido
	 */
	public String execute() {
		try {
			Usuario rolSector = new Usuario();
			super.setProperties();
			rolSector.setCod_error(0);
			String sector = session.get(SECTOR).toString();
			this.setLetra_sector(super.getSectorDAO()
					.obtenerSectorPorNombre(sector).getCodSector());
			super.setSectores(super.getUsuarioDao().recuperarSectoresUsuario(
					super.getUsuarioDao()
							.obtenerUsuario(session.get(USER).toString())
							.getId_usuario()));
			for (Sector sect : getSectores()) {
				if (!sect.getNomSector().equals(session.get(SECTOR).toString())) {
					sect.setNrosEspera(super.getTurnoDAO()
							.numerosEnEspera(new Integer(sect.getSector()))
							.toString());
				} else {
					sect.setCodSector("");
				}
			}
			if (verifyExistance()) {
				LlamarTurno lt = new LlamarTurno();
				rolSector = lt.execute(new Integer(session.get(TURNO)
						.toString()), true);
				this.setNomSector(session.get(SECTOR).toString());
				this.setNomUsuario(session.get(USER).toString());
				this.setNrosEspera(super.getTurnoDAO().numerosEnEspera(
						new Integer(getSectorDAO().obtenerSectorPorNombre(
								session.get(SECTOR).toString()).getSector())));
				this.setNroLlamado(getLetra_sector().toUpperCase() + "--");
				session.remove(TURNO);
				session.put(TURNO, "");
				if (rolSector.getCod_error() > 0) {

					return ERROR;
				}// end if
				return SUCCESS;
			} else {
				this.setErrorMessage("No ha llamado ningun Turno");
				return ERROR;
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}

	}
}