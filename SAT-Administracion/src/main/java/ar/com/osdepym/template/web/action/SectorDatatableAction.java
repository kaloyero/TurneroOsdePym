package ar.com.osdepym.template.web.action;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ParameterAware;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.service.SectorServicio;

import com.opensymphony.xwork2.ActionSupport;
 
public class SectorDatatableAction extends ActionSupport implements ParameterAware{
	  Map parameters;
	  private ArrayList<Sector> aaData = new ArrayList();
	  private SectorServicio sectorServicio = new SectorServicio();
	  private Sector row ;
	  private String error;
		private static Logger LOGGER = Logger.getLogger(LoggerVariables.ADMINISTRADOR + "-" + SectorDatatableAction.class);

		/**
		 * Lista sectores
		 * @return
		 */
	public String listaSectores() {
		LOGGER.debug("Recuperando Sectores");

		this.setAaData(this.getSectorServicio().listaSectores());
		return SUCCESS;
	}
	
	/**
	 * Sectores operaciones
	 * @return
	 */
	public String sectoresOperaciones() {
		LoginAction.getUsuariosLogueadosTest().remove(0);
		LoginAction.getUsuariosLogueadosTest().add(new Date());
		String[] accion= (String[]) parameters.get("action");
		if (accion[0].equalsIgnoreCase("edit") ){
			LOGGER.debug("Editando Sector");

			Sector sectorEditar= new Sector();
			sectorEditar.setCodigoSector(((String[]) parameters.get("data[codigoSector]"))[0].toUpperCase());
			sectorEditar.setDT_RowId(Integer.parseInt(((String[]) parameters.get("id"))[0]));
			sectorEditar.setHabilitado(((String[]) parameters.get("data[habilitado]"))[0]);
			sectorEditar.setNombreSector(((String[])parameters.get("data[nombreSector]"))[0].trim());
			//sectorEditar.setSector(Integer.parseInt(((String[]) parameters.get("data[sector]"))[0]));
			this.setRow(this.getSectorServicio().editarSector(sectorEditar));
			this.setError(this.getRow().getError());
			//this.setAaData(this.getSectorServicio().listaSectores());
		}
		if (accion[0].equalsIgnoreCase("create")){
			LOGGER.debug("Creando Sector");

			Sector sectorInsertar= new Sector();
			sectorInsertar.setCodigoSector(((String[]) parameters.get("data[codigoSector]"))[0].toUpperCase());
			sectorInsertar.setHabilitado(((String[]) parameters.get("data[habilitado]"))[0]);
			sectorInsertar.setNombreSector(((String[])parameters.get("data[nombreSector]"))[0].trim());
			//sectorInsertar.setSector(Integer.parseInt(((String[]) parameters.get("data[sector]"))[0]));
			this.setRow(this.getSectorServicio().insertarSector(sectorInsertar));
			this.setError(this.getRow().getError());
		}
		if (accion[0].equalsIgnoreCase("remove")){
			LOGGER.debug("Eliminando sector");

			this.getSectorServicio().eliminarSector(Integer.parseInt(((String[]) parameters.get("id[]"))[0]));
		}
		
		return SUCCESS;
	}

	public ArrayList<Sector> getAaData() {
		return aaData;
	}

	public void setAaData(ArrayList<Sector> aaData) {
		this.aaData = aaData;
	}
	public Map getParameters() {
	    return parameters;
	  }

	  public void setParameters(Map parameters) {
	    this.parameters = parameters;
	  }
	public SectorServicio getSectorServicio() {
		return sectorServicio;
	}
	public void setSectorServicio(SectorServicio sectorServicio) {
		this.sectorServicio = sectorServicio;
	}
	public Sector getRow() {
		return row;
	}
	public void setRow(Sector row) {
		this.row = row;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
 

}