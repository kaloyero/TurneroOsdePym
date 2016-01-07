package ar.com.osdepym.template.service;

import java.util.ArrayList;
import java.util.List;

import ar.com.osdepym.template.dao.PuestoDao;
import ar.com.osdepym.template.dao.SectorDao;
import ar.com.osdepym.template.entity.Puesto;
import ar.com.osdepym.template.entity.Sector;

												// Login

public class PuestoServicio {
	private PuestoDao puestoDao= new PuestoDao();
	
	/**
	 * Listar Puestos
	 * @return ArrayList<Puesto>
	 */
	public ArrayList<Puesto> listaPuestos() {
		return puestoDao.listaPuestos();
	}
	/**
	 * Crear Puesto
	 * @param puestoInsertar
	 * @return Puesto
	 */
	public Puesto insertarPuesto(Puesto puestoInsertar) {
		return this.puestoDao.insertarPuesto(puestoInsertar);
	}
	/**
	 * Editar Puesto
	 * @param puestoEditar
	 * @return Puesto
	 */
	public Puesto editarPuesto(Puesto puestoEditar) {
		
		return this.puestoDao.editarPuesto(puestoEditar);
	}
	/**
	 * Borrar Sector
	 * @return ArrayList<PuestoServicio>
	 */
	public ArrayList<PuestoServicio> borrarSector() {
		return null;
	}
	/**
	 * Eliminar Puesto
	 * @param parseInt
	 */
	public void eliminarPuesto(int parseInt) {
		this.puestoDao.eliminarPuesto(parseInt);		
	}
	
	
	
}
