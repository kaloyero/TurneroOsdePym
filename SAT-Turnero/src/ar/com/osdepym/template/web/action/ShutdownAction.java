package ar.com.osdepym.template.web.action;

import org.apache.log4j.Logger;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.common.utils.Utils;
import ar.com.osdepym.template.web.dao.TurneroDao;

import com.opensymphony.xwork2.ActionSupport;

public class ShutdownAction extends ActionSupport {

	private TurneroDao turneroDao = new TurneroDao();
	
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.TURNERO
			+ "-" + ShutdownAction.class);

	private String opcion;
	private String password;
	/**
	 * Action que llama al metodo de apagado y/o reinicio
	 * @return String
	 */
	public String shutdown() {

		LOGGER.debug("Preparando para apagar el sistema");

		String pass = turneroDao.obtenerPassword();
		if (password.equals(pass)) {
			switch (opcion) {
			case "Apagar":
				Utils.shutdown();
				return SUCCESS;
			case "Reiniciar":
				
				Utils.restart();
				return "reiniciar";
			}
			return SUCCESS;
		}else{
			LOGGER.error(LoggerVariables.ERROR+"- Password incorrecta");

			return ERROR;
		}
	}
	
	public TurneroDao getTurneroDao() {
		return turneroDao;
	}

	public void setTurneroDao(TurneroDao turneroDao) {
		this.turneroDao = turneroDao;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

}
