package ar.com.osdepym.template.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.service.LoginService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements
		HttpSessionBindingListener {

	private static Logger LOGGER = Logger
			.getLogger(LoggerVariables.ADMINISTRADOR + "-" + LoginAction.class);

	private String username;
	private String password;
	private String errorMessage;
	private static List<String> usuariosLogueados = new ArrayList<String>();
	private static List<Date> usuariosLogueadosTest = new ArrayList<Date>();

	HashMap<String, Integer> cache = new HashMap<String, Integer>();
	private LoginService loginService = new LoginService();
	Usuario usuario;
	SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext
			.getContext().getSession();

	/**
	 * Verifica si hay un usuario logueado en la sesion actual
	 * 
	 * @return String
	 */
	public String inicio() {
		 HttpServletResponse response = ServletActionContext.getResponse();
		    response.setHeader("Cache-Control", "no-cache");
		    response.setHeader("Pragma", "no-cache");
		    response.setHeader("Expires", "-1");
		LOGGER.debug(LoggerVariables.VERIFICANDO_LOGIN);
		try {

			if ((session.get("user") == null || session.get("user") == "")) {
				return SUCCESS;
			} else {
				return "logueado";
			}
		} catch (NullPointerException ex) {
			LOGGER.error(LoggerVariables.ERROR + "-" + ex.getMessage());
			return SUCCESS;
		}

	}

	/**
	 * Login
	 * 
	 * @return String
	 */
	public String login() {
		 HttpServletResponse response = ServletActionContext.getResponse();
		    response.setHeader("Cache-Control", "no-cache");
		    response.setHeader("Pragma", "no-cache");
		    response.setHeader("Expires", "-1");
		LOGGER.debug(LoggerVariables.PREPARANDO_LOGIN);
		try {
			usuario = new Usuario();
			usuario = loginService.login(username, password);
			if (usuario.getIdUsuario().equals("0")) {
				this.setErrorMessage(usuario.getError());
				LOGGER.debug(LoggerVariables.ERROR + "-" + getErrorMessage());
				return ERROR;
			} else if (usuariosLogueados.contains(usuario.getNombreUsuario())
					&& (!(session.get("user") == null || session.get("user") == ""))) {
				usuariosLogueadosTest.remove(0);

				usuariosLogueadosTest.add(new Date());

				this.session.put("user", usuario.getNombreUsuario());
				return SUCCESS;
			} else {
				if (usuario.getPerfilId() == 1) {
					if (existeUser()) {
						// usuariosLogueados.add(usuario.getNombreUsuario());
						// this.setUsername(usuario.getNombreUsuario());
						// session.put("user", usuario.getNombreUsuario());
						this.setErrorMessage("Ya hay un usuario logueado");
						LOGGER.debug(LoggerVariables.ERROR + "-"
								+ getErrorMessage());
						return ERROR + "Logueado";
					} else {
						usuariosLogueados.add(usuario.getNombreUsuario());
						this.setUsername(usuario.getNombreUsuario());
						session.put("user", usuario.getNombreUsuario());
						// session.put("tiempo", new Date());
						usuariosLogueadosTest.add(new Date());
						LOGGER.debug(LoggerVariables.LOGUEADO + "-"
								+ getUsername());

						return SUCCESS;
					}

				} else {
					this.setErrorMessage("El usuario no tiene los privilegios necesarios");
					LOGGER.debug(LoggerVariables.ERROR + "-"
							+ getErrorMessage());

					return ERROR;
				}
			}

		} catch (Exception e) {
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			LOGGER.error(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * Verifica si existe usuario logueado
	 * 
	 * @return
	 */
	private boolean existeUser() {
		try {
			LOGGER.debug("Verificando existencia usuario");

			if (usuariosLogueados.isEmpty() || usuariosLogueados.equals(null)) {

				return false;
			} else if (tiempoInactivo(new Date(), usuariosLogueadosTest.get(0)) > 1) {
				usuariosLogueados.remove(0);
				usuariosLogueadosTest.remove(0);
				return false;
			} else {
				return true;
			}
		} catch (NullPointerException e) {
			LOGGER.error(LoggerVariables.ERROR + "-" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Calcula tiempo inactividad del usuario
	 * 
	 * @param dt2
	 * @param dt1
	 * @return
	 */
	public long tiempoInactivo(Date dt2, Date dt1) {
		long diff = dt2.getTime() - dt1.getTime();
		long diffMinutes = diff / (60 * 1000) % 60;
		return diffMinutes;

	}

	public String continuar() {

		return SUCCESS;
	}

	/**
	 * Limpia listas y sessions
	 * 
	 * @return
	 */
	public String desconectar() {
		LOGGER.debug("Cerrando sesión...");
		if (!usuariosLogueados.isEmpty()) {
			usuariosLogueados.remove(0);
		}
		if (!usuariosLogueadosTest.isEmpty()) {
			usuariosLogueadosTest.remove(0);
		}

		session.remove("user");
		this.setUsername("");
		this.setPassword("");
		this.setErrorMessage("");

		return SUCCESS;
	}

	public String salir() {

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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static List<String> getUsuariosLogueados() {
		return usuariosLogueados;
	}

	public static void setUsuariosLogueados(List<String> usuariosLogueados) {
		LoginAction.usuariosLogueados = usuariosLogueados;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println();
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static Logger getLOGGER() {
		return LOGGER;
	}

	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

	public static List<Date> getUsuariosLogueadosTest() {
		return usuariosLogueadosTest;
	}

	public static void setUsuariosLogueadosTest(List<Date> usuariosLogueadosTest) {
		LoginAction.usuariosLogueadosTest = usuariosLogueadosTest;
	}

	public HashMap<String, Integer> getCache() {
		return cache;
	}

	public void setCache(HashMap<String, Integer> cache) {
		this.cache = cache;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public SessionMap<String, Object> getSession() {
		return session;
	}

	public void setSession(SessionMap<String, Object> session) {
		this.session = session;
	}

}
