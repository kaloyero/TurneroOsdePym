package ar.com.osdepym.template.service;

import ar.com.osdepym.template.dao.LoginDao;
import ar.com.osdepym.template.entity.Usuario;

public class LoginService {
	
	private LoginDao loginDao = new LoginDao();
	
	/**
	 * Servicio para login del usuario
	 * @param usuario
	 * @param pass
	 * @return Usuario
	 */
	public Usuario login(String usuario, String pass){
		
		return loginDao.login(usuario, pass);
		
	}

}
