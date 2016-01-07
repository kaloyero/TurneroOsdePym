package ar.com.osdepym.template.service;

import java.util.ArrayList;

import ar.com.osdepym.template.dao.SucursalDao;
import ar.com.osdepym.template.entity.Sucursal;

												// Login

public class SucursalServicio {
	private SucursalDao sucursalDao= new SucursalDao();
	
	/**
	 * Listar Scursales
	 * @return ArrayList<Sucursal>
	 */
	public ArrayList<Sucursal> listaSucursal() {
		return sucursalDao.listaSucursal();
	}

	/**
	 * Editar Sucursales
	 * @param sucursalEditar
	 * @return Sucursal
	 */
	public Sucursal editarSucursal(Sucursal sucursalEditar) {

		return this.sucursalDao.editarSucursal(sucursalEditar);
	}
	/**
	 * Insertar Sucursales
	 * @param sucursalInsertar
	 * @return Sucursal
	 */
	public Sucursal insertarSucursal(Sucursal insertarSucursal) {
		
		return this.sucursalDao.insertarSucursal(insertarSucursal);
	}

	
	
}
