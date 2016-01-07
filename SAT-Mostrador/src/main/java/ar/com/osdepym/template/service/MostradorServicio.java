package ar.com.osdepym.template.service;

import java.util.ArrayList;

import ar.com.osdepym.template.dao.MostradorDao;
import ar.com.osdepym.template.entity.Turno;



public class MostradorServicio {
	private MostradorDao mostradorDao= new MostradorDao();
	
	/**
	 * Servicio para obtener los ultimos turnos
	 * @return ArrayList<Turno>
	 */
	public ArrayList<Turno> getUltimosTurnos() {
		return this.mostradorDao.getUltimosTurnos();
	}
	
	
	
}
