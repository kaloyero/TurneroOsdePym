package ar.com.osdepym.template.web.action;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;

import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.service.UsuarioServicio;

import com.opensymphony.xwork2.ActionSupport;
 
public class UsuarioDatatableAction extends ActionSupport implements ParameterAware{
	  Map parameters;
	  private ArrayList<Usuario> aaData = new ArrayList();
	  private UsuarioServicio usuarioServicio = new UsuarioServicio();
	  private Usuario row ;
	  private String error;
	
	/**
	 * Lista usuarios
	 * @return
	 */
	public String listaUsuarios() {
		this.setAaData(this.getUsuarioServicio().listaUsuarios());
		return SUCCESS;
	}
	/**
	 * Usuarios operaciones
	 * @return
	 */
	public String usuariosOperaciones() {
		LoginAction.getUsuariosLogueadosTest().remove(0);
		LoginAction.getUsuariosLogueadosTest().add(new Date());
		String[] accion= (String[]) parameters.get("action");
		if (accion[0].equalsIgnoreCase("edit") ){
			Usuario usuarioEditar= new Usuario();
			
			usuarioEditar.setNombreUsuario(((String[]) parameters.get("data[nombreUsuario]"))[0].trim());
			usuarioEditar.setPassword(((String[]) parameters.get("data[password]"))[0].trim());
			usuarioEditar.setHabilitado(((String[]) parameters.get("data[habilitado]"))[0]);
			usuarioEditar.setDT_RowId(Integer.parseInt(((String[]) parameters.get("id"))[0]));
			usuarioEditar.setPerfilId(Integer.parseInt(((String[]) parameters.get("data[perfilId]"))[0]));
			//usuarioEditar.setPuestoId(Integer.parseInt(((String[]) parameters.get("data[puestoId]"))[0]));

			this.setRow(this.getUsuarioServicio().editarUsuario(usuarioEditar));
			this.setError(this.getRow().getError());
		}
		if (accion[0].equalsIgnoreCase("create")){
			Usuario usuarioIngresar= new Usuario();
			
			usuarioIngresar.setNombreUsuario(((String[]) parameters.get("data[nombreUsuario]"))[0].trim());
			usuarioIngresar.setPassword(((String[]) parameters.get("data[password]"))[0].trim());
			usuarioIngresar.setHabilitado(((String[]) parameters.get("data[habilitado]"))[0]);
			usuarioIngresar.setPerfilId(Integer.parseInt(((String[]) parameters.get("data[perfilId]"))[0]));
			//usuarioIngresar.setPuestoId(Integer.parseInt(((String[]) parameters.get("data[puestoId]"))[0]));
			
			this.setRow(this.getUsuarioServicio().insertarUsuario(usuarioIngresar));
			this.setError(this.getRow().getError());
		}
		if (accion[0].equalsIgnoreCase("remove")){
			this.getUsuarioServicio().eliminarUsuario(Integer.parseInt(((String[]) parameters.get("id[]"))[0]));
		}
		
		return SUCCESS;
	}

	public ArrayList<Usuario> getAaData() {
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
	public Usuario getRow() {
		return row;
	}
	public void setRow(Usuario row) {
		this.row = row;
	}
	public void setAaData(ArrayList<Usuario> aaData) {
		this.aaData = aaData;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

 

}