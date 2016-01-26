package ar.com.osdepym.turnerorestapi.model.dao;

import java.util.List;

import ar.com.osdepym.turnerorestapi.model.TurnoEstadistica;

/**
 * Interfase de acceso a datos
 * 
 * @author Alejandro Masciotra
 *
 */
public interface ConsultaDao {
	  public List<TurnoEstadistica> getEstadisticas(Integer ultimoId);

	List<TurnoEstadistica> getActualizaTurnosLlamados(Integer ultimoId);
}
