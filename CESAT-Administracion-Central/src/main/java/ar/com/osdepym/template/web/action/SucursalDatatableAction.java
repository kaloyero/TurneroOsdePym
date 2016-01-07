package ar.com.osdepym.template.web.action;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;

import ar.com.osdepym.template.entity.Sucursal;
import ar.com.osdepym.template.service.SucursalServicio;

import com.opensymphony.xwork2.ActionSupport;
 
public class SucursalDatatableAction extends ActionSupport implements ParameterAware{
	  Map parameters;
	  private ArrayList<Sucursal> aaData = new ArrayList();
	  private SucursalServicio sucursalServicio = new SucursalServicio();
	  private Sucursal row ;
	  private String error;
	
	/**
	 * Lista Sucursales
	 * @return
	 */
	public String listaSucursales() {
		this.setAaData(this.getSucursalServicio().listaSucursal());
		return SUCCESS;
	}
	
	/**
	 * Sucursales Operaiones
	 * @return
	 */
	public String sucursalesOperaciones() {

		//LoginAction.getUsuariosLogueadosTest().remove(0);
		//LoginAction.getUsuariosLogueadosTest().add(new Date());
		String[] accion= (String[]) parameters.get("action");
	

		if (accion[0].equalsIgnoreCase("edit") ){
			Sucursal sucursalEditar= new Sucursal();

			sucursalEditar.setDT_RowId(Integer.parseInt(((String[]) parameters.get("id"))[0]));

			sucursalEditar.setCodSucursal((((String[]) parameters.get("data[codSucursal]"))[0]));

			sucursalEditar.setNombreSucursal(((String[]) parameters.get("data[nombreSucursal]"))[0].trim());

			sucursalEditar.setIp(((String[]) parameters.get("data[ip]"))[0]);
			sucursalEditar.setHabilitado(((String[]) parameters.get("data[habilitado]"))[0]);

			//sucursalEditar.setCodigoTotem(Integer.parseInt(((String[]) parameters.get("data[codigoTotem]"))[0]));
			

			this.setRow(this.getSucursalServicio().editarSucursal(sucursalEditar));
			this.setError(this.getRow().getError());
		}
		if (accion[0].equalsIgnoreCase("create") ){
			Sucursal sucursalInsertar= new Sucursal();
			sucursalInsertar.setCodSucursal((((String[]) parameters.get("data[codSucursal]"))[0]));
			sucursalInsertar.setNombreSucursal(((String[]) parameters.get("data[nombreSucursal]"))[0].trim());
			sucursalInsertar.setIp(((String[]) parameters.get("data[ip]"))[0]);
			sucursalInsertar.setHabilitado(((String[]) parameters.get("data[habilitado]"))[0]);

			//sucursalInsertar.setCodigoTotem(Integer.parseInt(((String[]) parameters.get("data[codigoTotem]"))[0]));

			this.setRow(this.getSucursalServicio().insertarSucursal(sucursalInsertar));
			this.setError(this.getRow().getError());
		}
		
		return SUCCESS;
	}

	
	public ArrayList<Sucursal> getAaData() {
		return aaData;
	}
	public void setAaData(ArrayList<Sucursal> aaData) {
		this.aaData = aaData;
	}
	public SucursalServicio getSucursalServicio() {
		return sucursalServicio;
	}
	public void setSucursalServicio(SucursalServicio sucursalServicio) {
		this.sucursalServicio = sucursalServicio;
	}
	public Map getParameters() {
	    return parameters;
	  }

	  public void setParameters(Map parameters) {
	    this.parameters = parameters;
	  }


	public Sucursal getRow() {
		return row;
	}
	public void setRow(Sucursal row) {
		this.row = row;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

 

}