package ar.com.osdepym.template.web.action;

// Login

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sector;

public class CanceladoAction extends AbstractLoginAction {
	
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + CanceladoAction.class);

	/**
	 * Metodo para cancelar turno
	 */
	public String execute() {
		try {
			this.setNomUsuario(session.get(USER).toString());
			this.setNomSector(session.get(SECTOR).toString());
			this.setPerfil(session.get(PERFIL).toString());
			this.setPuesto(session.get(PUESTO).toString());
			this.setIpv4(session.get(DIR_IP).toString());
			this.setLetra_sector(getSectorDAO().obtenerSectorPorNombre(
					session.get(SECTOR).toString()).getCodSector());
			this.setNroLlamado(this.getLetra_sector().toUpperCase() + "--");
			this.setNrosEspera(getTurnoDAO().numerosEnEspera(
					new Integer(getSectorDAO().obtenerSectorPorCodigo(
							this.getLetra_sector()).getSector())));
			setSectores(getUsuarioDao().recuperarSectoresUsuario(
					getUsuarioDao()
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
			if (!verifyExistance()) {
				this.setErrorMessage("No ha llamado ningun turno");
				return ERROR;
			} else {
				this.session.put(TURNO, "");
				return SUCCESS;
			}

		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());

			return ERROR + "null";
		}
	}
}