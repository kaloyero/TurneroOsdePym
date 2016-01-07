package ar.com.osdepym.template.service;

import java.util.ArrayList;

import ar.com.osdepym.template.dao.ConfiguracionDao;
import ar.com.osdepym.template.dao.SucursalDao;
import ar.com.osdepym.template.entity.Configuracion;
import ar.com.osdepym.template.entity.Sucursal;

												// Login

public class ConfiguracionServicio {
	private ConfiguracionDao configuracionDao= new ConfiguracionDao();
	
	/**
	 * Listar Scursales
	 * @return ArrayList<Sucursal>
	 */
	public ArrayList<Configuracion> listaConfiguracion() {
		return configuracionDao.listaConfiguracion();
	}

	/**
	 * Editar Sucursales
	 * @param sucursalEditar
	 * @return Sucursal
	 */
	public Configuracion editarConfiguracion(Configuracion configuracionEditar) {

		return this.configuracionDao.editarConfiguracion(configuracionEditar);
	}
	
	
	
}
