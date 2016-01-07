package ar.com.osdepym.template.service;

import java.util.ArrayList;
import java.util.List;

import ar.com.osdepym.template.dao.SectorDao;
import ar.com.osdepym.template.entity.Sector;

												// Login

public class SectorServicio {
	private SectorDao sectorDao= new SectorDao();
		
	public SectorDao getSectorDao() {
		return sectorDao;
	}
	public void setSectorDao(SectorDao sectorDao) {
		this.sectorDao = sectorDao;
	}
	/**
	 * Listar Sectores
	 * @return ArrayList<Sector>
	 */
	public ArrayList<Sector> listaSectores() {
		return sectorDao.listaSectores();
	}
	/**
	 * Crear Sector
	 * @param sectorInsertar
	 * @return Sector
	 */
	public Sector insertarSector(Sector sectorInsertar) {
		return this.sectorDao.insertarSector(sectorInsertar);
	}
	/**
	 * Modificar Sector
	 * @param sectorEditar
	 * @return Sector
	 */
	public Sector editarSector(Sector sectorEditar) {
		
		return this.sectorDao.editarSector(sectorEditar);
	}
	/**
	 * Eliminar Sector
	 * @param id
	 */
	public void eliminarSector(int id) {
		 this.sectorDao.eliminarSector(id);
		
	}
	
	
	
}
