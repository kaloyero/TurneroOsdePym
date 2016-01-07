package ar.com.osdepym.template.web.action;

// Login

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.common.validation.ValidacionUsuario;
import ar.com.osdepym.template.entity.Sucursal;
import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.service.SucursalDao;

public class PantallaAction extends AbstractLoginAction implements
		ParameterAware {
	private static Logger LOGGER = Logger.getLogger(LoggerVariables.OPERADOR
			+ "-" + PantallaAction.class);

	Map parameters;
	HttpServletRequest request = ServletActionContext.getRequest();
	private String username;
	private String password;
	private List<String> lista;
	private String ejemplo;
	private List<Sucursal> sucursales = new ArrayList<Sucursal>();
	private String chequeoSucursales;

	private SucursalDao surcursalDao = new SucursalDao();


	/**
	 * Llama al metodo para validar usuario
	 * Setea puesto segun la IP
	 */
	public String execute() {
		System.out.println("sadasdas");

		String retorno = "successestad";
		Usuario usuario = new Usuario();
		usuario.setCod_error(0);
		ValidacionUsuario vu = new ValidacionUsuario();
		usuario = vu.execute(username, password, usuario);
		try {
			if (usuario.getCod_error() > 0) {
				this.setErrorMessage(usuario.getMensaje_error());
				return "error";
			} else {
				this.setPerfil(usuario.getPerfil());
				this.setId_user(usuario.getId_usuario().toString());
			
				this.setNomUsuario(usuario.getNomUsuario());
				System.out.println("DATOS"+this.getNomUsuario()+ this.getId_user()+getPerfil());

				session.put(USER, this.getNomUsuario());
				session.put("IDUSER", this.getId_user());
				session.put(PERFIL, getPerfil());
				lista = new ArrayList<String>();
				lista.add("google.com");
				lista.add("bing.com");
				lista.add("yahoo.com");
				lista.add("baidu.com");
				sucursales=surcursalDao.listaSucursal();

				this.setLista(lista);
				this.setSucursales(sucursales);
				chequeoSucursales=surcursalDao.getHorarioUltimaActualizacion();
				this.setChequeoSucursales(chequeoSucursales);
				
				return retorno;
			}
		} catch (Exception e) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			System.out.println(e.getMessage());
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			return "error";
		}
	}

	

	public String inicio() {
		try {
			if (verificarLogueado()) {
				//setProperties();
				System.out.println("VerifcarLogua");
				lista = new ArrayList<String>();
				lista.add("google.com");
				lista.add("bing.com");
				lista.add("yahoo.com");
				lista.add("baidu.com");
				this.setLista(lista);
				sucursales=surcursalDao.listaSucursal();
				this.setSucursales(sucursales);
				chequeoSucursales=surcursalDao.getHorarioUltimaActualizacion();
				this.setChequeoSucursales(chequeoSucursales);
					return "logueadoEstadis";
				
			}
		} catch (Exception ex) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());
			return ERROR + "null";
		}
		System.out.println("EntraLogin");

		return SUCCESS;

	}
	/**
	 * Cerrar sesion. Limpia la sesion
	 * @return
	 */
	public String desconectar() {
		try {
			 {
				this.setNomUsuario("");
				this.setId_turno("");
				this.setId_user("");
				this.setLetra_sector("");
				this.setNomSector("");
				this.setNro_sector("");
				this.setNumero("");
				this.setPuesto("");
				
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	


	public List<String> getLista() {
		return lista;
	}



	public void setLista(List<String> lista) {
		this.lista = lista;
	}



	public String getChequeoSucursales() {
		return chequeoSucursales;
	}



	public void setChequeoSucursales(String chequeoSucursales) {
		this.chequeoSucursales = chequeoSucursales;
	}



	public String getEjemplo() {
		return ejemplo;
	}



	public void setEjemplo(String ejemplo) {
		this.ejemplo = ejemplo;
	}
	public List<Sucursal> getSucursales() {
		return sucursales;
	}
	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}
	public SucursalDao getSurcursalDao() {
		return surcursalDao;
	}
	public void setSurcursalDao(SucursalDao surcursalDao) {
		this.surcursalDao = surcursalDao;
	}
	
}