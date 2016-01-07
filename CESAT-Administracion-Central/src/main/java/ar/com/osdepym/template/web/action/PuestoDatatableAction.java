package ar.com.osdepym.template.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ParameterAware;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Puesto;
import ar.com.osdepym.template.service.PuestoServicio;

import com.opensymphony.xwork2.ActionSupport;

public class PuestoDatatableAction extends ActionSupport implements
		ParameterAware {
	Map parameters;
	private ArrayList<Puesto> aaData = new ArrayList();
	private PuestoServicio puestoServicio = new PuestoServicio();
	private Puesto row;
	private String error;

	private static Logger LOGGER = Logger
			.getLogger(LoggerVariables.ADMINISTRADOR + "-"
					+ PuestoDatatableAction.class);
	/**
	 * lista Puestos
	 * @return
	 */
	public String listaPuestos() {
		LOGGER.debug("Recuperando puestos");

		this.setAaData(this.getPuestoServicio().listaPuestos());
		return SUCCESS;
	}

	/**
	 * Puestos operaciones
	 * @return
	 */
	public String puestosOperaciones() {
		LoginAction.getUsuariosLogueadosTest().remove(0);
		LoginAction.getUsuariosLogueadosTest().add(new Date());
		String[] accion = (String[]) parameters.get("action");
		if (accion[0].equalsIgnoreCase("edit")) {
			Puesto puestoEditar = new Puesto();
			puestoEditar.setDT_RowId(Integer.parseInt(((String[]) parameters
					.get("id"))[0]));
			puestoEditar.setPuesto(Integer.parseInt(((String[]) parameters
					.get("data[puesto]"))[0]));
			puestoEditar.setHabilitado(((String[]) parameters
					.get("data[habilitado]"))[0]);
			puestoEditar.setIp(((String[]) parameters.get("data[ip]"))[0]);

			this.setRow(this.getPuestoServicio().editarPuesto(puestoEditar));
			this.setError(this.getRow().getError());
		}
		if (accion[0].equalsIgnoreCase("create")) {
			Puesto puestoInsertar = new Puesto();
			puestoInsertar.setPuesto(Integer.parseInt(((String[]) parameters
					.get("data[puesto]"))[0]));
			puestoInsertar.setHabilitado(((String[]) parameters
					.get("data[habilitado]"))[0]);

			puestoInsertar.setIp(((String[]) parameters.get("data[ip]"))[0]);

			this.setRow(this.getPuestoServicio().insertarPuesto(puestoInsertar));
			this.setError(this.getRow().getError());
		}
		if (accion[0].equalsIgnoreCase("remove")) {
			this.getPuestoServicio().eliminarPuesto(
					Integer.parseInt(((String[]) parameters.get("id[]"))[0]));
		}

		return SUCCESS;
	}

	public ArrayList<Puesto> getAaData() {
		return aaData;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public void setAaData(ArrayList<Puesto> aaData) {
		this.aaData = aaData;
	}

	public PuestoServicio getPuestoServicio() {
		return puestoServicio;
	}

	public void setPuestoServicio(PuestoServicio puestoServicio) {
		this.puestoServicio = puestoServicio;
	}

	public Puesto getRow() {
		return row;
	}

	public void setRow(Puesto row) {
		this.row = row;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}