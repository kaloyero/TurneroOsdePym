package ar.com.osdepym.template.service;

import java.util.ArrayList;
import java.util.List;

import ar.com.osdepym.template.dao.SectorDao;
import ar.com.osdepym.template.dao.UsuarioDao;
import ar.com.osdepym.template.entity.Sector;
import ar.com.osdepym.template.entity.Usuario;
import ar.com.osdepym.template.entity.UsuarioSector;

												// Login

public class UsuarioServicio {
	private UsuarioDao usuarioDao= new UsuarioDao();
	
	
	
	
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	/**
	 * Listar Usuarios servicio
	 * @return ArrayList<Usuario>
	 */
	public ArrayList<Usuario> listaUsuarios() {
		return usuarioDao.listaUsuarios();
	}
	/**
	 * Insertar Usuarios servicio
	 * @return Usuario
	 */
	public Usuario insertarUsuario(Usuario usuarioInsertar) {
		return this.usuarioDao.insertarUsuario(usuarioInsertar);
	}
	/**
	 * Editar Usuarios servicio
	 * @return Usuario
	 */
	public Usuario editarUsuario(Usuario usuarioEditar) {
		
		return this.usuarioDao.editarUsuario(usuarioEditar);
	}
	/**
	 * Eliminar Sector Usuarios servicio
	 * @return ArrayList<UsuarioServicio>
	 */
	public ArrayList<UsuarioServicio> borrarSector() {
		return null;
	}
	/**
	 * Eliminar Usuarios servicio
	 */
	public void eliminarUsuario(int parseInt) {
		// TODO Auto-generated method stub
		this.usuarioDao.eliminarUsuario(parseInt);
	}
	
	//////////////////////////////////////////////
	/**
	 * Listar Usuarios Sectores servicio
	 * @return ArrayList<UsuarioSector>
	 */
	public ArrayList<UsuarioSector> listaUsuariosSectores() {
		return usuarioDao.listaUsuarioSectores();
	}
	/**
	 * Eliminar UsuarioSector
	 * @param parseInt
	 */
	public void eliminarUsuarioSector(int parseInt) {
		// TODO Auto-generated method stub
		this.usuarioDao.eliminarUsuarioSector(parseInt);
	}
	/**
	 * Crear UsuarioSector
	 * @param usuarioSectorInsertar
	 * @return UsuarioSector
	 */
	public UsuarioSector insertarUsuarioSector(UsuarioSector usuarioSectorInsertar) {
		return this.usuarioDao.insertarUsuarioSector(usuarioSectorInsertar);
	}
	/**
	 * Editar UsuarioSector
	 * @param usuarioSectorEditar
	 * @return UsuarioSector
	 */
	public UsuarioSector editarUsuarioSector(UsuarioSector usuarioSectorEditar) {
		
		return this.usuarioDao.editarUsuarioSector(usuarioSectorEditar);
	}
	
}
