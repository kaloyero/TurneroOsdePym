package ar.com.osdepym.template.web.action;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;

import ar.com.osdepym.template.entity.UsuarioSector;
import ar.com.osdepym.template.service.UsuarioServicio;

import com.opensymphony.xwork2.ActionSupport;
 
public class UsuarioSectoresDatatableAction extends ActionSupport implements ParameterAware{
	  Map parameters;
	  private ArrayList<UsuarioSector> aaData = new ArrayList();
	  private UsuarioServicio usuarioServicio = new UsuarioServicio();
	  private UsuarioSector row ;
	
	  /**
	   * Lista usuarios sectores
	   * @return
	   */
	public String listaUsuarioSectores() {
		this.setAaData(this.getUsuarioServicio().listaUsuariosSectores());
		return SUCCESS;
	}
	/**
	 * Usuarios Sectores operaciones
	 * @return
	 */
	public String usuarioSectoresOperaciones() {
		LoginAction.getUsuariosLogueadosTest().remove(0);
		LoginAction.getUsuariosLogueadosTest().add(new Date());
		String[] accion= (String[]) parameters.get("action");
		if (accion[0].equalsIgnoreCase("edit") ){
			UsuarioSector usuarioSectorEditar= new UsuarioSector();
			
			usuarioSectorEditar.setSectorId(Integer.parseInt(((String[]) parameters.get("data[sectorId]"))[0]));
			usuarioSectorEditar.setUsuarioId(Integer.parseInt(((String[]) parameters.get("data[usuarioId]"))[0]));

			usuarioSectorEditar.setDT_RowId(Integer.parseInt(((String[]) parameters.get("id"))[0]));
		

			this.setRow(this.getUsuarioServicio().editarUsuarioSector(usuarioSectorEditar));
		}
		if (accion[0].equalsIgnoreCase("create")){
			UsuarioSector usuarioSectorIngresar= new UsuarioSector();
			
			usuarioSectorIngresar.setSectorId(Integer.parseInt(((String[]) parameters.get("data[sectorId]"))[0]));
			usuarioSectorIngresar.setUsuarioId(Integer.parseInt(((String[]) parameters.get("data[usuarioId]"))[0]));
			
			this.setRow(this.getUsuarioServicio().insertarUsuarioSector(usuarioSectorIngresar));
		}
		if (accion[0].equalsIgnoreCase("remove")){
			this.getUsuarioServicio().eliminarUsuarioSector(Integer.parseInt(((String[]) parameters.get("id[]"))[0]));
		}
		
		return SUCCESS;
	}

	public ArrayList<UsuarioSector> getAaData() {
		return aaData;
	}
	public Map getParameters() {
	    return parameters;
	  }

	  public void setParameters(Map parameters) {
	    this.parameters = parameters;
	  }
	public UsuarioServicio getUsuarioServicio() {
		return usuarioServicio;
	}
	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}
	public UsuarioSector getRow() {
		return row;
	}
	public void setRow(UsuarioSector row) {
		this.row = row;
	}
	public void setAaData(ArrayList<UsuarioSector> aaData) {
		this.aaData = aaData;
	}

 

}